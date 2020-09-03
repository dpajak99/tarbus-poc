package com.softarea.mpktarnow.model;

public class MarkerTag {
  private Vehicle vehicle;
  private String type;

  public MarkerTag(Vehicle vehicle, String type) {
    this.vehicle = vehicle;
    this.type = type;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public String getType() {
    return type;
  }
}
