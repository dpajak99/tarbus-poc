package com.softarea.mpktarnow.ui.map;

import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.controllers.MapFragment;
import com.softarea.mpktarnow.services.GoogleMapService;
import com.softarea.mpktarnow.services.MapService;
import com.softarea.mpktarnow.statics.Statics;

public class GetCoordsMapFragment extends MapFragment {
  @Override
  public void onMapReady(GoogleMap googleMap) {
    setMapService(new MapService(new GoogleMapService(requireActivity(), getView(), googleMap), getBundle()));
    this.mapService.setCurrentCoords();
    this.mapService.moveCamera(new LatLng(Statics.centerLng, Statics.centerLat));

    Button buttonSearchOnMap = getView().findViewById(R.id.button_select_location);
    buttonSearchOnMap.setOnClickListener(view -> {
      getFragmentManager().popBackStackImmediate();
    });
  }
}
