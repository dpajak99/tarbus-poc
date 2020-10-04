package com.softarea.mpktarnow.model;

import com.google.android.gms.maps.model.LatLng;
import com.softarea.mpktarnow.model.db.BusStopListItem;

public class BusStopMapItem implements BusStop {
  private int id;
  private int isCity;
  private double latitude;
  private double longitude;
  private String name;

  public BusStopMapItem(SearchResultPoint searchResultPoint) {
    this.id = searchResultPoint.getBusStopId();
    this.isCity = searchResultPoint.getIsCity();
    this.latitude = searchResultPoint.getLat();
    this.longitude = searchResultPoint.getLng();
    this.name = searchResultPoint.getBusStopName();
  }

  public BusStopMapItem(BusStopListItem busStopListItem) {
    this.id = busStopListItem.getId();
    this.isCity = busStopListItem.getIsCity();
    this.latitude = busStopListItem.getLatitude();
    this.longitude = busStopListItem.getLongitude();
    this.name = busStopListItem.getName();
  }

  public BusStopMapItem(int id, String name, double latitude, double longitude, int idCity) {
    this.id = id;
    this.isCity = idCity;
    this.latitude = latitude;
    this.longitude = longitude;
    this.name = name;
  }

  public BusStopMapItem(LatLng position) {
    this.id = 0;
    this.isCity = 0;
    this.latitude = position.latitude;
    this.longitude = position.longitude;
    this.name = "NULL";
  }

  public int getId() {
    return id;
  }

  public int getIsCity() {
    return isCity;
  }

  public LatLng getPosition() {
    return new LatLng(latitude, longitude);
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getName() {
    return name;
  }
}
