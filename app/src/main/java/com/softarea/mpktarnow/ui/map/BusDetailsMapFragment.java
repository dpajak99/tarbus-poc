package com.softarea.mpktarnow.ui.map;

import com.google.android.gms.maps.GoogleMap;
import com.softarea.mpktarnow.controllers.map.MapFragment;
import com.softarea.mpktarnow.services.GoogleMapService;
import com.softarea.mpktarnow.services.MapService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BusDetailsMapFragment extends MapFragment {
  ScheduledExecutorService e;

  @Override
  public void onMapReady(GoogleMap googleMap) {
    setMapService(new MapService(new GoogleMapService(requireActivity(), getView(), googleMap), getBundle()));

    e = Executors.newSingleThreadScheduledExecutor();
    e.scheduleAtFixedRate(this::refresh, 0, 1, TimeUnit.SECONDS);

    this.mapService.getTrack();
  }


  private void refresh() {
    this.mapService.getBusDetails();
  }

  @Override
  public void onStop() {
    super.onStop();
    e.shutdown();
  }
}
