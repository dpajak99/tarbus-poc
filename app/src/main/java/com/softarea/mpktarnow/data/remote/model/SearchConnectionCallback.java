package com.softarea.mpktarnow.data.remote.model;

import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "CONNECTIONS")
public class SearchConnectionCallback {
  @PropertyElement(name = "html")
  String html;
  @PropertyElement(name = "json")
  String json;

  public SearchConnectionCallback() {
  }

  public String getJson() {
    return json;
  }
}
