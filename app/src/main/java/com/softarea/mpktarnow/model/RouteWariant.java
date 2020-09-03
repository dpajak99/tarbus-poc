package com.softarea.mpktarnow.model;

import java.util.List;

public class RouteWariant {
  int wariantId;
  int zero;
  int way;
  String destinationName;
  String busStart;
  String busEnd;
  List<Integer> busOnTrack;
  List<Integer> pointsOnTrack;
  int zero2;
  String destination;
  int zero3;

  public RouteWariant(int wariantId, int zero, int way, String destinationName, String busStart, String busEnd, List<Integer> busOnTrack, List<Integer> pointsOnTrack, int zero2, String destination, int zero3) {
    this.wariantId = wariantId;
    this.zero = zero;
    this.way = way;
    this.destinationName = destinationName;
    this.busStart = busStart;
    this.busEnd = busEnd;
    this.busOnTrack = busOnTrack;
    this.pointsOnTrack = pointsOnTrack;
    this.zero2 = zero2;
    this.destination = destination;
    this.zero3 = zero3;
  }

  public int getWariantId() {
    return wariantId;
  }

  public int getZero() {
    return zero;
  }

  public int getWay() {
    return way;
  }

  public String getDestinationName() {
    return destinationName;
  }

  public String getBusStart() {
    return busStart;
  }

  public String getBusEnd() {
    return busEnd;
  }

  public List<Integer> getBusOnTrack() {
    return busOnTrack;
  }

  public List<Integer> getPointsOnTrack() {
    return pointsOnTrack;
  }

  public int getZero2() {
    return zero2;
  }

  public String getDestination() {
    return destination;
  }

  public int getZero3() {
    return zero3;
  }
}
