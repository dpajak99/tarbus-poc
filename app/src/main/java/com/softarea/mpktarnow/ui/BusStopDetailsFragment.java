package com.softarea.mpktarnow.ui;

import android.os.Bundle;
import android.os.Handler;
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
import com.softarea.mpktarnow.data.remote.dao.MpkDAO;

public class BusStopDetailsFragment extends Fragment {
  public static BusAdapter busAdapter;
  private SwipeRefreshLayout mSwipeRefreshLayout;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_bus_stop_details, container, false);

    Bundle bundle = this.getArguments();
    int id = bundle.getInt("id");

    MpkDAO.getAndUpdateBusStopDetails(id);

    RecyclerView busesList = root.findViewById(R.id.list_buses);
    busAdapter = new BusAdapter();
    busesList.setHasFixedSize(true);
    busesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    busesList.setAdapter(busAdapter);




    mSwipeRefreshLayout = root.findViewById(R.id.swiperefresh_items);
    mSwipeRefreshLayout.setOnRefreshListener(() -> {

      MpkDAO.getAndUpdateBusStopDetails(id);

      final Handler handler = new Handler();
      handler.postDelayed(() -> {
        if(mSwipeRefreshLayout.isRefreshing()) {
          mSwipeRefreshLayout.setRefreshing(false);
        }
      }, 1000);
    });

    return root;
  }
}
