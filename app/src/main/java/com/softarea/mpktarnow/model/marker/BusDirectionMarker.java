package com.softarea.mpktarnow.model.marker;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class BusDirectionMarker implements MainMarker {
  private static final int MARKER_ZINDEX = 10;

  private Marker marker;
  private GoogleMap map;

  public BusDirectionMarker(GoogleMap map, LatLng position) {
    this.map = map;
    this.marker = map.addMarker(new MarkerOptions()
      .position(position)
      .anchor(0.5f, 0.5f)
      .zIndex(MARKER_ZINDEX));

    marker.setTag(this);
  }

  @Override
  public void onClick() {

  }

  @Override
  public void onItemWindowClick() {

  }

  @Override
  public void setObject(Object object) {

  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public Marker getMarker() {
    return marker;
  }
}
