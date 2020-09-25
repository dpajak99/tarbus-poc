package com.softarea.mpktarnow.model;

import java.util.List;

public class ListMediator<E> {
  private List<E> objectList;

  public ListMediator(List<E> objectList) {
    this.objectList = objectList;
  }

  public List<E> getObjectList() {
    return objectList;
  }
}
