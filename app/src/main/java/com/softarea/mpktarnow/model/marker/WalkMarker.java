package com.softarea.mpktarnow.model.marker;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.softarea.mpktarnow.R;

public class WalkMarker implements MainMarker {
  private static final int MARKER_ZINDEX = 10;

  private Marker marker;
  private FragmentActivity activity;
  private GoogleMap map;

  public WalkMarker(FragmentActivity activity, GoogleMap map, LatLng position) {
    this.activity = activity;
    this.map = map;
    this.marker = map.addMarker(new MarkerOptions()
      .position(position)
      .icon(BitmapDescriptorFactory.fromResource(R.drawable.tp_walk))
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
