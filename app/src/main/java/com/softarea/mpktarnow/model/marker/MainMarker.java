package com.softarea.mpktarnow.model.marker;

import com.google.android.gms.maps.model.Marker;

public interface MainMarker {
  void onClick();
  void onItemWindowClick();
  void setObject( Object object);
  boolean isOpen();
  Marker getMarker();
}
