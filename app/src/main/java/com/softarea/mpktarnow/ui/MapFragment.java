package com.softarea.mpktarnow.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.utils.DatabaseUtils;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
  private GoogleMap mMap;
  private List<BusStop> busStops;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_map, container, false);

    busStops = DatabaseUtils.getDatabase(getContext()).dbBusStopDAO().getAll();

    SupportMapFragment mapFragment =
      (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
    if (mapFragment != null) {
      mapFragment.getMapAsync(this);
    }

    return root;
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

    // Add a marker in Sydney and move the camera
    LatLng coords_tarnow = new LatLng(50.012458, 20.988236);


    for( BusStop busStop : busStops) {
      int icon_resource = R.drawable.ic_buspoint;
      if( busStop.getIdCity() != 1 ) {
        icon_resource = R.drawable.ic_buspoint_yellow;
      }
      LatLng coords_curr_pos = new LatLng(Double.parseDouble(busStop.getLongitude()), Double.parseDouble(busStop.getLatitude()));
      mMap.addMarker(
        new MarkerOptions()
          .position(coords_curr_pos)
          .title(String.valueOf(busStop.getName()))
          .icon(BitmapDescriptorFactory.fromResource(icon_resource)));
    }

    //mMap.addMarker(new MarkerOptions().position(coords_tarnow).title("Tarnów"));
    mMap.moveCamera(CameraUpdateFactory.newLatLng(coords_tarnow));
  }
}
