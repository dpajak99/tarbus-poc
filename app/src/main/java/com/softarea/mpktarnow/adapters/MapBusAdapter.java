package com.softarea.mpktarnow.adapters;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.softarea.mpktarnow.R;

public class MapBusAdapter implements GoogleMap.InfoWindowAdapter {

  private String busDelayValue;
  private String busDelayInfo;
  private String busDestination;
  private int busDelayColor;
  private FragmentActivity activity;

  public MapBusAdapter(FragmentActivity activity) {
    this.activity = activity;
  }

  @Override
  public View getInfoWindow(Marker arg0) {

    return null;
  }

  @Override
  public View getInfoContents(Marker marker) {
    View v;
    v = activity.getLayoutInflater().inflate(R.layout.item_buspin, null);


    TextView tvBusDelayValue = v.findViewById(R.id.bus_delay_value);
    TextView tvBusDelayInfo= v.findViewById(R.id.bus_delay_info);
    TextView tvBusDestination= v.findViewById(R.id.bus_delay_destination);

    tvBusDelayValue.setText(busDelayValue);
    tvBusDelayInfo.setText(busDelayInfo);
    tvBusDestination.setText(busDestination);
    tvBusDelayValue.setTextColor(activity.getColor(busDelayColor));

    return v;
  }

  public void setBusDelayValue(String busDelayValue) {
    this.busDelayValue = busDelayValue;
  }

  public void setBusDelayInfo(String busDelayInfo) {
    this.busDelayInfo = busDelayInfo;
  }

  public void setBusDelayColor(int busDelayColor) {
    this.busDelayColor = busDelayColor;
  }

  public void setBusDestination(String busDestination) {
    this.busDestination = busDestination;
  }
}

