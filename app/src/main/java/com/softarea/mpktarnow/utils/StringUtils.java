package com.softarea.mpktarnow.utils;

import java.text.Normalizer;

public class StringUtils {
  public static String normalize( String base ) {
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
      .replaceAll("ż", "z");  }

  public static String replaceHTML( String base ) {
    return Normalizer.normalize(base,
      Normalizer.Form.NFD)
      .replaceAll("&lt;", "<");  }
}
