package com.softarea.mpktarnow.utils;

public class DateUtils {
  public static String min2HHMM( int n ) {
    int t = n / 60;
    int i = n - t * 60;

    return (t < 10 ? " " + t : t) + ":" + (i < 10 ? "0" + i : i); }

  public static String sec2HHMM(int n) {
    n = n % 86400;
    int i = Integer.parseInt(String.valueOf(n / 3600));
    int t = Integer.parseInt(String.valueOf(n % 3600 / 60));
    return i + ":" + (t < 10 ? "0" + t : t);
  }
}
