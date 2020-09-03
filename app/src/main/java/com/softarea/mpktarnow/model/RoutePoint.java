package com.softarea.mpktarnow.model;

import com.google.android.gms.maps.model.LatLng;

public class RoutePoint {
  double latitude;
  double longtitude;

  public RoutePoint(double latitude, double longtitude) {
    this.latitude = latitude;
    this.longtitude = longtitude;
  }

  public LatLng getCoords() {
    return new LatLng(this.longtitude, this.latitude);
  }
}
