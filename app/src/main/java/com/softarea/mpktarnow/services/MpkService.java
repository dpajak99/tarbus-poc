package com.softarea.mpktarnow.services;

import com.google.gson.JsonArray;
import com.softarea.mpktarnow.model.Departues;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MpkService {
  @GET("GetNextDepartues")
  Call<Departues> getSchedule(@Query("busStopId") String id);

  @GET("GetBusStopList?q=&ttId=0")
  Call<JsonArray> getCities();

  @GET("GetMapBusStopList?ttId=1")
  Call<JsonArray> getBusStopCoords();
}
