package com.softarea.mpktarnow.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Route")
public class Route {
  @PrimaryKey
  int id;
  @ColumnInfo(name = "busLine")
  int busLine;

  public Route(int id, int busLine) {
    this.id = id;
    this.busLine = busLine;
  }

  public int getId() {
    return id;
  }

  public int getBusLine() {
    return busLine;
  }

  @Override
  public String toString() {
    return "Route{" +
      "id=" + id +
      ", busLine=" + busLine +
      '}';
  }
}
