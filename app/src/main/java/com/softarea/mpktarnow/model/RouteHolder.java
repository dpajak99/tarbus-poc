package com.softarea.mpktarnow.model;

import java.util.List;

public class RouteHolder {
  int id;
  int startPoint;
  int endPoint;
  List<RoutePoint> points;

  public RouteHolder(int id, int startPoint, int endPoint, List<RoutePoint> points) {
    this.id = id;
    this.startPoint = startPoint;
    this.endPoint = endPoint;
    this.points = points;
  }

  public int getId() {
    return id;
  }

  public int getStartPoint() {
    return startPoint;
  }

  public int getEndPoint() {
    return endPoint;
  }

  public List<RoutePoint> getPoints() {
    return points;
  }
}
