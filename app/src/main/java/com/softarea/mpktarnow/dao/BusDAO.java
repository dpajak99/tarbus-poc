package com.softarea.mpktarnow.dao;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.model.Marker;
import com.google.gson.JsonArray;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.model.BusStopInfoMapBox;
import com.softarea.mpktarnow.model.Departues;
import com.softarea.mpktarnow.model.ListMediator;
import com.softarea.mpktarnow.model.VehiclesList;
import com.softarea.mpktarnow.services.RetrofitJsonClient;
import com.softarea.mpktarnow.services.RetrofitXmlClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusDAO {
  public static void getBusStopByLine( Handler handler, String busId, int busLine) {
    Log.i("TEST", "getBusStopByLine: start");
    if (busId.equals("0")) {
      busId = "";
    }
    List<Object> vehicles = new ArrayList<>();
    Call<VehiclesList> call = RetrofitXmlClient.getInstance().getMPKService().getVehicles(String.valueOf(busLine), "", busId);
    //Call<VehiclesList> call = RetrofitXmlClient.getInstance().getMPKService().getVehicles("", "", "");
    //Call<VehiclesList> call = RetrofitXmlClient.getInstance().getMPKService().getVehicles("9", "", "");
    call.enqueue(new Callback<VehiclesList>() {
      @Override
      public void onResponse(Call<VehiclesList> call, Response<VehiclesList> response) {
        Log.i("TEST", "getBusStopByLine: onResponse");
        if (!response.headers().get("content-length").equals("15")) {
          VehiclesList jsonVehicles = response.body();
          for (int i = 0; i < jsonVehicles.getJsonVehicles().size(); i++) {
            vehicles.add(MpkDAO.parseJsonToVehicle(jsonVehicles.getJsonVehicles().get(i).getContent()));
          }
          sendMessageToUi(handler, new ListMediator(vehicles));
        }
      }

      @Override
      public void onFailure(Call<VehiclesList> call, Throwable t) {
        Log.i("TEST", "DeserializeFromXML - onFailure : " + t.toString());
      }
    });
  }

  public static void getBusTrackByLine( Handler handler, String busId, String busLine) {
    Call<JsonArray> call = RetrofitJsonClient.getInstance().getMPKService().getTracks(busId, busLine, "1");
    call.enqueue(new Callback<JsonArray>() {
      @Override
      public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
        JsonArray jsonArray = response.body();
        sendMessageToUi(handler, jsonArray);
      }

      @Override
      public void onFailure(Call<JsonArray> call, Throwable t) {
        Log.i("TEST", "DeserializeFromXML - onFailure : " + t.toString());
      }
    });
  }

  public static void getBusStopInfo(Handler handler, int id, Marker marker, BusStop busStop) {
    Call<Departues> call = RetrofitXmlClient.getInstance().getMPKService().getSchedule(String.valueOf(id));
    call.enqueue(new Callback<Departues>() {
      @Override
      public void onResponse(Call<Departues> call, Response<Departues> response) {
        Departues departues = response.body();
        BusStopInfoMapBox busStopInfoMapBox = new BusStopInfoMapBox(departues, marker, busStop);
        sendMessageToUi(handler, busStopInfoMapBox);
      }

      @Override
      public void onFailure(Call<Departues> call, Throwable t) {
        //AlertUtils.alert(activity, activity.getString(R.string.error_download_shedule));
      }
    });
  }

  private static void sendMessageToUi(Handler handler, Object s) {
    Log.i("TEST", "sendMessageToUi: sendMessageToUi");
    Message message = handler.obtainMessage();
    message.obj = s;
    handler.sendMessage(message);
  }
}
