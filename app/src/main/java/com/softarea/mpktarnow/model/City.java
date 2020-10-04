package com.softarea.mpktarnow.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.softarea.mpktarnow.model.db.BusStopListItem;

import java.util.List;

@Entity(tableName = "City")
public class City {
  @PrimaryKey
  int id;
  @ColumnInfo(name = "name")
  String name;
  @Ignore
  List<BusStopListItem> busStops;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<BusStopListItem> getBusStops() {
    return busStops;
  }

  public City(int id, String name, List<BusStopListItem> busStops) {
    this.id = id;
    this.name = name;
    this.busStops = busStops;
  }

  public City(int id, String name) {
    this.id = id;
    this.name = name;
  }



  @Override
  public String toString() {
    return "City{" +
      "id='" + id + '\'' +
      ", name='" + name + '\'' +
      ", busStops=" + busStops +
      '}';
  }
}
