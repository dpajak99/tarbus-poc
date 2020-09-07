package com.softarea.mpktarnow.services;

import com.google.gson.JsonArray;
import com.softarea.mpktarnow.model.Departues;
import com.softarea.mpktarnow.model.SearchConnectionCallback;
import com.softarea.mpktarnow.model.VehiclesList;

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

  @GET("CNR_GetVehicles")
  Call<VehiclesList> getVehicles(@Query("r") String busLine, @Query("d") String destination, @Query("nb") String busId );

  @GET("GetRouteList?ttId=0")
  Call<JsonArray> getRouteList();

  @GET("GetTracks")
  Call<JsonArray> getTracks( @Query("routeId") String routeId, @Query("ttId") String ttId, @Query("transits") String transits );

  @GET("SearchConnection")
  Call<SearchConnectionCallback> searchConnection(@Query("lng1") String lng1, @Query("lat1") String lat1, @Query("lng2") String lng2, @Query("lat2") String lat2,
                                                  @Query("hour") String hour, @Query("date") String date, @Query("lang") String lang, @Query("c") String c,
                                                  @Query("transfers") String transfers, @Query("mode") String mode, @Query("carriers") String carriers,
                                                  @Query("skipRoutes") String skipRoutes, @Query("vehicleFlags") String vehicleFlags, @Query("walkMode") String walkMode);
}
