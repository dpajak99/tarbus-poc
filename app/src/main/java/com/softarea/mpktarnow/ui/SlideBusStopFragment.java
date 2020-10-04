package com.softarea.mpktarnow.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.adapters.BusLineListAdapter;
import com.softarea.mpktarnow.adapters.SlideBusStopAdapter;
import com.softarea.mpktarnow.utils.DatabaseUtils;

import java.util.List;

public class SlideBusStopFragment extends Fragment {
  private ViewPager2 viewPager;
  private FragmentStateAdapter pagerAdapter;
  public static int busStopId;
  public static String busStopNameGlobal;
  public static List<String> busLinesFromBusStop;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_slide_bus_stop, container, false);

    Bundle bundle = this.getArguments();
    busStopId = bundle.getInt("id");
    busStopNameGlobal = bundle.getString("BUS_STOP_NAME");

    RecyclerView busList = root.findViewById(R.id.rl_bus_line_list);
    TextView tv_bus_stop_name = root.findViewById(R.id.tv_bus_stop_name);

    tv_bus_stop_name.setText(bundle.getString("BUS_STOP_NAME"));
    BusLineListAdapter busLineListAdapter = new BusLineListAdapter();
    busList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    busList.setHasFixedSize(true);
    busList.setAdapter(busLineListAdapter);

    busLinesFromBusStop = DatabaseUtils.getDatabase(getContext()).dbBusStopDAO().getBusLines(busStopId);
    busLineListAdapter.update(busLinesFromBusStop);

    viewPager = root.findViewById(R.id.pager_bus_stop);
    pagerAdapter = new SlideBusStopAdapter(this);
    viewPager.setAdapter(pagerAdapter);

    TabLayout tabLayout = root.findViewById(R.id.tab_slide_bus_stop);
    new TabLayoutMediator(tabLayout, viewPager,
      (tab, position) -> {
        if (position == 0) {
          tab.setText("NAJBLIŻSZE");
        } else {
          tab.setText("ROZKŁAD");
        }
      }
    ).attach();

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
