package com.softarea.mpktarnow.model;

import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "R")
public class Bus {
  @Attribute(name = "nr")
  String nr;
  @Attribute(name = "dir")
  String dir;
  @Attribute(name = "vt")
  String vt;
  @Attribute(name = "vuw")
  String vuw;
  @Element(name = "S")
  BusTimeInfo s;

  public String getNr() {
    return nr;
  }

  public String getDir() {
    return dir;
  }

  public String getVt() {
    return vt;
  }

  public String getVuw() {
    return vuw;
  }

  public BusTimeInfo getS() {
    return s;
  }
}
