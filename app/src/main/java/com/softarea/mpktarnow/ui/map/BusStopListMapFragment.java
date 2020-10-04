package com.softarea.mpktarnow.ui.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.softarea.mpktarnow.controllers.MapFragment;
import com.softarea.mpktarnow.services.GoogleMapService;
import com.softarea.mpktarnow.services.MapService;
import com.softarea.mpktarnow.statics.Statics;

public class BusStopListMapFragment extends MapFragment {
  @Override
  public void onMapReady(GoogleMap googleMap) {
    setMapService(new MapService(new GoogleMapService(requireActivity(), getView(), googleMap), getBundle()));
    this.mapService.showBusStops();
    this.mapService.moveCamera(new LatLng(Statics.centerLng, Statics.centerLat));
  }
}
