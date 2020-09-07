package com.softarea.mpktarnow.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.adapters.SearchConnectionResultAdapter;
import com.softarea.mpktarnow.model.RoutePoint;
import com.softarea.mpktarnow.model.SearchConnectionCallback;
import com.softarea.mpktarnow.model.SearchResult;
import com.softarea.mpktarnow.model.SearchResultPoint;
import com.softarea.mpktarnow.services.RetrofitXmlClient;
import com.softarea.mpktarnow.utils.StringUtils;
import com.softarea.mpktarnow.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {
  SearchConnectionResultAdapter searchConnectionResultAdapter;
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

    RecyclerView searchResultList = root.findViewById(R.id.list_search_results);
    searchConnectionResultAdapter = new SearchConnectionResultAdapter();
    searchResultList.setHasFixedSize(true);
    searchResultList.setLayoutManager(new LinearLayoutManager(getActivity()));
    searchResultList.setAdapter(searchConnectionResultAdapter);

    Bundle bundle = this.getArguments();

    String lngFrom = String.valueOf(bundle.getDouble("lngFrom"));
    String latFrom = String.valueOf(bundle.getDouble("latFrom"));
    String latTo = String.valueOf(bundle.getDouble("latTo"));
    String lngTo = String.valueOf(bundle.getDouble("lngTo"));

    Log.i("TEST", lngFrom + " " + latFrom + " | " + lngTo + " " + latTo );

    String hour = bundle.getString("time");
    String date = bundle.getString("date");
    String lang = "pl";
    String c = "0";
    String transfers = "4";
    String mode = bundle.getString("mode");
    String carriers = "";
    String skipRoutes = "";
    String vehicleFlags = "";
    String walkMode = "undefined";

    Log.i("TEST", "DATA: " + TimeUtils.getCurrentTime() + TimeUtils.getCurrentDate());

    Call<SearchConnectionCallback> call = RetrofitXmlClient.getInstance().getMPKService().searchConnection(lngFrom, latFrom, lngTo, latTo, hour, date, lang, c, transfers, mode, carriers,skipRoutes,vehicleFlags,walkMode);
    call.enqueue(new Callback<SearchConnectionCallback>() {
      @Override
      public void onResponse(Call<SearchConnectionCallback> call, Response<SearchConnectionCallback> response) {
        SearchConnectionCallback connection = response.body();
        List<SearchResult> searchResults = new ArrayList<>();
        try {
          JSONArray resultWariants = new JSONArray(connection.getJson());
          for (int i = 0; i < resultWariants.length(); i++) {
            JSONObject searchResult = resultWariants.getJSONObject(i);
            JSONArray searchData = searchResult.optJSONArray("data");
            List<SearchResultPoint> searchResultPoints = new ArrayList<>();

            for(int j = 0; j < searchData.length(); j++) {
              List<RoutePoint> routePoints = new ArrayList<>();

              JSONArray searchPoint = searchData.getJSONArray(j);
              SearchResultPoint searchResultPoint = new SearchResultPoint(
                searchPoint.getInt(0),
                StringUtils.changeHashForLetters(searchPoint.getString(1)),
                searchPoint.getDouble(2),
                searchPoint.getDouble(3),
                searchPoint.getBoolean(4),
                searchPoint.getBoolean(5),
                searchPoint.getString(6),
                searchPoint.getString(7),
                routePoints,
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
          Log.i("TEST", "Results: " + searchResults.size());
          searchConnectionResultAdapter.updateArticles(searchResults);
        } catch (JSONException e) {
          e.printStackTrace();
          Log.i("TEST", e.toString());
        }
      }

      @Override
      public void onFailure(Call<SearchConnectionCallback> call, Throwable t) {
        Log.i("TEST", "connection error: " + t.getMessage());
      }
    });

    return root;
  }
}
