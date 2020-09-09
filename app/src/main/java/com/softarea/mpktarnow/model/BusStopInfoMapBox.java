package com.softarea.mpktarnow.model;

import com.google.android.gms.maps.model.Marker;

public class BusStopInfoMapBox {
  private Departues departues;
  private Marker marker;
  private BusStop busStop;

  public BusStopInfoMapBox(Departues departues, Marker marker, BusStop busStop) {
    this.departues = departues;
    this.marker = marker;
    this.busStop = busStop;
  }

  public Departues getDepartues() {
    return departues;
  }

  public Marker getMarker() {
    return marker;
  }

  public BusStop getBusStop() {
    return busStop;
  }
}
