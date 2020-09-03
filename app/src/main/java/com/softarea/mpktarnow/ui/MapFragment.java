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
import com.google.android.gms.maps.model.LatLng;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.utils.MapUtils;

public class MapFragment extends Fragment implements OnMapReadyCallback {
  public static GoogleMap mMap;
  Bundle bundle;
  View root;


  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    root = inflater.inflate(R.layout.fragment_map, container, false);
    bundle = this.getArguments();

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

    if (bundle.getString("key").equals("busStopList")) {
      MapUtils.showBusStops(root, requireActivity(), mMap );
    } else if (bundle.getString("key").equals("busDetails")) {
      MapUtils.showTrack(requireActivity(), bundle, mMap);
      MapUtils.showBusDetails(requireActivity(), bundle, mMap);
    }

    LatLng coords_tarnow = new LatLng(50.012458, 20.988236);
    mMap.moveCamera(CameraUpdateFactory.newLatLng(coords_tarnow));
  }





}
