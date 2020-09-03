package com.softarea.mpktarnow.model;

import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "D")
public class Departue {
  @Attribute(name = "i")
  int id;
  @Attribute(name = "di")
  int departueId;
  @Attribute(name = "n")
  int busId;
  @Attribute(name = "t")
  int t;  /* JAKIŚ CZAS */
  @Attribute(name = "r")
  int busLine;
  @Attribute(name = "d")
  String busDirection;
  @Attribute(name = "dd")
  String dd;
  @Attribute(name = "p")
  String p;
  @Attribute(name = "kn")
  String kn;
  @Attribute(name = "vr")
  int remainingTime;
  @Attribute(name = "m")
  int m;
  @Attribute(name = "v")
  String time;
  @Attribute(name = "vn")
  String vn;

  public Departue() {
  }

  public Departue(int id, int departueId, int busId, int t, int busLine, String busDirection, String dd, String p, String kn, int remainingTime, int m, String time, String vn) {
    this.id = id;
    this.departueId = departueId;
    this.busId = busId;
    this.t = t;
    this.busLine = busLine;
    this.busDirection = busDirection;
    this.dd = dd;
    this.p = p;
    this.kn = kn;
    this.remainingTime = remainingTime;
    this.m = m;
    this.time = time;
    this.vn = vn;
  }

  public int getId() {
    return id;
  }

  public int getDepartueId() {
    return departueId;
  }

  public int getBusId() {
    return busId;
  }

  public int getT() {
    return t;
  }

  public int getBusLine() {
    return busLine;
  }

  public String getBusDirection() {
    return busDirection;
  }

  public String getDd() {
    return dd;
  }

  public String getP() {
    return p;
  }

  public String getKn() {
    return kn;
  }

  public int getRemainingTime() {
    return remainingTime;
  }

  public int getM() {
    return m;
  }

  public String getTime() {
    return time;
  }

  public String getVn() {
    return vn;
  }

  @Override
  public String toString() {
    return "Departue{" +
      "id=" + id +
      ", departueId=" + departueId +
      ", n=" + busId +
      ", t=" + t +
      ", busLine=" + busLine +
      ", busDirection='" + busDirection + '\'' +
      ", dd='" + dd + '\'' +
      ", p='" + p + '\'' +
      ", kn='" + kn + '\'' +
      ", remainingTime=" + remainingTime +
      ", m=" + m +
      ", time='" + time + '\'' +
      ", vn='" + vn + '\'' +
      '}';
  }
}
