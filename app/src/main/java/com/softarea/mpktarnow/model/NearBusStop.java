package com.softarea.mpktarnow.model;

public class NearBusStop {
  private int meters;
  private BusStop busStop;

  public NearBusStop(int meters, BusStop busStop) {
    this.meters = meters;
    this.busStop = busStop;
  }

  public int getMeters() {
    return meters;
  }


  public BusStop getBusStop() {
    return busStop;
  }

}
