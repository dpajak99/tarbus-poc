package com.softarea.mpktarnow.controllers.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.services.MapService;

public abstract class MapFragment extends Fragment implements OnMapReadyCallback {
  protected MapService mapService;
  private View view;
  private Bundle bundle;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    bundle = this.getArguments();
    this.view = inflater.inflate(R.layout.fragment_map, container, false);
    SupportMapFragment mapFragment =
      (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
    if (mapFragment != null) {
      mapFragment.getMapAsync(this);
    }

    return view;
  }

  @Override
  public abstract void onMapReady(GoogleMap googleMap);

  protected void setMapService(MapService mapService) {
    this.mapService = mapService;
    this.mapService.setListeners();
    this.mapService.showCurrentLocation();
  }


  public View getView() {
    return view;
  }

  protected Bundle getBundle() {
    return bundle;
  }
}
