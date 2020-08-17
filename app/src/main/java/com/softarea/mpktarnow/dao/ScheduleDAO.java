package com.softarea.mpktarnow.dao;

import android.util.Log;

import com.softarea.mpktarnow.model.Departues;
import com.softarea.mpktarnow.services.BusClient;
import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ScheduleDAO {
  public static void DeserializeFromXML(int id, Callback<Departues> callback) throws IOException {
    Log.i("TEST", "DeserializeFromXML - start");
    TikXml tikxml = new TikXml.Builder().exceptionOnUnreadXml(true).build();

    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("http://rozklad.komunikacja.tarnow.pl/Home/")
      .client(new OkHttpClient())
      .addConverterFactory(TikXmlConverterFactory.create(tikxml))
      .build();

    BusClient busClient = retrofit.create(BusClient.class);
    Log.i("TEST", "DeserializeFromXML - after retrofit");
    Call<Departues> call = busClient.getSchedule(String.valueOf(id) );
    Log.i("TEST", "DeserializeFromXML - after first call");
    call.enqueue(callback);
  }

  public static void DeserializeFromJSON(int id, Callback<List<List<Object>>> callback) throws IOException {
    Log.i("TEST", "DeserializeFromJSON - start");

    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("http://rozklad.komunikacja.tarnow.pl/Home/")
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    BusClient busClient = retrofit.create(BusClient.class);
    Log.i("TEST", "DeserializeFromJSON - after retrofit");
    Call<List<List<Object>>> call = busClient.getBusStops("1");
    Log.i("TEST", "DeserializeFromJSON - after first call");
    call.enqueue(callback);
  }
}
