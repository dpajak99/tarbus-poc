package com.softarea.mpktarnow.model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.softarea.mpktarnow.model.BusStop;

import java.util.List;

@Entity(tableName = "Przystanek_autobusowy", primaryKeys = {"id_przystanku"})
public class BusStopListItem implements BusStop {
  @ColumnInfo(name = "id_przystanku")
  @NonNull
  int id;
  @ColumnInfo(name = "id_miejscowosci")
  @NonNull
  int isCity;
  @ColumnInfo(name = "latitude")
  @NonNull
  double latitude;
  @ColumnInfo(name = "longitude")
  @NonNull
  double longitude;
  @ColumnInfo(name = "nazwa_przystanku")
  @NonNull
  String name;
  @ColumnInfo(name = "numer_przystanku")
  @NonNull
  String number;
  @ColumnInfo(name = "strefa")
  @NonNull
  String zone;
  @ColumnInfo(name = "autobusy_odj")
  @NonNull
  String busLineDepartues;

  @Ignore
  List<String> departueLines;

  public BusStopListItem(int id, int isCity, double latitude, double longitude, String name, String number, String zone, String busLineDepartues) {
    this.id = id;
    this.isCity = isCity;
    this.latitude = latitude;
    this.longitude = longitude;
    this.name = name;
    this.number = number;
    this.zone = zone;
    this.busLineDepartues = busLineDepartues;
  }

  public int getId() {
    return id;
  }

  public int getIsCity() {
    return isCity;
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

  public String getNumber() {
    return number;
  }

  public String getZone() {
    return zone;
  }

  public String getBusLineDepartues() {
    return busLineDepartues;
  }

  public List<String> getDepartueLines() {
    return departueLines;
  }

  public void setDepartueLines(List<String> departueLines) {
    this.departueLines = departueLines;
  }
}
