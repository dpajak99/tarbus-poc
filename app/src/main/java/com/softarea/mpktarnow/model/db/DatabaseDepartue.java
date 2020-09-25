package com.softarea.mpktarnow.model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Odjazd", primaryKeys = {"id_odjazdu"})
public class DatabaseDepartue {
  @ColumnInfo(name = "id_odjazdu")
  int departueId;
  @ColumnInfo(name = "id_przystanku")
  @NonNull
  int budStopId;
  @ColumnInfo(name = "id_autobusu")
  @NonNull
  int busId;
  @ColumnInfo(name = "id_wariant_trasy")
  @NonNull
  int wariantId;
  @ColumnInfo(name = "godzina_odjazdu_in_sec")
  @NonNull
  int departueTime;
  @ColumnInfo(name = "dodatkowe_oznaczenia_string")
  @NonNull
  String additions;
  @ColumnInfo(name = "typ_dnia")
  @NonNull
  String dayType;

  public DatabaseDepartue(int departueId, @NonNull int budStopId, @NonNull int busId, @NonNull int wariantId, @NonNull int departueTime, @NonNull String additions, @NonNull String dayType) {
    this.departueId = departueId;
    this.budStopId = budStopId;
    this.busId = busId;
    this.wariantId = wariantId;
    this.departueTime = departueTime;
    this.additions = additions;
    this.dayType = dayType;
  }

  public int getDepartueId() {
    return departueId;
  }

  @NonNull
  public int getBudStopId() {
    return budStopId;
  }

  @NonNull
  public int getBusId() {
    return busId;
  }

  @NonNull
  public int getWariantId() {
    return wariantId;
  }

  @NonNull
  public int getDepartueTime() {
    return departueTime;
  }

  @NonNull
  public String getAdditions() {
    return additions;
  }

  @NonNull
  public String getDayType() {
    return dayType;
  }
}
