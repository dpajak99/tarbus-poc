package com.softarea.mpktarnow.data.remote.model;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

@Xml(name = "VL")
public class VehiclesList {
  @Element
  List<P> jsonVehicles;

  public VehiclesList() {
  }

  public List<P> getJsonVehicles() {
    return jsonVehicles;
  }

  @Override
  public String toString() {
    return "VehiclesList{" +
      "jsonVehicles=" + jsonVehicles.toString() +
      '}';
  }
}
