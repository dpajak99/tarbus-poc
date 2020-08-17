package com.softarea.mpktarnow.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.adapters.ScheduleAdapter;
import com.softarea.mpktarnow.dao.ScheduleDAO;
import com.softarea.mpktarnow.database.model.BusStopDB;
import com.softarea.mpktarnow.database.model.ScheduleTokenDB;
import com.softarea.mpktarnow.utils.DatabaseUtils;
import com.softarea.mpktarnow.utils.DateUtils;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
  ScheduleAdapter scheduleAdapter;
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_home, container, false);

    setHasOptionsMenu(true);

    RecyclerView scheduleList = root.findViewById(R.id.list_schedules);
    scheduleAdapter = new ScheduleAdapter(getActivity());
    scheduleList.setHasFixedSize(true);
    scheduleList.setLayoutManager(new LinearLayoutManager(getActivity()));
    scheduleList.setAdapter(scheduleAdapter);

    SearchView searchView = root.findViewById(R.id.search_view);
    searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        return false;
      }
      @Override
      public boolean onQueryTextChange(String newText) {
        scheduleAdapter.getFilter().filter(newText);
        return false;
      }
    });

    Log.i("TEST", DateUtils.min2HHMM(553));

    if(DatabaseUtils.getDatabase(getContext()).scheduleTokenDBDAO().getScheduleTokenCount() == 0 ) {
        try {
          Log.i("TEST", "DeserializeFromJSON - try - start");
          ScheduleDAO.DeserializeFromJSON(1, new Callback<List<List<Object>> >() {
            @Override
            public void onResponse(Call<List<List<Object>> >  call, Response<List<List<Object>>> response) {
              Log.i("TEST", "DeserializeFromJSON - onResponse - start");
              List<List<Object>> busStops = response.body();
              Log.i("TEST", busStops.toString());

              for(int i = 0; i < busStops.size(); i++ ) {
                DatabaseUtils.getDatabase(getContext()).busStopDBDAO().insert(new BusStopDB(
                  (int) Math.round((Double) busStops.get(i).get(0)) ,
                  (String) busStops.get(i).get(1),
                  (String) busStops.get(i).get(2),
                  (Double) busStops.get(i).get(3),
                  (Double) busStops.get(i).get(4),
                  (Double) busStops.get(i).get(5),
                  (Double) busStops.get(i).get(6),
                  (String) busStops.get(i).get(7),
                  (Double) busStops.get(i).get(8),
                  (Double) busStops.get(i).get(9)));
              }
            }

            @Override
            public void onFailure(Call<List<List<Object>>> call, Throwable t) {
              Log.i("TEST", "DeserializeFromJSON - onFailure" + t.toString());
            }
          });
          //Log.i("TEST", "HomeFragment - " + schedules.get(0).toString());
          //schedules.add(schedule);
        } catch (IOException e) {
          Log.i("TEST", "error : " + e);
          e.printStackTrace();
        }

      DatabaseUtils.getDatabase(getContext()).scheduleTokenDBDAO().setScheduleToken(new ScheduleTokenDB(0, 20));
    } else {
      List<BusStopDB> busStopDBS = DatabaseUtils.getDatabase(getContext()).busStopDBDAO().getAll();
      Log.i("TEST", "BusStop: " + busStopDBS.get(0).toString());
      scheduleAdapter.updateArticles(busStopDBS);
    }
    return root;
  }
}
