package com.softarea.mpktarnow.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class SearchResultPoint {
  private int busStopId;
  private String busStopName;
  private double lat;
  private double lng;
  private boolean isEnterBus;
  private boolean isChangeBus;
  private String busLine;
  private String type;
  private List<RoutePoint> points;
  private int isCity;
  private int zero2;
  private int zero3;
  private int timeInSec1;
  private int timeInSec2;
  private String date;

  public SearchResultPoint(int busStopId, String busStopName, double lat, double lng, boolean isEnterBus, boolean isChangeBus, String busLine, String type, List<RoutePoint> points, int isCity, int zero2, int zero3, int timeInSec1, int timeInSec2, String date) {
    this.busStopId = busStopId;
    this.busStopName = busStopName;
    this.lat = lat;
    this.lng = lng;
    this.isEnterBus = isEnterBus;
    this.isChangeBus = isChangeBus;
    this.busLine = busLine;
    this.type = type;
    this.points = points;
    this.isCity = isCity;
    this.zero2 = zero2;
    this.zero3 = zero3;
    this.timeInSec1 = timeInSec1;
    this.timeInSec2 = timeInSec2;
    this.date = date;
  }

  @Override
  public String toString() {
    return "SearchResultPoint{" +
      "busStopId=" + busStopId +
      ", busStopName='" + busStopName + '\'' +
      ", lat=" + lat +
      ", lng=" + lng +
      ", isEnterBus=" + isEnterBus +
      ", isChangeBus=" + isChangeBus +
      ", busLine='" + busLine + '\'' +
      ", type='" + type + '\'' +
      ", points=" + points +
      ", zero1=" + isCity +
      ", zero2=" + zero2 +
      ", zero3=" + zero3 +
      ", timeInSec1=" + timeInSec1 +
      ", timeInSec2=" + timeInSec2 +
      ", date='" + date + '\'' +
      '}';
  }

  public int getBusStopId() {
    return busStopId;
  }

  public String getBusStopName() {
    return busStopName;
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  public LatLng getCoords() {
    return new LatLng(this.lng, this.lat);
  }

  public boolean isEnterBus() {
    return isEnterBus;
  }

  public boolean isChangeBus() {
    return isChangeBus;
  }

  public String getBusLine() {
    return busLine;
  }

  public String getType() {
    return type;
  }

  public List<RoutePoint> getPoints() {
    return points;
  }

  public int getIsCity() {
    return isCity;
  }

  public int getZero2() {
    return zero2;
  }

  public int getZero3() {
    return zero3;
  }

  public int getTimeInSec1() {
    return timeInSec1;
  }

  public int getTimeInSec2() {
    return timeInSec2;
  }

  public String getDate() {
    return date;
  }
}
