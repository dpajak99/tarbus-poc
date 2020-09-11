package com.softarea.mpktarnow.ui;

import android.annotation.SuppressLint;
import android.hardware.GeomagneticField;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.activities.MainActivity;
import com.softarea.mpktarnow.adapters.NearBusStopAdapter;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.model.NearBusStop;
import com.softarea.mpktarnow.utils.DatabaseUtils;
import com.softarea.mpktarnow.utils.GeoUtils;
import com.softarea.mpktarnow.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class NearFragment extends Fragment {
  NearBusStopAdapter nearBusStopAdapter;
  List<BusStop> busStops;
  GeomagneticField geoField;

  private LinearLayoutManager llm;
  private float currentDegree = 0f;


  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_near, container, false);

    llm = new LinearLayoutManager(getActivity());
    RecyclerView nearBusStopList = root.findViewById(R.id.list_nearest_busstops);
    nearBusStopAdapter = new NearBusStopAdapter(getActivity());
    nearBusStopList.setHasFixedSize(true);
    nearBusStopList.setLayoutManager(llm);
    nearBusStopList.setAdapter(nearBusStopAdapter);

    busStops = DatabaseUtils.getDatabase(getContext()).dbBusStopDAO().getAll();

    GeoUtils.getCurrentLocation(handler, getContext(), GeoUtils.STATUS_CURRENT_POSITION);

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

  private void showNearestBusStops() {
    List<NearBusStop> nearBusStops = new ArrayList<>();
    nearBusStops.clear();
    for (BusStop busStop : busStops) {
      nearBusStops.add(new NearBusStop(
        MathUtils.calcDistanse(MainActivity.lat_current, MainActivity.lng_current, busStop.getLongitude(), busStop.getLatitude()),
        busStop
      ));
    }
    nearBusStopAdapter.update(nearBusStops);
  }


  @SuppressLint("HandlerLeak")
  private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case GeoUtils.STATUS_CURRENT_POSITION:
          LatLng pos = (LatLng) msg.obj;
          MainActivity.lat_current = pos.latitude;
          MainActivity.lng_current = pos.longitude;

          showNearestBusStops();
          break;
      }
    }
  };
}
