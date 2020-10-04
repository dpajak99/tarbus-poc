package com.softarea.mpktarnow.services;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
<<<<<<< HEAD
import android.util.Log;
import android.view.View;
=======
import android.view.View;
import android.widget.LinearLayout;
>>>>>>> master
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.gson.JsonArray;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.activities.MainActivity;
<<<<<<< HEAD
import com.softarea.mpktarnow.dao.BusStopDAO;
import com.softarea.mpktarnow.dao.RouteDAO;
import com.softarea.mpktarnow.data.remote.dao.BusDAO;
import com.softarea.mpktarnow.model.BigBusMarker;
import com.softarea.mpktarnow.model.BusStopMapItem;
import com.softarea.mpktarnow.model.ListMediator;
=======
import com.softarea.mpktarnow.data.remote.dao.BusDAO;
import com.softarea.mpktarnow.dao.BusStopDAO;
import com.softarea.mpktarnow.dao.RouteDAO;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.model.ListMediator;
import com.softarea.mpktarnow.model.Route;
>>>>>>> master
import com.softarea.mpktarnow.model.RouteHolder;
import com.softarea.mpktarnow.model.RoutePoint;
import com.softarea.mpktarnow.model.RouteWariant;
import com.softarea.mpktarnow.model.SearchResultPoint;
import com.softarea.mpktarnow.model.Vehicle;
<<<<<<< HEAD
import com.softarea.mpktarnow.model.db.BusStopListItem;
import com.softarea.mpktarnow.model.db.Route;
import com.softarea.mpktarnow.model.marker.BusChangeMarker;
=======
import com.softarea.mpktarnow.model.marker.BusChangeMarker;
import com.softarea.mpktarnow.model.marker.BusDirectionMarker;
import com.softarea.mpktarnow.model.marker.BusMarker;
>>>>>>> master
import com.softarea.mpktarnow.model.marker.BusStopMarker;
import com.softarea.mpktarnow.model.marker.MainMarker;
import com.softarea.mpktarnow.model.marker.WalkMarker;
import com.softarea.mpktarnow.utils.DatabaseUtils;
import com.softarea.mpktarnow.utils.GeoUtils;
import com.softarea.mpktarnow.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class MapService {
<<<<<<< HEAD
=======
  public static final int MARKER_BUSSTOP_START = 0;
  public static final int MARKER_BUSSTOP_END = 1;
  public static final int MARKER_BUSSTOP_CITY = 2;
  public static final int MARKER_BUSSTOP_ZONE = 3;
  public static final int MARKER_BUSSTOP_TRACK = 4;

>>>>>>> master
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

<<<<<<< HEAD
  public void moveCamera(LatLng latLng) {
=======
  public void moveCamera( LatLng latLng ) {
>>>>>>> master
    map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
  }

  public void showCurrentLocation() {
    GeoUtils.getCurrentLocation(handlerCurrentPosition, activity);
  }

  public void getBusDetails() {
<<<<<<< HEAD
    int intBusId = bundle.getInt("busId");
    int intBusLine = bundle.getInt("busLine");

    Log.i("TEST", "budId - " + intBusId + " | busLine: " + intBusLine);
    String stringBusId = String.valueOf(intBusId);
    String stringBusLine = String.valueOf(intBusLine);
    if( intBusId == 0 ) {
      stringBusId = "";
    }

    if( intBusLine == 0 ) {
      stringBusLine = "";
    }

    BusDAO.getBusStopByLine(handlerBusStopByLine, stringBusId , stringBusLine);
=======
    BusDAO.getBusStopByLine(handlerBusStopByLine, String.valueOf(bundle.getInt("busId")), bundle.getInt("busLine"));
>>>>>>> master
  }

  private void drawPolylines(List<RoutePoint> routePoints, int color) {
    int routePointsSize = routePoints.size();
    for (int j = 0; j < routePointsSize; j++) {
      if (j < routePointsSize - 1) {
        drawSinglePolyline(routePoints.get(j).getCoords(), routePoints.get(j + 1).getCoords(), color);
      }
    }
  }

  public void drawSinglePolyline(LatLng first, LatLng second, int color) {
    map.addPolyline(new PolylineOptions()
      .add(first, second)
      .width(15)
      .geodesic(true)
      .startCap(new RoundCap())
      .jointType(JointType.ROUND)
      .color(activity.getColor(color)));
  }

  public void setCurrentCoords() {
<<<<<<< HEAD
    view.findViewById(R.id.info_box).setVisibility(View.VISIBLE);
    view.findViewById(R.id.tv_viewfinder).setVisibility(View.VISIBLE);

=======
    LinearLayout boxInfo = view.findViewById(R.id.info_box);
    TextView viewFinder = view.findViewById(R.id.tv_viewfinder);
    boxInfo.setVisibility(View.VISIBLE);
    viewFinder.setVisibility(View.VISIBLE);
>>>>>>> master
    TextView cameraCoordsValue = view.findViewById(R.id.tv_camera_position);
    map.setOnCameraMoveListener(() -> {

      LatLng position = map.getCameraPosition().target;
<<<<<<< HEAD
      if (bundle.getInt("key") == MapService.BUNDLE_MAP_GET_FROM) {
=======
      if (bundle.getInt("key") == MapService.BUNDLE_MAP_GET_FROM ) {
>>>>>>> master
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
<<<<<<< HEAD
      return true;
=======
       return true;
>>>>>>> master
    });


  }

  public void showBusStops() {
    for (BusStopListItem busStop : DatabaseUtils.getDatabase(mapService.getActivity()).dbBusStopDAO().getAll()) {
      createBusStopMarkerOnMap(new BusStopMapItem(busStop));
    }
  }

  public void showTracks(JsonArray jsonArray) {
    BusStopMapItem busStop;

    List<BusStopMapItem> busStops = BusStopDAO.parseRouteBusStops(jsonArray.get(0).getAsJsonArray());
    List<RouteHolder> routeHolders = RouteDAO.parsePolylines(jsonArray.get(2).getAsJsonArray());
    List<RouteWariant> routeWariants = RouteDAO.parseRouteWariants(jsonArray.get(3).getAsJsonArray());

    for (RouteWariant routeWariant : routeWariants) {
      if (routeWariant.getWariantId() == bundle.getInt("wariantId")) {
        int getPointOnTrackSize = routeWariant.getPointsOnTrack().size();

        for (int i = 0; i < getPointOnTrackSize; i++) {
<<<<<<< HEAD
          RouteHolder routeHolder = routeHolders.get(routeWariant.getPointsOnTrack().get(i));
          if (i != getPointOnTrackSize - 1) {
            busStop = busStops.get(routeWariant.getBusOnTrack().get(i));
=======
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
>>>>>>> master
          } else {
            busStop = new BusStopMapItem(routeHolder.getPoints().get(routeHolder.getPoints().size() - 1).getCoords());
          }

          createBusStopMarkerOnMap(busStop);
          drawPolylines(routeHolder.getPoints(), getTrackColor(busStop.getIsCity()));
        }
        break;
      }
    }
  }

<<<<<<< HEAD
  public int getTrackColor( int isCity ) {
    if( isCity == 0 ) {
      return R.color.color_city;
    } else {
      return  R.color.color_zone;
    }
  }

  private List<BigBusMarker> bigBusMarkers = new ArrayList<>();
  private void showBusDetails(List<Vehicle> vehicles) {
    boolean isTarget = true;
    Vehicle mainVehicle = null;
    BigBusMarker bigBusMarker = null;
    if (cameraStatus.get(0)) {
      isTarget = false;
    }

    for (int i = 0; i < vehicles.size(); i++) {
      Vehicle vehicle = vehicles.get(i);
      mainVehicle = vehicle;
      if( bigBusMarkers.size() < vehicles.size() ) {
        bigBusMarker = new BigBusMarker(activity, map);
      } else {
        bigBusMarker = bigBusMarkers.get(i);
      }

      if (vehicle.isSetNumerLinii()) {
        bigBusMarker.setImageArrow(BitmapDescriptorFactory.fromResource(R.drawable.vh_arrow));
        bigBusMarker.setImagePin(BitmapDescriptorFactory.fromBitmap(
          ImageUtils.createBusPinImage(activity, vehicle.getNumerLini())));
      } else {
        bigBusMarker.setImageArrow(BitmapDescriptorFactory.fromResource(R.drawable.vh_clock));
        bigBusMarker.setImagePin(BitmapDescriptorFactory.fromBitmap(ImageUtils.createLongBusPinImage(
=======
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
>>>>>>> master
          activity,
          vehicle.getNastNumLini(),
          String.valueOf(vehicle.getIleSekDoOdjazdu()))));
      }

<<<<<<< HEAD
      bigBusMarker.setPosition(new LatLng(vehicle.getSzerokosc(), vehicle.getDlugosc()));
      bigBusMarker.setObject(vehicle);
      bigBusMarker.setRotation(vehicle.getWektor());

      bigBusMarkers.add(bigBusMarker);
      bigBusMarker.openIfWasOpened();
=======
      busPosition.getMarker().setPosition(new LatLng(vehicle.getSzerokosc(), vehicle.getDlugosc()));
      busPosition.setObject(vehicle);

      busDirection.getMarker().setRotation(vehicle.getWektor());
      busDirection.getMarker().setPosition(new LatLng(vehicle.getSzerokosc(), vehicle.getDlugosc()));

      if (busPosition.isOpen()) {
        busPosition.getMarker().showInfoWindow();
      }
>>>>>>> master
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
    SearchResultPoint prevSearchResultPoint = null;
    SearchResultPoint nextSearchResultPoint = null;

    for (int i = 0; i < searchResultPointsSize; i++) {
      SearchResultPoint searchResultPoint = searchResultPoints.get(i);
      List<RoutePoint> routePoints = searchResultPoint.getPoints();
      BusStopMapItem busStop = new BusStopMapItem(searchResultPoint);

      if (i != 0) {
        prevSearchResultPoint = searchResultPoints.get(i - 1);
      }
      if (i < searchResultPointsSize - 1) {
        nextSearchResultPoint = searchResultPoints.get(i + 1);
      }

      if (searchResultPoint.getBusStopId() == 0) {
        if (i == 0) {
          drawSinglePolyline(searchResultPoint.getCoords(), searchResultPoints.get(i + 1).getCoords(), R.color.error);
        } else {
          drawSinglePolyline(searchResultPoint.getCoords(), searchResultPoints.get(i - 1).getCoords(), R.color.error);
        }
      }
      drawSinglePolyline(new LatLng(searchResultPoint.getLng(), searchResultPoint.getLat()), new LatLng(prevSearchResultPoint.getLng(), prevSearchResultPoint.getLat()), R.color.error);

      createBusStopMarkerOnMap(busStop);
      if (i < searchResultPointsSize - 1 && !nextSearchResultPoint.isChangeBus()){
        drawPolylines(routePoints, busStop.getId());
      }

      if (searchResultPoint.isChangeBus() && searchResultPoint.getBusStopId() != 0) {
<<<<<<< HEAD
        new BusChangeMarker(activity, map, new LatLng(searchResultPoint.getLng(), searchResultPoint.getLat()), searchResultPoint.getBusLine());
        new WalkMarker(activity, map, new LatLng(prevSearchResultPoint.getLng(), prevSearchResultPoint.getLat()));
=======

        new BusChangeMarker(activity, map, new LatLng(searchResultPoint.getLng(), searchResultPoint.getLat()), searchResultPoint.getBusLine());
        new WalkMarker(activity, map,  new LatLng(prevSearchResultPoint.getLng(), prevSearchResultPoint.getLat()));

        createSinglePolyline(new LatLng(searchResultPoint.getLng(), searchResultPoint.getLat()), new LatLng(prevSearchResultPoint.getLng(), prevSearchResultPoint.getLat()), R.color.error);
>>>>>>> master
      }
    }
  }

<<<<<<< HEAD
  public void createBusStopMarkerOnMap(BusStopMapItem busStop) {
    new BusStopMarker(view, activity, map, busStop);
=======
  public void createBusStopMarkerOnMap(BusStop busStop, int type) {
    new BusStopMarker(view, activity, map, new LatLng(busStop.getLongitude(), busStop.getLatitude()), type, busStop);
>>>>>>> master
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
