package com.softarea.mpktarnow.utils;

import com.softarea.mpktarnow.model.DepartueItem;
import com.softarea.mpktarnow.model.NearBusStop;

import java.util.Comparator;

public class ListUtils {
  public static class Sortbyroll implements Comparator<DepartueItem> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(DepartueItem a, DepartueItem b)
    {
      return a.getDepartueTime() - b.getDepartueTime();
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
