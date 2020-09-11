package com.softarea.mpktarnow.model;

public class NearBusStop {
  private int meters;
  private float bearing;
  private BusStop busStop;

  public NearBusStop(int meters, float bearing, BusStop busStop) {
    this.meters = meters;
    this.bearing = bearing;
    this.busStop = busStop;
  }

  public int getMeters() {
    return meters;
  }

  public float getBearing() {
    return bearing;
  }

  public BusStop getBusStop() {
    return busStop;
  }

  public void setMeters(int meters) {
    this.meters = meters;
  }

  public void setBearing(float bearing) {
    this.bearing = bearing;
  }
}
