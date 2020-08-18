package com.softarea.mpktarnow.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "BusStop")
public class BusStop {
  @PrimaryKey
  int id;
  @ColumnInfo(name = "idCity")
  int idCity;
  @ColumnInfo(name = "latitude")
  String latitude;
  @ColumnInfo(name = "longitude")
  String longitude;
  @ColumnInfo(name = "name")
  String name;
  @ColumnInfo(name = "number")
  String number;
  @ColumnInfo(name = "zone")
  int zone;
  @ColumnInfo(name = "timetableType")
  int timetableType;
  @ColumnInfo(name = "showNum")
  String showNum;

  public BusStop(int id, int idCity, String latitude, String longitude, String name, String number, int zone, int timetableType, String showNum) {
    this.id = id;
    this.idCity = idCity;
    this.latitude = latitude;
    this.longitude = longitude;
    this.name = name;
    this.number = number;
    this.zone = zone;
    this.timetableType = timetableType;
    this.showNum = showNum;
  }

  @Override
  public String toString() {
    return "BusStop{" +
      "id=" + id +
      ", idCity=" + idCity +
      ", latitude=" + latitude +
      ", longitude=" + longitude +
      ", name='" + name + '\'' +
      ", number='" + number + '\'' +
      ", zone=" + zone +
      ", timetableType=" + timetableType +
      ", showNum='" + showNum + '\'' +
      '}';
  }

  public int getId() {
    return id;
  }

  public int getIdCity() {
    return idCity;
  }

  public String getLatitude() {
    return latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public String getName() {
    return name;
  }

  public String getNumber() {
    return number;
  }

  public int getZone() {
    return zone;
  }

  public int getTimetableType() {
    return timetableType;
  }

  public String getShowNum() {
    return showNum;
  }
}
