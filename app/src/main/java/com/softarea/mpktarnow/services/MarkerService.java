package com.softarea.mpktarnow.services;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.softarea.mpktarnow.model.MarkerTag;

public class MarkerService {
  private Marker marker;
  private GoogleMap googleMap;

  public MarkerService(GoogleMap googleMap, LatLng position, int zIndex, MarkerTag tag, boolean anchor) {
    this.googleMap = googleMap;
    this.marker = googleMap.addMarker(
      new MarkerOptions()
        .position(position)
        .zIndex(zIndex));
    if (anchor) {
      this.marker.setAnchor(0.5f, 0.5f);
    }
    this.marker.setTag(tag);
  }

  public MarkerService(GoogleMap googleMap, LatLng position, int zIndex, BitmapDescriptor icon, MarkerTag tag,  float[] anchor) {
    this.googleMap = googleMap;
    this.marker = googleMap.addMarker(
      new MarkerOptions()
        .position(position)
        .icon(icon)
        .flat(true)
        .anchor(anchor[0], anchor[1])
        .zIndex(zIndex));
    this.marker.setTag(tag);
  }

  public MarkerService(GoogleMap googleMap, LatLng position, int zIndex, BitmapDescriptor icon, MarkerTag tag, boolean anchor) {
    this.googleMap = googleMap;
    this.marker = googleMap.addMarker(
      new MarkerOptions()
        .position(position)
        .icon(icon)
        .zIndex(zIndex));
    if (anchor) {
      this.marker.setAnchor(0.5f, 0.5f);
    }
    this.marker.setTag(tag);
  }

  public void setTag(MarkerTag tag) {
    this.marker.setTag(tag);
  }

  public void setIcon(BitmapDescriptor icon) {
    this.marker.setIcon(icon);
  }

  public void setRotation(float rotation) {
    this.marker.setRotation(rotation);
  }

  public void setPosition(LatLng position) {
    this.marker.setPosition(position);
  }

  public void showInfoWindow() {
    this.marker.showInfoWindow();
  }
}
