package com.softarea.mpktarnow.utils;

import com.softarea.mpktarnow.data.remote.model.Departue;
import com.softarea.mpktarnow.model.NearBusStop;

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

  public static class SortByTime implements Comparator<NearBusStop> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(NearBusStop a, NearBusStop b)
    {
      return a.getMeters() - b.getMeters();
    }
  }
}
