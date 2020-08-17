package com.softarea.mpktarnow.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "BusStops")
public class BusStopDB {
  @PrimaryKey
  private int id;
  @ColumnInfo( name = "name")
  private String name;
  @ColumnInfo( name = "idString")
  private String idString;
  @ColumnInfo( name = "zeroone")
  private double zeroone;
  @ColumnInfo( name = "width")
  private double width;
  @ColumnInfo( name = "height")
  private double height;
  @ColumnInfo( name = "zerotwo")
  private double zerotwo;
  @ColumnInfo( name = "type")
  private String type;
  @ColumnInfo( name = "zerothree")
  private double zerothree;
  @ColumnInfo( name = "oneone")
  private double oneone;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getIdString() {
    return idString;
  }

  public double getZeroone() {
    return zeroone;
  }

  public double getWidth() {
    return width;
  }

  public double getHeight() {
    return height;
  }

  public double getZerotwo() {
    return zerotwo;
  }

  public String getType() {
    return type;
  }

  public double getZerothree() {
    return zerothree;
  }

  public double getOneone() {
    return oneone;
  }

  public BusStopDB(int id, String name, String idString, double zeroone, double width, double height, double zerotwo, String type, double zerothree, double oneone) {
    this.id = id;
    this.name = name;
    this.idString = idString;
    this.zeroone = zeroone;
    this.width = width;
    this.height = height;
    this.zerotwo = zerotwo;
    this.type = type;
    this.zerothree = zerothree;
    this.oneone = oneone;
  }

  @Override
  public String toString() {
    return "BusStopDB{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", idString='" + idString + '\'' +
      ", zeroone=" + zeroone +
      ", width=" + width +
      ", height=" + height +
      ", zerotwo=" + zerotwo +
      ", type='" + type + '\'' +
      ", zerothree=" + zerothree +
      ", oneone='" + oneone + '\'' +
      '}';
  }
}
