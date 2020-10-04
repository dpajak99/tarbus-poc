package com.softarea.mpktarnow.model;

public class BusLineStopMediator {
  private int busStopId;
  private String busStopName;
  private String line;
  private boolean expandable;

  public BusLineStopMediator(int busStopId, String busStopName, String line) {
    this.busStopId = busStopId;
    this.busStopName = busStopName;
    this.line = line;
    this.expandable = expandable;
  }

  public int getBusStopId() {
    return busStopId;
  }

  public String getBusStopName() {
    return busStopName;
  }

  public String getLine() {
    return line;
  }

  public void setExpandable(boolean expandable) {
    this.expandable = expandable;
  }

  public boolean isExpandable() {
    return expandable;
  }

  @Override
  public String toString() {
    return "BusLineStopMediator{" +
      "busStopId=" + busStopId +
      ", line='" + line + '\'' +
      ", expandable=" + expandable +
      '}';
  }
}
