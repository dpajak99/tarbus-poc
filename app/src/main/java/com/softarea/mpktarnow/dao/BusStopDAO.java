package com.softarea.mpktarnow.dao;

import android.util.Log;

import com.google.gson.JsonArray;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.model.RoutePoint;
import com.softarea.mpktarnow.model.SearchConnectionCallback;
import com.softarea.mpktarnow.model.SearchResult;
import com.softarea.mpktarnow.model.SearchResultPoint;
import com.softarea.mpktarnow.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

  public static List<SearchResult> parseSearchResults( SearchConnectionCallback connection ) {
    List<SearchResult> searchResults = new ArrayList<>();
    try {
      JSONArray resultWariants = new JSONArray(connection.getJson());
      Log.i("TEST", resultWariants.toString());
      for (int i = 0; i < resultWariants.length(); i++) {
        JSONObject searchResult = resultWariants.getJSONObject(i);
        JSONArray searchData = searchResult.optJSONArray("data");
        List<SearchResultPoint> searchResultPoints = new ArrayList<>();

        for(int j = 0; j < searchData.length(); j++) {
          List<RoutePoint> routePointsList = new ArrayList<>();

          JSONArray searchPoint = searchData.getJSONArray(j);
          JSONArray routePoints = searchPoint.getJSONArray(8);
          routePointsList.add(new RoutePoint(searchPoint.getDouble(2), searchPoint.getDouble(3)));
          for( int k = 0; k < routePoints.length(); k++ ) {
            JSONObject jsonRoutePoint = routePoints.getJSONObject(k);
            routePointsList.add( new RoutePoint(jsonRoutePoint.optDouble("lng"), jsonRoutePoint.optDouble("lat")));
          }

          SearchResultPoint searchResultPoint = new SearchResultPoint(
            searchPoint.getInt(0),
            StringUtils.changeHashForLetters(searchPoint.getString(1)),
            searchPoint.getDouble(2),
            searchPoint.getDouble(3),
            searchPoint.getBoolean(4),
            searchPoint.getBoolean(5),
            searchPoint.getString(6),
            searchPoint.getString(7),
            routePointsList,
            searchPoint.getInt(9),
            searchPoint.getInt(10),
            searchPoint.getInt(11),
            searchPoint.getInt(12),
            searchPoint.getInt(13),
            searchPoint.getString(14)
          );
          searchResultPoints.add(searchResultPoint);
        }
        searchResults.add( new SearchResult(searchResult.optString("id"),  searchResultPoints));
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return searchResults;
  }
}
