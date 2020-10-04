package com.softarea.mpktarnow.model;

import com.google.android.gms.maps.model.Marker;
import com.softarea.mpktarnow.data.remote.model.Departues;

public class BusStopInfoMapBox {
  private Departues departues;
  private Marker marker;
  private BusStopMapItem busStop;

  public BusStopInfoMapBox(Departues departues, Marker marker, BusStopMapItem busStop) {
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

  public BusStopMapItem getBusStop() {
    return busStop;
  }
}
