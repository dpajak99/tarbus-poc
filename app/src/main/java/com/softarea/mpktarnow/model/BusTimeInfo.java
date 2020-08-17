package com.softarea.mpktarnow.model;

import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "S")
public class BusTimeInfo {
  @Attribute(name = "th")
  String th;
  @Attribute(name = "tm")
  String tm;
  @Attribute(name = "t")
  String t;
  @Attribute(name = "m")
  String m;
  @Attribute(name = "s")
  String s;
  @Attribute(name = "id")
  String id;
  @Attribute(name = "nb")
  String nb;
  @Attribute(name = "veh")
  String veh;
  @Attribute(name = "uw")
  String uw;
  @Attribute(name = "kuw")
  String kuw;


  public BusTimeInfo() {
  }

  public String getTh() {
    return th;
  }

  public String getTm() {
    return tm;
  }

  public String getT() {
    return t;
  }

  public String getM() {
    return m;
  }

  public String getS() {
    return s;
  }

  public String getId() {
    return id;
  }

  public String getNb() {
    return nb;
  }

  public String getVeh() {
    return veh;
  }

  public String getUw() {
    return uw;
  }

  public String getKuw() {
    return kuw;
  }
}
