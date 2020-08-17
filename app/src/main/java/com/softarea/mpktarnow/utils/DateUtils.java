package com.softarea.mpktarnow.utils;

public class DateUtils {
  public static String min2HHMM( int n ) {
    int t = n / 60;
    int i = n - t * 60;

    return (t < 10 ? " " + t : t) + ":" + (i < 10 ? "0" + i : i); }
}
