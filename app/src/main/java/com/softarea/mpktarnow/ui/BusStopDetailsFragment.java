package com.softarea.mpktarnow.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.adapters.BusAdapter;
<<<<<<< HEAD
import com.softarea.mpktarnow.data.remote.dao.BusDAO;
import com.softarea.mpktarnow.data.remote.model.Departues;
import com.softarea.mpktarnow.data.remote.model.RemoteDepartue;
import com.softarea.mpktarnow.model.DepartueItem;
import com.softarea.mpktarnow.model.db.DatabaseDepartue;
import com.softarea.mpktarnow.utils.DatabaseUtils;
import com.softarea.mpktarnow.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
=======
import com.softarea.mpktarnow.data.remote.dao.MpkDAO;
>>>>>>> master

public class BusStopDetailsFragment extends Fragment {
  private BusAdapter busAdapter;
  int busStopId;
  private SwipeRefreshLayout mSwipeRefreshLayout;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_bus_stop_details, container, false);

    busStopId = SlideBusStopFragment.busStopId;
    updateFragment(busStopId);

    RecyclerView busesList = root.findViewById(R.id.list_buses);
    busAdapter = new BusAdapter();
    busesList.setHasFixedSize(true);
    busesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    busesList.setAdapter(busAdapter);

    mSwipeRefreshLayout = root.findViewById(R.id.swiperefresh_items);
    mSwipeRefreshLayout.setOnRefreshListener(() -> {

      updateFragment(busStopId);

      final Handler handler = new Handler();
      handler.postDelayed(() -> {
        if(mSwipeRefreshLayout.isRefreshing()) {
          mSwipeRefreshLayout.setRefreshing(false);
        }
      }, 1000);
    });

    return root;
  }

  private void updateFragment(int id) {
    BusDAO.getNextDepartues(id, handlerGetNextDepartues);
  }

  @SuppressLint("HandlerLeak")
  private final Handler handlerGetNextDepartues = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      Departues departues = (Departues) msg.obj;
      int currentTime = TimeUtils.getCurrentTimeInMin();
      List<DatabaseDepartue> departuesFromDatabase = DatabaseUtils.getDatabase(getContext()).dbBusStopDAO().getNextDepartues(busStopId, currentTime, "RO");

      List<DepartueItem> result = new ArrayList<>();
      List<Integer> usedId = new ArrayList<>();

      for(RemoteDepartue remoteDepartue : departues.getDepartueList()) {
        result.add( new DepartueItem(remoteDepartue, currentTime));
        usedId.add( remoteDepartue.getWariantId());
      }

      for(DatabaseDepartue databaseDepartue :departuesFromDatabase) {
        Log.i("TEST", "ID: " + databaseDepartue.getDepartueId());
        boolean status = true;
        for( int i = 0; i < usedId.size(); i++ ) {
          int used = usedId.get(i);
          if( used == databaseDepartue.getWariantId()) {
            status = false;
            usedId.remove(i);

          }
        }
        if( status ) {
          String databaseDestination = DatabaseUtils.getDatabase(getContext()).dbBusStopDAO().getDestinationByRouteId(busStopId, databaseDepartue.getWariantId());
          int busLine = DatabaseUtils.getDatabase(getContext()).dbBusStopDAO().getBusLineFromId(databaseDepartue.getBusId());
          result.add(new DepartueItem(databaseDepartue, databaseDestination, busLine));
        }
      }

      busAdapter.update(result);
    }
  };
}
