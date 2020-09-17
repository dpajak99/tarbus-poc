package com.softarea.mpktarnow.utils;

public class MathUtils {
  public static int makePositive( int value ) {
    if(value < 0 ) {
      return value * -1;
    }
    return value;
  }

  public static int calcDistanse(double lat1, double lng1, double lat2, double lng2) {

    double earthRadius = 6371000; //meters
    double dLat = Math.toRadians(lat2-lat1);
    double dLng = Math.toRadians(lng2-lng1);
    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
      Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
        Math.sin(dLng/2) * Math.sin(dLng/2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    double dist = (double) (earthRadius * c);

    return (int) dist;
  }

  public static double calcBearing(double lat1, double lon1, double lat2, double lon2) {

    double longDiff = lon2 - lon1;
    double y = Math.sin(longDiff) * Math.cos(lat2);
    double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(longDiff);

    return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
  }
}
