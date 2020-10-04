package com.softarea.mpktarnow.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.adapters.ScheduleBusLineAdapter;
import com.softarea.mpktarnow.model.BusLineStopMediator;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_schedule, container, false);

    List<BusLineStopMediator> busLineStopMediators = new ArrayList<>();
    for(String busLine :SlideBusStopFragment.busLinesFromBusStop) {
      busLineStopMediators.add(new BusLineStopMediator(SlideBusStopFragment.busStopId, SlideBusStopFragment.busStopNameGlobal,  busLine));
    }

    RecyclerView scheduleBusLineRecycler = root.findViewById(R.id.rv_schedule_all);
    ScheduleBusLineAdapter scheduleBusLineAdapter = new ScheduleBusLineAdapter(getActivity());
    scheduleBusLineRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    scheduleBusLineRecycler.setAdapter(scheduleBusLineAdapter);
    scheduleBusLineAdapter.update(busLineStopMediators);



    return root;
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
  }

}
