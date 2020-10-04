package com.softarea.mpktarnow.model;

import com.softarea.mpktarnow.model.db.BusStopListItem;

public class NearBusStop {
  private int meters;
  private BusStopListItem busStop;

  public NearBusStop(int meters, BusStopListItem busStop) {
    this.meters = meters;
    this.busStop = busStop;
  }

  public int getMeters() {
    return meters;
  }


  public BusStopListItem getBusStop() {
    return busStop;
  }

}
