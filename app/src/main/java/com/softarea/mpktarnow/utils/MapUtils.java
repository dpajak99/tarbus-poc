package com.softarea.mpktarnow.utils;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.gson.JsonArray;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.adapters.MapBusAdapter;
import com.softarea.mpktarnow.adapters.MapScheduleAdapter;
import com.softarea.mpktarnow.dao.BusStopDAO;
import com.softarea.mpktarnow.dao.MpkDAO;
import com.softarea.mpktarnow.dao.RouteDAO;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.model.Departues;
import com.softarea.mpktarnow.model.MarkerTag;
import com.softarea.mpktarnow.model.Route;
import com.softarea.mpktarnow.model.RouteHolder;
import com.softarea.mpktarnow.model.RouteWariant;
import com.softarea.mpktarnow.model.Vehicle;
import com.softarea.mpktarnow.model.VehiclesList;
import com.softarea.mpktarnow.services.GoogleMapService;
import com.softarea.mpktarnow.services.RetrofitJsonClient;
import com.softarea.mpktarnow.services.RetrofitXmlClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapUtils {
  AtomicBoolean isMarkerInfoOpened = new AtomicBoolean(false);
  GoogleMapService mapService;
  GoogleMap map;
  FragmentActivity activity;
  Bundle bundle;

  public MapUtils(GoogleMapService mapService, Bundle bundle) {
    this.mapService = mapService;
    this.map = mapService.getMap();
    this.activity = mapService.getActivity();
    this.bundle = bundle;
  }

  public void setListeners() {
    map.setOnInfoWindowClickListener(marker -> {
      MarkerTag tag = (MarkerTag) marker.getTag();
      switch (tag.getType()) {
        case MarkerTag.TYPE_BUSSTOP:
          BusStop busStop = (BusStop) tag.getObject();
          Bundle result = new Bundle();
          result.putInt("id", busStop.getId());
          Navigation.findNavController(mapService.getView()).navigate(R.id.navigation_bus_stop_details, result);
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
          MapScheduleAdapter mapScheduleAdapter = new MapScheduleAdapter(activity);
          map.setInfoWindowAdapter(mapScheduleAdapter);
          map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));

          Call<Departues> call = RetrofitXmlClient.getInstance().getMPKService().getSchedule(String.valueOf(busStop.getId()));
          call.enqueue(new Callback<Departues>() {
            @Override
            public void onResponse(Call<Departues> call, Response<Departues> response) {
              Departues departues = response.body();
              mapScheduleAdapter.setDepartues(departues.getDepartueList());
              mapScheduleAdapter.setBusStopName(busStop.getName());

              marker.showInfoWindow();
            }

            @Override
            public void onFailure(Call<Departues> call, Throwable t) {
              AlertUtils.alert(activity, activity.getString(R.string.error_download_shedule));
            }
          });
          return true;
      }

      return true;
    });

    mapService.getMap().setOnInfoWindowCloseListener(marker -> {
      isMarkerInfoOpened.set(false);
    });
  }

  public void showBusStops() {
    List<BusStop> busStops = DatabaseUtils.getDatabase(mapService.getActivity()).dbBusStopDAO().getAll();

    for (BusStop busStop : busStops) {
      int icon_resource = R.drawable.ic_buspoint;
      if (busStop.getIdCity() != 1) {
        icon_resource = R.drawable.ic_buspoint_yellow;
      }

      Marker markerBusStop = mapService.getMap().addMarker(
        new MarkerOptions()
          .position(new LatLng(busStop.getLongitude(), busStop.getLatitude()))
          .icon(BitmapDescriptorFactory.fromResource(icon_resource)));
      markerBusStop.setTag(new MarkerTag(busStop, MarkerTag.TYPE_BUSSTOP));
    }

  }

  public void showBusDetails() {
    Marker busPosition = map.addMarker(
      new MarkerOptions()
        .position(new LatLng(50.012458, 20.988236))
        .zIndex(10));

    Marker busDirection = map.addMarker(
      new MarkerOptions()
        .position(new LatLng(50.012458, 20.988236))
        .flat(true)
        .anchor(0.5f, 0.5f)
        .zIndex(10));

    List<Boolean> cameraStatus = new ArrayList<>();
    cameraStatus.add(true);
    ScheduledExecutorService e = Executors.newSingleThreadScheduledExecutor();
    e.scheduleAtFixedRate(() -> {
      List<Vehicle> vehicles = new ArrayList<>();

      Call<VehiclesList> call = RetrofitXmlClient.getInstance().getMPKService().getVehicles(String.valueOf(bundle.getInt("busLine")), "", String.valueOf(bundle.getInt("busId")));
      //Call<VehiclesList> call = RetrofitXmlClient.getInstance().getMPKService().getVehicles("", "", "");
      //Call<VehiclesList> call = RetrofitXmlClient.getInstance().getMPKService().getVehicles("9", "", "");
      call.enqueue(new Callback<VehiclesList>() {
        @Override
        public void onResponse(Call<VehiclesList> call, Response<VehiclesList> response) {
          VehiclesList jsonVehicles = response.body();

          for (int i = 0; i < jsonVehicles.getJsonVehicles().size(); i++) {
            vehicles.add(MpkDAO.parseJsonToVehicle(jsonVehicles.getJsonVehicles().get(i).getContent()));
            Vehicle vehicle = vehicles.get(i);

            Bitmap bitmap;
            int icon = R.drawable.vh_arrow;
            if (!vehicle.getNumerLini().equals("")) {
              bitmap = ImageUtils.createBusPinImage(activity, vehicle.getNumerLini());
            } else {
              bitmap = ImageUtils.createLongBusPinImage(activity, vehicle.getNastNumLini(), String.valueOf(vehicle.getIleSekDoOdjazdu()));
              icon = R.drawable.vh_clock;
            }

            MarkerTag markerTag = new MarkerTag(vehicle, MarkerTag.TYPE_BUSPIN);
            busPosition.setTag(markerTag);
            busPosition.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
            busPosition.setPosition(new LatLng(vehicle.getSzerokosc(), vehicle.getDlugosc()));

            markerTag = new MarkerTag(vehicle, MarkerTag.TYPE_BUSCOMPASS);
            busDirection.setIcon(BitmapDescriptorFactory.fromResource(icon));
            busDirection.setRotation(vehicle.getWektor());
            busDirection.setPosition(new LatLng(vehicle.getSzerokosc(), vehicle.getDlugosc()));

            if (isMarkerInfoOpened.get()) {
              busPosition.showInfoWindow();
            }
            if(cameraStatus.get(0)) {
              map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(vehicle.getSzerokosc(), vehicle.getDlugosc())));
              cameraStatus.clear();
              cameraStatus.add(false);
            }
          }

        }

        @Override
        public void onFailure(Call<VehiclesList> call, Throwable t) {
          Log.i("TEST", "DeserializeFromXML - onFailure : " + t.toString());
        }
      });
    }, 0, 10, TimeUnit.SECONDS);
  }

  public void showTrack() {
    Route route = DatabaseUtils.getDatabase(activity).dbRoutesDAO().getRouteByBusLine(bundle.getInt("busLine"));
    Call<JsonArray> call = RetrofitJsonClient.getInstance().getMPKService().getTracks(String.valueOf(route.getId()), String.valueOf(bundle.getInt("busLine")), "1");
    call.enqueue(new Callback<JsonArray>() {
      @Override
      public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
        JsonArray jsonArray = response.body();
        List<BusStop> busStops = BusStopDAO.parseRouteBusStops(jsonArray.get(0).getAsJsonArray());
        List<RouteHolder> routeHolders = RouteDAO.parsePolylines(jsonArray.get(2).getAsJsonArray());
        List<RouteWariant> routeWariants = RouteDAO.parseRouteWariants(jsonArray.get(3).getAsJsonArray());

        for (RouteWariant routeWariant : routeWariants) {
          if (routeWariant.getWariantId() == bundle.getInt("wariantId")) {
            for (int i = 0; i < routeWariant.getPointsOnTrack().size(); i++) {
              int trackColor = R.color.color_city;
              BusStop busStop = busStops.get(routeWariant.getBusOnTrack().get(i));
              int icon_resource = R.drawable.ic_buspoint;
              if (busStop.getIdCity() != 0) {
                icon_resource = R.drawable.ic_buspoint_yellow;
                trackColor = R.color.color_zone;
              }
              Marker markerBusStop = mapService.getMap().addMarker(
                new MarkerOptions()
                  .position(new LatLng(busStop.getLongitude(), busStop.getLatitude()))
                  .zIndex(0)
                  .anchor(0.5f, 0.5f)
                  .icon(BitmapDescriptorFactory.fromResource(icon_resource)));
              markerBusStop.setTag(new MarkerTag(busStop, MarkerTag.TYPE_BUSSTOP));

              if (i < routeWariant.getPointsOnTrack().size() - 1) {
                RouteHolder routeHolder = routeHolders.get(routeWariant.getPointsOnTrack().get(i));
                for (int j = 0; j < routeHolder.getPoints().size(); j++) {
                  if (j < routeHolder.getPoints().size() - 1) {
                    Polyline line = map.addPolyline(new PolylineOptions()
                      .add(routeHolder.getPoints().get(j + 1).getCoords(), routeHolder.getPoints().get(j).getCoords())
                      .width(15)
                      .geodesic(true)
                      .startCap(new RoundCap())
                      .jointType(JointType.ROUND)
                      .color(activity.getColor(trackColor)));
                  }
                }
              }
            }
            break;
          }
        }
      }

      @Override
      public void onFailure(Call<JsonArray> call, Throwable t) {
        Log.i("TEST", "DeserializeFromXML - onFailure : " + t.toString());
      }
    });
  }


}
