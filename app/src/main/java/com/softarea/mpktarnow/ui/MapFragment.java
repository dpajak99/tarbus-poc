package com.softarea.mpktarnow.ui;

import android.os.Bundle;
import android.util.Log;
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
    mapUtils = new MapService(new GoogleMapService(requireActivity(), root, googleMap), bundle);
    mapUtils.setListeners();
    if (bundle.getString("key").equals("busStopList")) {
      mapUtils.showBusStops();
    } else if (bundle.getString("key").equals("busDetails")) {
      mapUtils.getTrack();
      mapUtils.getBusDetails();
    } else if( bundle.getString("key").equals("searchConnection")) {
      mapUtils.setSearchTrack(MainActivity.searchConnectionList);
    } else if (bundle.getString("key").equals("getFrom") || bundle.getString("key").equals("getTo")) {
      LinearLayout boxInfo = root.findViewById(R.id.info_box);
      TextView viewFinder = root.findViewById(R.id.tv_viewfinder);
      boxInfo.setVisibility(View.VISIBLE);
      viewFinder.setVisibility(View.VISIBLE);

      TextView cameraCoordsValue = root.findViewById(R.id.tv_camera_position);
      googleMap.setOnCameraMoveListener(() -> {

        LatLng position = googleMap.getCameraPosition().target;
        if (bundle.getString("key").equals("getFrom")) {
          MainActivity.lng_from = position.longitude;
          MainActivity.lat_from = position.latitude;
        } else if (bundle.getString("key").equals("getTo")) {
          MainActivity.lng_to = position.longitude;
          MainActivity.lat_to = position.latitude;
        }
        Log.i("TEST", googleMap.getCameraPosition().toString());
        cameraCoordsValue.setText(position.latitude + " " + position.longitude);
      });

      Button buttonSearchOnMap = root.findViewById(R.id.button_select_location);
      buttonSearchOnMap.setOnClickListener(view -> {
        getFragmentManager().popBackStackImmediate();
      });


    }

    mapUtils.showCurrentLocation();
    LatLng coords_tarnow = new LatLng(Statics.centerLng, Statics.centerLat);
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(coords_tarnow));
  }

  @Override
  public void onStop() {
    super.onStop();
    mapUtils.stopRefreshingMap();
  }
}
