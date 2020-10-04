package com.softarea.mpktarnow.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.softarea.mpktarnow.R;
<<<<<<< HEAD
import com.softarea.mpktarnow.data.remote.model.RemoteDepartue;
=======
import com.softarea.mpktarnow.data.remote.model.Departue;
>>>>>>> master

import java.util.ArrayList;
import java.util.List;

public class MapScheduleAdapter implements GoogleMap.InfoWindowAdapter {

  private List<RemoteDepartue> remoteDepartues = new ArrayList<>();
  private String busStopName;
  private FragmentActivity activity;

  public MapScheduleAdapter(FragmentActivity activity) {
    this.activity = activity;
  }

  @Override
  public View getInfoWindow(Marker arg0) {

    return null;
  }

  @Override
  public View getInfoContents(Marker marker) {
    View v;
    v = activity.getLayoutInflater().inflate(R.layout.item_busstoppin, null);
    RecyclerView recyclerView = v.findViewById(R.id.list_buses_map);
    TextView name = v.findViewById(R.id.bus_stop_title);
    MapBusStopAdapter busAdapter = new MapBusStopAdapter(activity);

    name.setText(busStopName);

    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new GridLayoutManager(activity, 4));
    recyclerView.setAdapter(busAdapter);

    busAdapter.update(remoteDepartues);
    return v;
  }

  public void setRemoteDepartues(List<RemoteDepartue> remoteDepartues) {
    this.remoteDepartues = remoteDepartues;
  }

  public List<RemoteDepartue> getRemoteDepartues() {
    return remoteDepartues;
  }

  public void setBusStopName(String busStopName) {
    this.busStopName = busStopName;
  }
}

