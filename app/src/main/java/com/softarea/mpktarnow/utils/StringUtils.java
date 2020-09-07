package com.softarea.mpktarnow.utils;

import java.text.Normalizer;

public class StringUtils {
  public static String normalize(String base) {
    return Normalizer.normalize(base,
      Normalizer.Form.NFD)
      .replaceAll("ą", "a")
      .replaceAll("ę", "e")
      .replaceAll("ć", "c")
      .replaceAll("ł", "l")
      .replaceAll("ń", "n")
      .replaceAll("ó", "o")
      .replaceAll("ś", "s")
      .replaceAll("ź", "z")
      .replaceAll("ż", "z");
  }

  public static String changeHashForLetters(String base) {
    return Normalizer.normalize(base,
      Normalizer.Form.NFD)
      .replaceAll("&#243;", "ó");
  }

  public static String replaceHTML(String base) {
    return Normalizer.normalize(base,
      Normalizer.Form.NFD)
      .replaceAll("&lt;", "<");
  }

  public static String join(Object... values) {
    StringBuilder sb = new StringBuilder("");
    for (int i = 0; i < values.length; i++) {
      sb.append(values[i]);
    }
    return sb.toString();
  }

  public static String deleteWhiteSpaces( String text ) {
    StringBuilder result = new StringBuilder("");
    for( int i = 0; i < text.length(); i++ ) {
      if( text.charAt(i) != ' ' ) {
        result.append(text.charAt(i));
      }
    }
    return result.toString();
  }
}
