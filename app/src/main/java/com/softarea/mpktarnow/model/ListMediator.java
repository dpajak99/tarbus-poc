package com.softarea.mpktarnow.model;

import java.util.List;

public class ListMediator {
  private List<Object> objectList;

  public ListMediator(List<Object> objectList) {
    this.objectList = objectList;
  }

  public List<Object> getObjectList() {
    return objectList;
  }
}
