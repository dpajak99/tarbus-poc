package com.softarea.mpktarnow.services;

import com.softarea.mpktarnow.model.Departues;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BusClient {
  @GET("GetNextDepartues")
  Call<Departues> getSchedule(@Query("busStopId") String id);

  @GET("GetMapBusStopList")
  Call<List<List<Object>>> getBusStops(@Query("ttId") String id);
}
