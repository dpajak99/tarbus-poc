package com.softarea.mpktarnow.services;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.gson.JsonArray;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.activities.MainActivity;
import com.softarea.mpktarnow.adapters.MapBusAdapter;
import com.softarea.mpktarnow.adapters.MapScheduleAdapter;
import com.softarea.mpktarnow.dao.BusDAO;
import com.softarea.mpktarnow.dao.BusStopDAO;
import com.softarea.mpktarnow.dao.RouteDAO;
import com.softarea.mpktarnow.model.AutoRefresh;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.model.BusStopInfoMapBox;
import com.softarea.mpktarnow.model.ListMediator;
import com.softarea.mpktarnow.model.MarkerTag;
import com.softarea.mpktarnow.model.Route;
import com.softarea.mpktarnow.model.RouteHolder;
import com.softarea.mpktarnow.model.RoutePoint;
import com.softarea.mpktarnow.model.RouteWariant;
import com.softarea.mpktarnow.model.SearchResultPoint;
import com.softarea.mpktarnow.model.Vehicle;
import com.softarea.mpktarnow.utils.DatabaseUtils;
import com.softarea.mpktarnow.utils.GeoUtils;
import com.softarea.mpktarnow.utils.ImageUtils;
import com.softarea.mpktarnow.utils.MathUtils;
import com.softarea.mpktarnow.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MapService {
  private static final int MARKER_BUSSTOP_START = 0;
  private static final int MARKER_BUSSTOP_END = 1;
  private static final int MARKER_BUSSTOP_CITY = 2;
  private static final int MARKER_BUSSTOP_ZONE = 3;
  private static final int MARKER_BUSSTOP_TRACK = 4;

  public static final int BUNDLE_BUS_DETAILS = 5;
  public static final int BUNDLE_BUS_STOP_LIST = 6;
  public static final int BUNDLE_SEARCH_CONNECTION = 7;
  public static final int BUNDLE_MAP_GET_FROM = 8;
  public static final int BUNDLE_MAP_GET_TO = 9;

  private AtomicBoolean isMarkerInfoOpened = new AtomicBoolean(false);
  private GoogleMapService mapService;
  private GoogleMap map;
  private FragmentActivity activity;
  private Bundle bundle;
  private List<Boolean> cameraStatus = new ArrayList<>();
  private MarkerService busPosition;
  private MarkerService busDirection;
  private AutoRefresh autoRefresh;
  private MapScheduleAdapter mapScheduleAdapter;

  public MapService(GoogleMapService mapService, Bundle bundle) {
    this.mapService = mapService;
    this.map = mapService.getMap();
    this.activity = mapService.getActivity();
    this.bundle = bundle;
    setCameraMovingStatus(true);
    autoRefresh = new AutoRefresh() {
      @Override
      public void refreshFunction() {
        refreshMap();
      }
    };
  }

  public void refreshMap() {
    if (bundle.getInt( "key" ) == MapService.BUNDLE_BUS_DETAILS) {
      getBusDetails();
    }
  }

  public void stopRefreshingMap() {
    autoRefresh.stopRefreshing();
  }

  public void setCameraMovingStatus(boolean status) {
    cameraStatus.clear();
    cameraStatus.add(status);
  }

  public void showCurrentLocation() {
    GeoUtils.getCurrentLocation(handler, activity, GeoUtils.UPDATE_POSITION);
  }

  public void getBusDetails() {
    BusDAO.getBusStopByLine(handler, String.valueOf(bundle.getInt("busId")), bundle.getInt("busLine"));
  }

  private void createPolylines(List<RoutePoint> routePoints, int color) {
    int routePointsSize = routePoints.size();
    for (int j = 0; j < routePointsSize; j++) {
      if (j < routePointsSize - 1) {
        createSinglePolyline(routePoints.get(j).getCoords(), routePoints.get(j + 1).getCoords(), color);
      }
    }
  }

  public void createSinglePolyline(LatLng first, LatLng second, int color) {
    Polyline line = map.addPolyline(new PolylineOptions()
      .add(first, second)
      .width(15)
      .geodesic(true)
      .startCap(new RoundCap())
      .jointType(JointType.ROUND)
      .color(activity.getColor(color)));
  }

  public void setListeners() {
    map.setOnInfoWindowClickListener(marker -> {
      MarkerTag tag = (MarkerTag) marker.getTag();
      switch (tag.getType()) {
        case MarkerTag.TYPE_BUSSTOP:
          BusStop busStop = (BusStop) tag.getObject();
          if (busStop.getId() != 0) {
            Bundle result = new Bundle();
            result.putInt("id", busStop.getId());
            Navigation.findNavController(mapService.getView()).navigate(R.id.navigation_bus_stop_details, result);
          }
          break;
      }
    });

    mapService.getMap().setOnMarkerClickListener(marker -> {
      MarkerTag tag = (MarkerTag) marker.getTag();
      switch (tag.getType()) {
        case MarkerTag.TYPE_BUSPIN:
          Vehicle vehicle = (Vehicle) tag.getObject();
          MapBusAdapter mapBusAdapter = new MapBusAdapter(activity);
          map.setInfoWindowAdapter(mapBusAdapter);

          mapBusAdapter.setBusDelayInfo(TimeUtils.calcDelayInfo(activity, vehicle.getOdchylenie()));
          mapBusAdapter.setBusDelayValue(TimeUtils.calcDelayValue(vehicle.getOdchylenie()));
          mapBusAdapter.setBusDestination(vehicle.getDestination());
          if ((MathUtils.makePositive(vehicle.getOdchylenie()) / 60) < 4) {
            mapBusAdapter.setBusDelayColor(R.color.success);
          } else {
            mapBusAdapter.setBusDelayColor(R.color.error);
          }

          if (isMarkerInfoOpened.get()) {
            marker.hideInfoWindow();
            isMarkerInfoOpened.set(false);
          } else if (!isMarkerInfoOpened.get()) {
            marker.showInfoWindow();
            isMarkerInfoOpened.set(true);
          }
          break;
        case MarkerTag.TYPE_BUSSTOP:
          BusStop busStop = (BusStop) tag.getObject();
          BusDAO.getBusStopInfo(handler, busStop.getId(), marker, busStop);

          mapScheduleAdapter = new MapScheduleAdapter(activity);
          map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
          map.setInfoWindowAdapter(mapScheduleAdapter);
          break;
        case MarkerTag.TYPE_BUSSTOPEND:
          break;
        case MarkerTag.TYPE_BUSCOMPASS:
          break;
        default:
          break;
      }

      return true;
    });

    mapService.getMap().setOnInfoWindowCloseListener(marker -> {
      isMarkerInfoOpened.set(false);
    });
  }

  public void setBusStopInfo(BusStopInfoMapBox busStopInfoMapBox) {
    mapScheduleAdapter.setDepartues(busStopInfoMapBox.getDepartues().getDepartueList());
    mapScheduleAdapter.setBusStopName(busStopInfoMapBox.getBusStop().getName());
    busStopInfoMapBox.getMarker().showInfoWindow();
  }

  public void showBusStops() {
    for (BusStop busStop : DatabaseUtils.getDatabase(mapService.getActivity()).dbBusStopDAO().getAll()) {
      if (busStop.getIdCity() != 1) {
        createBusStopMarkerOnMap(busStop, MapService.MARKER_BUSSTOP_ZONE);
      } else {
        createBusStopMarkerOnMap(busStop, MapService.MARKER_BUSSTOP_CITY);
      }
    }
  }

  public void showTracks(JsonArray jsonArray) {
    List<BusStop> busStops = BusStopDAO.parseRouteBusStops(jsonArray.get(0).getAsJsonArray());
    List<RouteHolder> routeHolders = RouteDAO.parsePolylines(jsonArray.get(2).getAsJsonArray());
    List<RouteWariant> routeWariants = RouteDAO.parseRouteWariants(jsonArray.get(3).getAsJsonArray());
    int getPointOnTrackSize;
    int trackColor = 0;
    RouteHolder routeHolder;
    BusStop busStop;
    for (RouteWariant routeWariant : routeWariants) {
      if (routeWariant.getWariantId() == bundle.getInt("wariantId")) {
        getPointOnTrackSize = routeWariant.getPointsOnTrack().size();
        for (int i = 0; i < getPointOnTrackSize; i++) {
          routeHolder = routeHolders.get(routeWariant.getPointsOnTrack().get(i));
          busStop = busStops.get(routeWariant.getBusOnTrack().get(i));
          if (i == getPointOnTrackSize - 1) {
            MarkerService markerEndTrack = new MarkerService(
              map,
              routeHolder.getPoints().get(routeHolder.getPoints().size() - 1).getCoords(),
              0,
              BitmapDescriptorFactory.fromResource(R.drawable.ic_buspoint_red),
              new MarkerTag(busStop, MarkerTag.TYPE_BUSSTOPEND),
              true
            );
          } else if (busStop.getIdCity() != 0) {
            if (i == 0) {
              createBusStopMarkerOnMap(busStop, MapService.MARKER_BUSSTOP_START);
            } else {
              createBusStopMarkerOnMap(busStop, MapService.MARKER_BUSSTOP_ZONE);
            }
            trackColor = R.color.color_zone;
          } else {
            if (i == 0) {
              createBusStopMarkerOnMap(busStop, MapService.MARKER_BUSSTOP_START);
            } else {
              createBusStopMarkerOnMap(busStop, MapService.MARKER_BUSSTOP_CITY);
            }
            trackColor = R.color.color_city;
          }
          createPolylines(routeHolder.getPoints(), trackColor);
        }
        break;
      }
    }
  }

  private void showBusDetails(List<Object> vehicles) {
    boolean isTarget = true;
    Vehicle mainVehicle = null;
    if (cameraStatus.get(0)) {
      isTarget = false;
      busPosition = new MarkerService(map, new LatLng(0, 0), 10, null, false);
      busDirection = new MarkerService(map, new LatLng(0, 0), 10, null, true);
    }

    for (Object object : vehicles) {
      Vehicle vehicle = (Vehicle) object;
      mainVehicle = vehicle;
      if (!vehicle.getNumerLini().equals("")) {
        busDirection.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.vh_arrow));
        busPosition.setIcon(BitmapDescriptorFactory.fromBitmap(
          ImageUtils.createBusPinImage(activity, vehicle.getNumerLini())));
      } else {
        busDirection.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.vh_clock));
        busPosition.setIcon(BitmapDescriptorFactory.fromBitmap(ImageUtils.createLongBusPinImage(
          activity,
          vehicle.getNastNumLini(),
          String.valueOf(vehicle.getIleSekDoOdjazdu()))));
      }

      busPosition.setTag(new MarkerTag(vehicle, MarkerTag.TYPE_BUSPIN));
      busPosition.setPosition(new LatLng(vehicle.getSzerokosc(), vehicle.getDlugosc()));

      busDirection.setTag(new MarkerTag(vehicle, MarkerTag.TYPE_BUSCOMPASS));
      busDirection.setRotation(vehicle.getWektor());
      busDirection.setPosition(new LatLng(vehicle.getSzerokosc(), vehicle.getDlugosc()));

      if (isMarkerInfoOpened.get()) {
        busPosition.showInfoWindow();
      }
    }

    if (cameraStatus.get(0)) {
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mainVehicle.getSzerokosc(), mainVehicle.getDlugosc()), 13));
      setCameraMovingStatus(isTarget);
    }
  }

  public void getTrack() {
    int busLine = bundle.getInt("busLine");
    Route route = DatabaseUtils.getDatabase(activity).dbRoutesDAO().getRouteByBusLine(busLine);
    BusDAO.getBusTrackByLine(handler, String.valueOf(route.getId()), String.valueOf(busLine));
  }

  public void setSearchTrack(List<SearchResultPoint> searchResultPoints) {
    int searchResultPointsSize = searchResultPoints.size();
    SearchResultPoint searchResultPoint;
    SearchResultPoint prevSearchResultPoint = null;
    SearchResultPoint nextSearchResultPoint = null;
    List<RoutePoint> routePoints;

    BusStop busStop;
    for (int i = 0; i < searchResultPointsSize; i++) {
      searchResultPoint = searchResultPoints.get(i);
      routePoints = searchResultPoint.getPoints();
      busStop = new BusStop(searchResultPoint);

      if (i != 0) {
        prevSearchResultPoint = searchResultPoints.get(i - 1);
      }
      if (i != searchResultPointsSize - 1) {
        nextSearchResultPoint = searchResultPoints.get(i + 1);
      }

      if (searchResultPoint.getBusStopId() == 0) {
        if (i == 0) {
          createSinglePolyline(searchResultPoint.getCoords(), searchResultPoints.get(i + 1).getCoords(), R.color.error);
        } else {
          createSinglePolyline(searchResultPoint.getCoords(), searchResultPoints.get(i - 1).getCoords(), R.color.error);
        }
      }

      if (i == 0) {
        createBusStopMarkerOnMap(busStop, MapService.MARKER_BUSSTOP_START);
      } else if (i == searchResultPoints.size() - 1) {
        createBusStopMarkerOnMap(busStop, MapService.MARKER_BUSSTOP_END);
      } else {
        createBusStopMarkerOnMap(busStop, MapService.MARKER_BUSSTOP_TRACK);
        if (searchResultPoint.getZero1() != 0) {
          if (i < searchResultPointsSize - 1 && !nextSearchResultPoint.isChangeBus()) {
            createPolylines(routePoints, R.color.color_zone);
          }
        } else {
          if (i < searchResultPointsSize - 1 && !nextSearchResultPoint.isChangeBus()) {
            createPolylines(routePoints, R.color.color_city);
          }
        }
      }

      if (searchResultPoint.isChangeBus() && searchResultPoint.getBusStopId() != 0) {
        new MarkerService(
          map,
          new LatLng(searchResultPoint.getLng(), searchResultPoint.getLat()),
          1,
          BitmapDescriptorFactory.fromBitmap(ImageUtils.createBusChangeImage(activity, searchResultPoint.getBusLine())),
          new MarkerTag(null, MarkerTag.TYPE_ICON),
          false
        );

        new MarkerService(
          map,
          new LatLng(prevSearchResultPoint.getLng(), prevSearchResultPoint.getLat()),
          1,
          BitmapDescriptorFactory.fromResource(R.drawable.tp_walk),
          new MarkerTag(null, MarkerTag.TYPE_ICON),
          false
        );

        createSinglePolyline(new LatLng(searchResultPoint.getLng(), searchResultPoint.getLat()), new LatLng(prevSearchResultPoint.getLng(), prevSearchResultPoint.getLat()), R.color.error);
      }
    }
  }

  public void createBusStopMarkerOnMap(BusStop busStop, int type) {
    int icon = 0;
    int tagId = MarkerTag.TYPE_BUSSTOP;
    Log.i("TEST", "IdCity: " + busStop.getIdCity() + " | type + " + type);
    if (type == MapService.MARKER_BUSSTOP_START) {
      icon = R.drawable.ic_buspoint_green;
    } else if (type == MapService.MARKER_BUSSTOP_END) {
      icon = R.drawable.ic_buspoint_red;
      tagId = MarkerTag.TYPE_ICON;
    } else if ((busStop.getIdCity() != 0 && type == MapService.MARKER_BUSSTOP_TRACK) || type == MapService.MARKER_BUSSTOP_ZONE ){
      icon = R.drawable.ic_buspoint_yellow;
    } else {
      icon = R.drawable.ic_buspoint;
    }

    MarkerService markerEndTrack = new MarkerService(
      map,
      new LatLng(busStop.getLongitude(), busStop.getLatitude()),
      0,
      BitmapDescriptorFactory.fromResource(icon),
      new MarkerTag(busStop, tagId),
      true
    );
  }



  @SuppressLint("HandlerLeak")
  private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case GeoUtils.STATUS_CURRENT_POSITION:
          LatLng pos = (LatLng) msg.obj;
          MainActivity.lat_current = pos.latitude;
          MainActivity.lng_current = pos.longitude;
          break;
        case GeoUtils.UPDATE_POSITION:
          new MarkerService(
            map,
            new LatLng(MainActivity.lat_current, MainActivity.lng_current),
            0,
            BitmapDescriptorFactory.fromBitmap(ImageUtils.createYourPositionPin(activity, activity.getText(R.string.you_are_here).toString())),
            null,
            false);
          break;

        case BusDAO.BUS_STOP_BY_LINE:
          ListMediator listMediator = (ListMediator) msg.obj;
          showBusDetails(listMediator.getObjectList());
          break;

        case BusDAO.BUS_TRACK_BY_LINE:
          showTracks((JsonArray) msg.obj);
          break;
        case BusDAO.BUS_STOP_DETAILS:
          setBusStopInfo((BusStopInfoMapBox) msg.obj);
          break;
      }
    }
  };
}
