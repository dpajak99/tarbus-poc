package com.softarea.mpktarnow.model;

public class MarkerTag {
  public static final int TYPE_BUSSTOP = 0;
  public static final int TYPE_BUSPIN = 1;
  public static final int TYPE_BUSCOMPASS = 2;
  public static final int TYPE_BUSSTOPEND = 3;
  public static final int TYPE_ICON = 4;

  private Object object;
  private int type;

  public MarkerTag(Object object, int type) {
    this.object = object;
    this.type = type;
  }

  public Object getObject() {
    return object;
  }

  public int getType() {
    return type;
  }
}
