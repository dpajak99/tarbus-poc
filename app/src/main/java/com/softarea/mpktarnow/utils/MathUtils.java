package com.softarea.mpktarnow.utils;

public class MathUtils {
  public static int makePositive( int value ) {
    if(value < 0 ) {
      return value * -1;
    }
    return value;
  }
}
