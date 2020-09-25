package com.softarea.mpktarnow.data.remote.model;

import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

@Xml(name = "Departues")
public class Departues {
  @Element(name = "N")
  N n;
  @Attribute(name = "time")
  String time;
  @Attribute(name = "i")
  int busStopId;
  @Element(name = "D")
  List<Departue> departueList;

  public String getTime() {
    return time;
  }

  public int getBusStopId() {
    return busStopId;
  }

  public List<Departue> getDepartueList() {
    return departueList;
  }

  public Departues() {
  }
}
