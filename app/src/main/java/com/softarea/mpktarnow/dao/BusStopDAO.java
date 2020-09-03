package com.softarea.mpktarnow.dao;

import com.google.gson.JsonArray;
import com.softarea.mpktarnow.model.BusStop;

import java.util.ArrayList;
import java.util.List;

public class BusStopDAO {
  public static List<BusStop> parseRouteBusStops(JsonArray array) {
    List<BusStop> busStops = new ArrayList<>();
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
      busStops.add(busStop);
    }
    return busStops;
  }
}
