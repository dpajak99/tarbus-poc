package com.softarea.mpktarnow.model.marker;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.adapters.MapBusAdapter;
import com.softarea.mpktarnow.model.Vehicle;
import com.softarea.mpktarnow.utils.MathUtils;
import com.softarea.mpktarnow.utils.TimeUtils;

public class BusMarker implements MainMarker {
  private static final int MARKER_ZINDEX = 10;

  private Marker marker;
  private FragmentActivity activity;
  private GoogleMap map;
  private Vehicle vehicle;
  private boolean isOpen = false;

  public BusMarker( FragmentActivity activity, GoogleMap map, LatLng position) {
    this.activity = activity;
    this.map = map;
    this.marker = map.addMarker(new MarkerOptions()
      .position(position)
      .zIndex(MARKER_ZINDEX));

    marker.setTag(this);

    map.setOnInfoWindowCloseListener(marker -> {
      isOpen = false;
    });
  }

  @Override
  public void onClick() {
        MapBusAdapter mapBusAdapter = new MapBusAdapter(activity);
    map.setInfoWindowAdapter(mapBusAdapter);

    mapBusAdapter.setBusDelayInfo(TimeUtils.calcDelayInfo(activity, vehicle.getOdchylenie()));
    mapBusAdapter.setBusDelayValue(TimeUtils.calcDelayValue(vehicle.getOdchylenie()));
    mapBusAdapter.setBusDestination(vehicle.getDestination());

    if ((MathUtils.makePositive(vehicle.getOdchylenie()) / 60) < 4) {
      mapBusAdapter.setBusDelayColor(R.color.success);
    } else {
      mapBusAdapter.setBusDelayColor(R.color.error);
    }

    if (isOpen) {
      marker.hideInfoWindow();
      isOpen = false;
    } else {
      marker.showInfoWindow();
      isOpen = true;
    }
  }

  public void setObject( Object object) {
    this.vehicle = (Vehicle) object;
  }

  public boolean isOpen() {
      return isOpen;
  }

  @Override
  public void onItemWindowClick() {

  }

  @Override
  public Marker getMarker() {
    return marker;
  }
}
