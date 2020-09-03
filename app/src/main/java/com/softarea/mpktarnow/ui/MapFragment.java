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
import com.softarea.mpktarnow.services.GoogleMapService;
import com.softarea.mpktarnow.utils.MapUtils;

public class MapFragment extends Fragment implements OnMapReadyCallback {
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
    MapUtils mapUtils = new MapUtils(new GoogleMapService(requireActivity(), root, googleMap), bundle);
    mapUtils.setListeners();
    if (bundle.getString("key").equals("busStopList")) {
      mapUtils.showBusStops();
    } else if (bundle.getString("key").equals("busDetails")) {
      mapUtils.showTrack();
      mapUtils.showBusDetails();
    }

    LatLng coords_tarnow = new LatLng(50.012458, 20.988236);
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(coords_tarnow));
  }





}
