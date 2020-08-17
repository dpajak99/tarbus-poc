package com.softarea.mpktarnow.utils;

import com.softarea.mpktarnow.model.Departue;

import java.util.Comparator;

public class ListUtils {
  public static class Sortbyroll implements Comparator<Departue> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(Departue a, Departue b)
    {
      return a.getRemainingTime() - b.getRemainingTime();
    }
  }
}
