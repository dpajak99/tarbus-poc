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
import com.softarea.mpktarnow.dao.BusStopDAO;
import com.softarea.mpktarnow.model.SearchConnectionCallback;
import com.softarea.mpktarnow.model.SearchResult;
import com.softarea.mpktarnow.services.RetrofitXmlClient;
import com.softarea.mpktarnow.utils.TimeUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {
  private  SearchConnectionResultAdapter searchConnectionResultAdapter;
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
        List<SearchResult> searchResults = BusStopDAO.parseSearchResults(connection);
        searchConnectionResultAdapter.updateArticles(searchResults);
      }

      @Override
      public void onFailure(Call<SearchConnectionCallback> call, Throwable t) {
        Log.i("TEST", "connection error: " + t.getMessage());
      }
    });

    return root;
  }
}
