package com.softarea.mpktarnow.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.activities.MainActivity;
import com.softarea.mpktarnow.services.GoogleMapService;
import com.softarea.mpktarnow.services.MapService;
import com.softarea.mpktarnow.statics.Statics;

public class MapFragment extends Fragment implements OnMapReadyCallback {
  Bundle bundle;
  View root;
  MapService mapUtils;

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
    int bundleKey = bundle.getInt("key");
    mapUtils = new MapService(new GoogleMapService(requireActivity(), root, googleMap), bundle);
    mapUtils.setListeners();
    if ( bundleKey == MapService.BUNDLE_BUS_STOP_LIST) {
      mapUtils.showBusStops();
    } else if (bundleKey == MapService.BUNDLE_BUS_DETAILS) {
      mapUtils.getTrack();
      mapUtils.getBusDetails();
    } else if( bundleKey == MapService.BUNDLE_SEARCH_CONNECTION) {
      mapUtils.setSearchTrack(MainActivity.searchConnectionList);
    } else if (bundleKey == MapService.BUNDLE_MAP_GET_FROM || bundleKey == MapService.BUNDLE_MAP_GET_TO) {
      LinearLayout boxInfo = root.findViewById(R.id.info_box);
      TextView viewFinder = root.findViewById(R.id.tv_viewfinder);
      boxInfo.setVisibility(View.VISIBLE);
      viewFinder.setVisibility(View.VISIBLE);

      TextView cameraCoordsValue = root.findViewById(R.id.tv_camera_position);
      googleMap.setOnCameraMoveListener(() -> {

        LatLng position = googleMap.getCameraPosition().target;
        if (bundleKey == MapService.BUNDLE_MAP_GET_FROM ) {
          MainActivity.lng_from = position.longitude;
          MainActivity.lat_from = position.latitude;
        } else {
          MainActivity.lng_to = position.longitude;
          MainActivity.lat_to = position.latitude;
        }
        cameraCoordsValue.setText(position.latitude + " " + position.longitude);
      });

      Button buttonSearchOnMap = root.findViewById(R.id.button_select_location);
      buttonSearchOnMap.setOnClickListener(view -> {
        getFragmentManager().popBackStackImmediate();
      });


    }

    mapUtils.showCurrentLocation();
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Statics.centerLng, Statics.centerLat)));
  }

  @Override
  public void onStop() {
    super.onStop();
    mapUtils.stopRefreshingMap();
  }
}
