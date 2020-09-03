package com.softarea.mpktarnow.model;

import com.tickaroo.tikxml.annotation.TextContent;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "p")
public class P {
  @TextContent
  String content;

  public P() {
  }

  public String getContent() {
    return content;
  }

  @Override
  public String toString() {
    return "P{" +
      "content='" + content + '\'' +
      '}';
  }
}
