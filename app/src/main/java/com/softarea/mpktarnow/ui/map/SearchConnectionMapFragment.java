package com.softarea.mpktarnow.ui.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.softarea.mpktarnow.activities.MainActivity;
import com.softarea.mpktarnow.controllers.map.MapFragment;
import com.softarea.mpktarnow.services.GoogleMapService;
import com.softarea.mpktarnow.services.MapService;
import com.softarea.mpktarnow.statics.Statics;

public class SearchConnectionMapFragment extends MapFragment {
  @Override
  public void onMapReady(GoogleMap googleMap) {
    setMapService(new MapService(new GoogleMapService(requireActivity(), getView(), googleMap), getBundle()));
    this.mapService.setSearchTrack(MainActivity.searchConnectionList);
    this.mapService.moveCamera(new LatLng(Statics.centerLng, Statics.centerLat));
  }
}
