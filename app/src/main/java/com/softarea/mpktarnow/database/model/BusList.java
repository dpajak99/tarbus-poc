package com.softarea.mpktarnow.database.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusList {
  @SerializedName("BusList")
  public List<List<Object>> busList;

  public List<List<Object>> getBusList() {
    return busList;
  }

  public BusList(List<List<Object>> busList) {
    this.busList = busList;
  }
}
