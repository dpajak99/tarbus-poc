package com.softarea.mpktarnow.utils;

import androidx.fragment.app.FragmentActivity;

import com.softarea.mpktarnow.R;

public class TimeUtils {
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

  public static String calcDelayInfo(FragmentActivity activity, int value ) {
    if (value < 0) {
      return activity.getString(R.string.current_acceleration);
    } else {
      return activity.getString(R.string.current_delay);
    }
  }

  public static String calcDelayValue( int value ) {
    value = MathUtils.makePositive(value);
    if (value / 60 < 1) {
     return " < 1 min";
    } else {
      return " " + (int) (value / 60) + " min";
    }
  }
}
