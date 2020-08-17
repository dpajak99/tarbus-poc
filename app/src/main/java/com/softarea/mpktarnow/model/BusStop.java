package com.softarea.mpktarnow.model;


import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "Stop")
public class BusStop {
  @Attribute(name = "name")
  String name;
  @Attribute(name = "id")
  String id;
  @Attribute(name = "ds")
  String ds;
  @Element(name = "Day")
  Day day;

  public BusStop() {
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public String getAirCondition() {
    return ds;
  }

  public Day getDay() {
    return day;
  }

  public BusStop(String name, String id, String ds, Day day) {
    this.name = name;
    this.id = id;
    this.ds = ds;
    this.day = day;
  }
}
