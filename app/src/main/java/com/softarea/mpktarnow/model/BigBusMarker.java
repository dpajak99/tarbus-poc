package com.softarea.mpktarnow.model;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.softarea.mpktarnow.model.marker.BusDirectionMarker;
import com.softarea.mpktarnow.model.marker.BusMarker;
import com.softarea.mpktarnow.model.marker.MainMarker;

public class BigBusMarker {
  private MainMarker busPosition;
  private MainMarker busDirection;

  public BigBusMarker(FragmentActivity activity, GoogleMap map) {
    busPosition = new BusMarker(activity, map, new LatLng(0, 0));
    busDirection = new BusDirectionMarker(map, new LatLng(0, 0));
  }

  public void setImageArrow(BitmapDescriptor bitmapDescriptor) {
    busDirection.getMarker().setIcon(bitmapDescriptor);
  }

  public void setImagePin(BitmapDescriptor bitmapDescriptor) {
    busPosition.getMarker().setIcon(bitmapDescriptor);
  }

  public void setPosition(LatLng position) {
    busPosition.getMarker().setPosition(position);
    busDirection.getMarker().setPosition(position);
  }

  public void setObject(Vehicle vehicle) {
    busPosition.setObject(vehicle);
  }

  public void setRotation( float rotation ) {
    busDirection.getMarker().setRotation(rotation);
  }

  public void openIfWasOpened() {
    if (busPosition.isOpen()) {
      busPosition.getMarker().showInfoWindow();
    }
  }
}
