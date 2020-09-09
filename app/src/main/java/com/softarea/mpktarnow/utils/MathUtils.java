package com.softarea.mpktarnow.utils;

import java.text.DecimalFormat;

public class MathUtils {
  public static int makePositive( int value ) {
    if(value < 0 ) {
      return value * -1;
    }
    return value;
  }

  public double roundTwoDecimals(double d) {
    DecimalFormat twoDForm = new DecimalFormat("#.#######");
    return Double.parseDouble(twoDForm.format(d));
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
}
