package com.softarea.mpktarnow.dao;

import com.google.gson.JsonArray;
import com.softarea.mpktarnow.model.RouteHolder;
import com.softarea.mpktarnow.model.RoutePoint;
import com.softarea.mpktarnow.model.RouteWariant;

import java.util.ArrayList;
import java.util.List;

public class RouteDAO {
  public static List<RouteWariant> parseRouteWariants(JsonArray array) {
    List<RouteWariant> routeWariants = new ArrayList<>();
    for (int i = 0; i < array.size(); i++) {
      JsonArray jsonElements = array.get(i).getAsJsonArray();
      List<Integer> busOnTrack = new ArrayList<>();
      List<Integer> pointsOnTrack = new ArrayList<>();
      JsonArray jsonTrack = jsonElements.get(6).getAsJsonArray();
      JsonArray jsonBusOnTrack = jsonTrack.get(0).getAsJsonArray();
      JsonArray jsonPointsOnTrack = jsonTrack.get(1).getAsJsonArray();

      for (int j = 0; j < jsonBusOnTrack.size(); j++) {
        busOnTrack.add(jsonBusOnTrack.get(j).getAsInt());
      }
      for (int j = 0; j < jsonPointsOnTrack.size(); j++) {
        pointsOnTrack.add(jsonPointsOnTrack.get(j).getAsInt());
      }

      routeWariants.add(new RouteWariant(
        jsonElements.get(0).getAsInt(),
        jsonElements.get(0).getAsInt(),
        jsonElements.get(0).getAsInt(),
        jsonElements.get(0).getAsString(),
        jsonElements.get(0).getAsString(),
        jsonElements.get(0).getAsString(),
        busOnTrack,
        pointsOnTrack,
        jsonElements.get(0).getAsInt(),
        jsonElements.get(0).getAsString(),
        jsonElements.get(0).getAsInt()
      ));
    }
    return routeWariants;
  }

  public static List<RouteHolder> parsePolylines(JsonArray array) {
    List<RouteHolder> routeHolders = new ArrayList<>();
    double tmp = 0;

    for (int i = 0; i < array.size(); i++) {
      List<RoutePoint> routePoints = new ArrayList<>();
      JsonArray routeElement = array.get(i).getAsJsonArray();
      JsonArray routeJsonPoints = routeElement.get(3).getAsJsonArray();

      for (int j = 0; j < routeJsonPoints.size(); j++) {
        if (j % 2 == 0) {
          tmp = routeJsonPoints.get(j).getAsDouble();
        } else {
          routePoints.add(new RoutePoint(tmp, routeJsonPoints.get(j).getAsDouble()));
        }
      }
      routeHolders.add(new RouteHolder(routeElement.get(0).getAsInt(), routeElement.get(1).getAsInt(), routeElement.get(2).getAsInt(), routePoints));
    }

    return routeHolders;
  }
}
