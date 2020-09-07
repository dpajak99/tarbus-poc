package com.softarea.mpktarnow.model;

import java.util.List;

public class SearchResult {
  private String id;
  private List<SearchResultPoint> data;

  public SearchResult(String id, List<SearchResultPoint> data) {
    this.id = id;
    this.data = data;
  }

  public String getId() {
    return id;
  }

  public List<SearchResultPoint> getData() {
    return data;
  }
}
