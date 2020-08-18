package com.softarea.mpktarnow.dao;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.softarea.mpktarnow.database.AppDatabase;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.model.City;
import com.softarea.mpktarnow.model.Departues;
import com.softarea.mpktarnow.services.RetrofitJsonClient;
import com.softarea.mpktarnow.services.RetrofitXmlClient;
import com.softarea.mpktarnow.ui.BusStopDetailsFragment;
import com.softarea.mpktarnow.ui.HomeFragment;
import com.softarea.mpktarnow.utils.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MpkDAO {
  public static void getAndUpdateBusStopDetails(int id) {
    Call<Departues> call = RetrofitXmlClient.getInstance().getMPKService().getSchedule(String.valueOf(id));
    call.enqueue(new Callback<Departues>() {
      @Override
      public void onResponse(Call<Departues> call, Response<Departues> response) {
        BusStopDetailsFragment.busAdapter.clear();
        Departues departues = response.body();
        BusStopDetailsFragment.busAdapter.update(departues.getDepartueList());
      }

      @Override
      public void onFailure(Call<Departues> call, Throwable t) {
        Log.i("TEST", "DeserializeFromXML - onFailure : " + t.toString());
      }
    });
  }

  public static void getAndSaveCities(Context context) {

    HomeFragment.pd.setMessage("Trwa pobieranie rozkładu jazdy");
    HomeFragment.pd.show();

    Call<JsonArray> call = RetrofitJsonClient.getInstance().getMPKService().getCities();
    call.enqueue(new Callback<JsonArray>() {
      @Override
      public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
        AppDatabase db = DatabaseUtils.getDatabase(context);
        JsonArray array = response.body();
        for (int i = 0; i < array.size(); i++) {
          JsonArray jCity = array.get(i).getAsJsonArray();
          int cityId = jCity.get(0).getAsInt();
          String cityName = jCity.get(1).getAsString();
          JsonArray jBusStops = jCity.get(2).getAsJsonArray();
          db.dbCitiesDAO().insert(new City(cityId, cityName));

          for (int j = 0; j < jBusStops.size(); j++) {
            JsonArray jBusStop = jBusStops.get(j).getAsJsonArray();
            int busId = jBusStop.get(0).getAsInt();
            String busName = jBusStop.get(1).getAsString();
            String busNumber = jBusStop.get(2).getAsString();
            int busZone = jBusStop.get(3).getAsInt();
            int busTimeTable = jBusStop.get(4).getAsInt();
            String busShowNum = jBusStop.get(5).getAsString();

            db.dbBusStopDAO().insert(new BusStop(busId, cityId, "", "", busName, busNumber, busZone, busTimeTable, busShowNum));
          }
        }
        getAndSaveBusStopsCoords(context);
      }

      @Override
      public void onFailure(Call<JsonArray> call, Throwable t) {
        Log.i("TEST", "DeserializeFromJSON - onFailure" + t.toString());
      }
    });
  }

  public static void getAndSaveBusStopsCoords(Context context) {
    Call<JsonArray> call = RetrofitJsonClient.getInstance().getMPKService().getBusStopCoords();
    call.enqueue(new Callback<JsonArray>() {
      @Override
      public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
        JsonArray array = response.body();
        for (int i = 0; i < array.size(); i++) {
          JsonArray jBusStops = array.get(i).getAsJsonArray();
          int id = jBusStops.get(0).getAsInt();
          String latitude = jBusStops.get(4).getAsString();
          String longitude = jBusStops.get(5).getAsString();

          Log.i("TEST", jBusStops.get(4).getAsString() + " - " + jBusStops.get(5).getAsString());

          DatabaseUtils.getDatabase(context).dbBusStopDAO().updateCoords( latitude, longitude, id );

          if( i == array.size() - 1 ) {
            List<BusStop> busStops = DatabaseUtils.getDatabase(context).dbBusStopDAO().getAll();
            HomeFragment.scheduleAdapter.updateArticles(busStops);
            HomeFragment.pd.hide();
          }
        }
      }

      @Override
      public void onFailure(Call<JsonArray> call, Throwable t) {
        Log.i("TEST", "DeserializeFromJSON - onFailure" + t.toString());
      }
    });
  }
}
