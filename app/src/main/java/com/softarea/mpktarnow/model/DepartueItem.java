package com.softarea.mpktarnow.model;

import com.softarea.mpktarnow.data.remote.model.RemoteDepartue;
import com.softarea.mpktarnow.model.db.DatabaseDepartue;

public class DepartueItem {
  int departueId;
  int busId;
  int busLine;
  int wariantId;
  int departueTime;
  String liveTime;
  String destination;

  public DepartueItem(RemoteDepartue remoteDepartue, int currentTime) {
    this.departueId = remoteDepartue.getId();
    this.busId = remoteDepartue.getBusId();
    this.busLine = remoteDepartue.getBusLine();
    this.wariantId = remoteDepartue.getWariantId();
    this.departueTime = currentTime + remoteDepartue.getRemainingTime()/60;
    this.liveTime = remoteDepartue.getTime();
    this.destination = remoteDepartue.getBusDirection();
  }

  public DepartueItem( DatabaseDepartue databaseDepartue, String destination, int busLine ) {
    this.departueId = databaseDepartue.getDepartueId();
    this.busId = 0;
    this.busLine = busLine;
    this.wariantId = databaseDepartue.getWariantId();
    this.departueTime = databaseDepartue.getDepartueTime();
    this.liveTime = "";
    this.destination = destination;
  }

  public int getDepartueId() {
    return departueId;
  }

  public int getBusLine() {
    return busLine;
  }

  public int getBusId() {
    return busId;
  }

  public int getWariantId() {
    return wariantId;
  }

  public int getDepartueTime() {
    return departueTime;
  }

  public String getLiveTime() {
    return liveTime;
  }

  public String getDestination() {
    return destination;
  }
}
