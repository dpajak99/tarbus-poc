package com.softarea.mpktarnow.model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Wariant_trasy", primaryKeys = {"id"})
public class RouteVariant {
  @ColumnInfo(name = "id")
  int id;
  @ColumnInfo(name = "id_przystanku")
  @NonNull
  int budStopId;
  @ColumnInfo(name = "id_wariant_trasy")
  @NonNull
  int wariantId;
  @ColumnInfo(name = "opis_trasy")
  @NonNull
  String routeDescription;
  @ColumnInfo(name = "przystanek_poczatkowy")
  @NonNull
  String firstBusStop;
  @ColumnInfo(name = "przystanek_koncowy")
  @NonNull
  String lastBusStop;
  @ColumnInfo(name = "punkty_kluczowe")
  @NonNull
  String checkpoints;

  public RouteVariant(int id, int budStopId, int wariantId, @NonNull String routeDescription, @NonNull String firstBusStop, @NonNull String lastBusStop, @NonNull String checkpoints) {
    this.id = id;
    this.budStopId = budStopId;
    this.wariantId = wariantId;
    this.routeDescription = routeDescription;
    this.lastBusStop = lastBusStop;
    this.firstBusStop = firstBusStop;
    this.checkpoints = checkpoints;
  }

  public int getId() {
    return id;
  }

  public int getBudStopId() {
    return budStopId;
  }

  public int getWariantId() {
    return wariantId;
  }

  @NonNull
  public String getRouteDescription() {
    return routeDescription;
  }

  @NonNull
  public String getLastBusStop() {
    return lastBusStop;
  }

  @NonNull
  public String getFirstBusStop() {
    return firstBusStop;
  }

  @NonNull
  public String getCheckpoints() {
    return checkpoints;
  }
}
