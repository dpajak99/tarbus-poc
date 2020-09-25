package com.softarea.mpktarnow.services;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

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
import com.softarea.mpktarnow.data.remote.dao.BusDAO;
import com.softarea.mpktarnow.dao.BusStopDAO;
import com.softarea.mpktarnow.dao.RouteDAO;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.model.ListMediator;
import com.softarea.mpktarnow.model.Route;
import com.softarea.mpktarnow.model.RouteHolder;
import com.softarea.mpktarnow.model.RoutePoint;
import com.softarea.mpktarnow.model.RouteWariant;
import com.softarea.mpktarnow.model.SearchResultPoint;
import com.softarea.mpktarnow.model.Vehicle;
import com.softarea.mpktarnow.model.marker.BusChangeMarker;
import com.softarea.mpktarnow.model.marker.BusDirectionMarker;
import com.softarea.mpktarnow.model.marker.BusMarker;
import com.softarea.mpktarnow.model.marker.BusStopMarker;
import com.softarea.mpktarnow.model.marker.MainMarker;
import com.softarea.mpktarnow.model.marker.WalkMarker;
import com.softarea.mpktarnow.utils.DatabaseUtils;
import com.softarea.mpktarnow.utils.GeoUtils;
import com.softarea.mpktarnow.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class MapService {
  public static final int MARKER_BUSSTOP_START = 0;
  public static final int MARKER_BUSSTOP_END = 1;
  public static final int MARKER_BUSSTOP_CITY = 2;
  public static final int MARKER_BUSSTOP_ZONE = 3;
  public static final int MARKER_BUSSTOP_TRACK = 4;

  public static final int BUNDLE_MAP_GET_FROM = 8;
  public static final int BUNDLE_MAP_GET_TO = 9;

  private GoogleMapService mapService;
  private GoogleMap map;
  private FragmentActivity activity;
  private Bundle bundle;
  private List<Boolean> cameraStatus = new ArrayList<>();

  private View view;


  public MapService(GoogleMapService mapService, Bundle bundle) {
    this.mapService = mapService;
    this.map = mapService.getMap();
    this.activity = mapService.getActivity();
    this.bundle = bundle;
    this.view = mapService.getView();
    setCameraMovingStatus(true);
  }

  public void setCameraMovingStatus(boolean status) {
    cameraStatus.clear();
    cameraStatus.add(status);
  }

  public void moveCamera( LatLng latLng ) {
    map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
  }

  public void showCurrentLocation() {
    GeoUtils.getCurrentLocation(handlerCurrentPosition, activity);
  }

  public void getBusDetails() {
    BusDAO.getBusStopByLine(handlerBusStopByLine, String.valueOf(bundle.getInt("busId")), bundle.getInt("busLine"));
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

  public void setCurrentCoords() {
    LinearLayout boxInfo = view.findViewById(R.id.info_box);
    TextView viewFinder = view.findViewById(R.id.tv_viewfinder);
    boxInfo.setVisibility(View.VISIBLE);
    viewFinder.setVisibility(View.VISIBLE);
    TextView cameraCoordsValue = view.findViewById(R.id.tv_camera_position);
    map.setOnCameraMoveListener(() -> {

      LatLng position = map.getCameraPosition().target;
      if (bundle.getInt("key") == MapService.BUNDLE_MAP_GET_FROM ) {
        MainActivity.lng_from = position.longitude;
        MainActivity.lat_from = position.latitude;
      } else {
        MainActivity.lng_to = position.longitude;
        MainActivity.lat_to = position.latitude;
      }
      cameraCoordsValue.setText(position.latitude + " " + position.longitude);
    });
  }

  public void setListeners() {
    map.setOnInfoWindowClickListener(marker -> {
      MainMarker mainMarker = (MainMarker) marker.getTag();
      mainMarker.onItemWindowClick();
    });

    mapService.getMap().setOnMarkerClickListener(marker -> {
      MainMarker mainMarker = (MainMarker) marker.getTag();
      mainMarker.onClick();
       return true;
    });


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
            new BusStopMarker(view, activity, map, routeHolder.getPoints().get(routeHolder.getPoints().size() - 1).getCoords(), MapService.MARKER_BUSSTOP_END, busStop);
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

  private MainMarker busPosition;
  private MainMarker busDirection;
  private void showBusDetails(List<Vehicle> vehicles) {
    boolean isTarget = true;
    Vehicle mainVehicle = null;

    if (cameraStatus.get(0)) {
      isTarget = false;
      busPosition = new BusMarker(activity, map, new LatLng(0, 0));
      busDirection = new BusDirectionMarker(map, new LatLng(0, 0));
    }

    for (Vehicle vehicle : vehicles) {
      mainVehicle = vehicle;
      if (!vehicle.getNumerLini().equals("")) {
        busDirection.getMarker().setIcon(BitmapDescriptorFactory.fromResource(R.drawable.vh_arrow));
        busPosition.getMarker().setIcon(BitmapDescriptorFactory.fromBitmap(
          ImageUtils.createBusPinImage(activity, vehicle.getNumerLini())));
      } else {
        busDirection.getMarker().setIcon(BitmapDescriptorFactory.fromResource(R.drawable.vh_clock));
        busPosition.getMarker().setIcon(BitmapDescriptorFactory.fromBitmap(ImageUtils.createLongBusPinImage(
          activity,
          vehicle.getNastNumLini(),
          String.valueOf(vehicle.getIleSekDoOdjazdu()))));
      }

      busPosition.getMarker().setPosition(new LatLng(vehicle.getSzerokosc(), vehicle.getDlugosc()));
      busPosition.setObject(vehicle);

      busDirection.getMarker().setRotation(vehicle.getWektor());
      busDirection.getMarker().setPosition(new LatLng(vehicle.getSzerokosc(), vehicle.getDlugosc()));

      if (busPosition.isOpen()) {
        busPosition.getMarker().showInfoWindow();
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
    BusDAO.getBusTrackByLine(handlerBusTrackByLine, String.valueOf(route.getId()), String.valueOf(busLine));
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

        new BusChangeMarker(activity, map, new LatLng(searchResultPoint.getLng(), searchResultPoint.getLat()), searchResultPoint.getBusLine());
        new WalkMarker(activity, map,  new LatLng(prevSearchResultPoint.getLng(), prevSearchResultPoint.getLat()));

        createSinglePolyline(new LatLng(searchResultPoint.getLng(), searchResultPoint.getLat()), new LatLng(prevSearchResultPoint.getLng(), prevSearchResultPoint.getLat()), R.color.error);
      }
    }
  }

  public void createBusStopMarkerOnMap(BusStop busStop, int type) {
    new BusStopMarker(view, activity, map, new LatLng(busStop.getLongitude(), busStop.getLatitude()), type, busStop);
  }

  //STATUS_CURRENT_POSITION
  @SuppressLint("HandlerLeak")
  private final Handler handlerCurrentPosition = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      LatLng pos = (LatLng) msg.obj;
      MainActivity.lat_current = pos.latitude;
      MainActivity.lng_current = pos.longitude;

      new MarkerService(
        map,
        new LatLng(MainActivity.lat_current, MainActivity.lng_current),
        0,
        BitmapDescriptorFactory.fromBitmap(ImageUtils.createYourPositionPin(activity, activity.getText(R.string.you_are_here).toString())),
        null,
        false);
    }
  };

  //BUS_STOP_BY_LINE
  @SuppressLint("HandlerLeak")
  private final Handler handlerBusStopByLine = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      ListMediator<Vehicle> listMediator = (ListMediator<Vehicle>) msg.obj;
      showBusDetails(listMediator.getObjectList());
    }
  };

  //BUS_TRACK_BY_LINE
  @SuppressLint("HandlerLeak")
  private final Handler handlerBusTrackByLine = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      showTracks((JsonArray) msg.obj);
    }
  };


}
