package com.softarea.mpktarnow.utils;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.adapters.MapBusAdapter;
import com.softarea.mpktarnow.adapters.MapScheduleAdapter;
import com.softarea.mpktarnow.dao.MpkDAO;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.model.Departues;
import com.softarea.mpktarnow.model.MarkerTag;
import com.softarea.mpktarnow.model.Route;
import com.softarea.mpktarnow.model.Vehicle;
import com.softarea.mpktarnow.model.VehiclesList;
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
  public static void showBusStops(View view, FragmentActivity activity, GoogleMap mMap) {
    List<BusStop> busStops = DatabaseUtils.getDatabase(activity).dbBusStopDAO().getAll();
    MapScheduleAdapter mapScheduleAdapter;

    mMap.setOnInfoWindowClickListener(arg0 -> {
      Bundle result = new Bundle();
      result.putInt("id", Integer.parseInt(arg0.getTitle()));
      Navigation.findNavController(view).navigate(R.id.navigation_bus_stop_details, result);
    });

    for (BusStop busStop : busStops) {
      int icon_resource = R.drawable.ic_buspoint;
      if (busStop.getIdCity() != 1) {
        icon_resource = R.drawable.ic_buspoint_yellow;
      }
      LatLng coords_curr_pos = new LatLng(busStop.getLongitude(), busStop.getLatitude());

      mMap.addMarker(
        new MarkerOptions()
          .position(coords_curr_pos)
          .title(String.valueOf(busStop.getId()))
          .icon(BitmapDescriptorFactory.fromResource(icon_resource)));
    }

    mapScheduleAdapter = new MapScheduleAdapter(activity);
    mMap.setInfoWindowAdapter(mapScheduleAdapter);
    mMap.setOnMarkerClickListener(arg0 -> {

      mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
      Call<Departues> call = RetrofitXmlClient.getInstance().getMPKService().getSchedule(arg0.getTitle());
      call.enqueue(new Callback<Departues>() {
        @Override
        public void onResponse(Call<Departues> call, Response<Departues> response) {
          Departues departues = response.body();

          mapScheduleAdapter.setDepartues(departues.getDepartueList());
          mapScheduleAdapter.setBusStopName(DatabaseUtils.getDatabase(activity).dbBusStopDAO().getBusStopById(arg0.getTitle()).getName());

          arg0.showInfoWindow();
        }

        @Override
        public void onFailure(Call<Departues> call, Throwable t) {
          Log.i("TEST", "DeserializeFromXML - onFailure : " + t.toString());
        }
      });
      return true;
    });
  }

  public MapUtils() {
  }

  public static void showBusDetails(FragmentActivity activity, Bundle bundle, GoogleMap mMap) {

    AtomicBoolean isMarkerInfoOpened = new AtomicBoolean(false);
    MapBusAdapter mapBusAdapter = new MapBusAdapter(activity);
    mMap.setInfoWindowAdapter(mapBusAdapter);

    mMap.setOnMarkerClickListener(arg0 -> {
      MarkerTag tag = (MarkerTag) arg0.getTag();
      Vehicle vehicle = tag.getVehicle();
      Log.i("TEST", "TAG: " + tag.getType() + " " + tag.getVehicle().toString());

      int busDeviation = vehicle.getOdchylenie();
      Log.i("TEST", "getOdchylenie: " + busDeviation);
      if (busDeviation < 0) {
        mapBusAdapter.setBusDelayInfo("Aktualne przyśpieszenie");
        busDeviation *= -1;
      } else {
        mapBusAdapter.setBusDelayInfo("Aktualne opóźnienie");
      }

      if (busDeviation / 60 < 1) {
        mapBusAdapter.setBusDelayValue(" < 1 min");
      } else {
        mapBusAdapter.setBusDelayValue(" " + (int) busDeviation / 60 + " min");
        if (busDeviation / 60 < 4) {
          mapBusAdapter.setBusDelayColor(R.color.success);
        } else {
          mapBusAdapter.setBusDelayColor(R.color.error);
        }
      }

      mapBusAdapter.setBusDestination(vehicle.getCechy2());

      if (tag.getType().contains("busPosition") && isMarkerInfoOpened.get()) {
        arg0.hideInfoWindow();
        isMarkerInfoOpened.set(false);
      } else if (tag.getType().contains("busPosition") && !isMarkerInfoOpened.get()) {
        arg0.showInfoWindow();
        isMarkerInfoOpened.set(true);
      }

      return true;
    });

    mMap.setOnInfoWindowCloseListener(marker -> {
      isMarkerInfoOpened.set(false);
    });

    Marker busPosition = mMap.addMarker(
      new MarkerOptions()
        .position(new LatLng(50.012458, 20.988236))
        .zIndex(10));

    Marker busDirection = mMap.addMarker(
      new MarkerOptions()
        .position(new LatLng(50.012458, 20.988236))
        .flat(true)
        .anchor(0.5f, 0.5f)
        .zIndex(10));

    ScheduledExecutorService e = Executors.newSingleThreadScheduledExecutor();
    e.scheduleAtFixedRate(() ->

    {
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

            MarkerTag markerTag = new MarkerTag(vehicle, "busPosition");
            busPosition.setTag(markerTag);
            busPosition.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
            busPosition.setPosition(new LatLng(vehicle.getSzerokosc(), vehicle.getDlugosc()));

            busDirection.setIcon(BitmapDescriptorFactory.fromResource(icon));
            busDirection.setRotation(vehicle.getWektor());
            busDirection.setPosition(new LatLng(vehicle.getSzerokosc(), vehicle.getDlugosc()));

            if (isMarkerInfoOpened.get()) {
              busPosition.showInfoWindow();
            }
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(vehicle.getSzerokosc(), vehicle.getDlugosc())));
          }

        }

        @Override
        public void onFailure(Call<VehiclesList> call, Throwable t) {
          Log.i("TEST", "DeserializeFromXML - onFailure : " + t.toString());
        }
      });
    }, 0, 10, TimeUnit.SECONDS);
  }

  public static void showTrack(FragmentActivity activity, Bundle bundle, GoogleMap mMap) {
    Route route = DatabaseUtils.getDatabase(activity).dbRoutesDAO().getRouteByBusLine(bundle.getInt("busLine"));
    Call<JsonArray> call = RetrofitJsonClient.getInstance().getMPKService().getTracks(String.valueOf(route.getId()), String.valueOf(bundle.getInt("busLine")), "0");
    call.enqueue(new Callback<JsonArray>() {
      @Override
      public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
        JsonArray jsonArray = response.body();
        JsonArray array = jsonArray.get(0).getAsJsonArray();

        for (int i = 0; i < array.size(); i++) {
          JsonArray busArray = array.get(i).getAsJsonArray();
          BusStop busStop = new BusStop(
            busArray.get(0).getAsInt(),
            busArray.get(1).getAsString(),
            busArray.get(2).getAsString(),
            busArray.get(3).getAsDouble(),
            busArray.get(4).getAsDouble(),
            busArray.get(5).getAsInt()
          );
          int icon_resource = R.drawable.ic_buspoint;
          if (busStop.getIdCity() != 0) {
            icon_resource = R.drawable.ic_buspoint_yellow;
          }
          LatLng coords_curr_pos = new LatLng(busStop.getLongitude(), busStop.getLatitude());

          mMap.addMarker(
            new MarkerOptions()
              .position(coords_curr_pos)
              .title(String.valueOf(busStop.getId()))
              .zIndex(0)
              .icon(BitmapDescriptorFactory.fromResource(icon_resource)));
        }

      }

      @Override
      public void onFailure(Call<JsonArray> call, Throwable t) {
        Log.i("TEST", "DeserializeFromXML - onFailure : " + t.toString());
      }
    });
  }


}
