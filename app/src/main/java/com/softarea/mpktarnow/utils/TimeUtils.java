package com.softarea.mpktarnow.utils;

import androidx.fragment.app.FragmentActivity;

import com.softarea.mpktarnow.R;

import java.util.Calendar;

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
    } else if( value / 60 >= 60){
        int hours = (int) value / 3600;
        int minutes = (int) (value - hours * 3600) / 60;
        return " " + hours + " h " + minutes + " min";
    } else {
      return " " + (int) (value / 60) + " min";
    }
  }

  public static String getCurrentTime() {
    Calendar rightNow = Calendar.getInstance();
    return StringUtils.join( makeZero(rightNow.get(Calendar.HOUR_OF_DAY)), ":", makeZero(rightNow.get(Calendar.MINUTE)));
  }

  public static String getCurrentDate() {
    //"2020-09-04"
    Calendar rightNow = Calendar.getInstance();
    return StringUtils.join( makeZero(rightNow.get(Calendar.YEAR)), "-", makeZero(rightNow.get(Calendar.MONTH)), "-", makeZero(rightNow.get(Calendar.DAY_OF_MONTH)));
  }

  public static String makeZero( int content ) {
    String text = String.valueOf(content);
    if( text.length() == 1) {
      return StringUtils.join("0", text);
    }
    return text;
  }
}
