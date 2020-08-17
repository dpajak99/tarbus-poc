package com.softarea.mpktarnow.model;

import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

@Xml(name = "Day")
public class Day {
  @Attribute(name = "type")
  String type;
  @Attribute(name = "desc")
  String dayType;
  @Element
  List<Bus> R;

  public Day() {
  }

  public String getType() {
    return type;
  }

  public String getDayType() {
    return dayType;
  }

  public List<Bus> getBuses() {
    return R;
  }
}
