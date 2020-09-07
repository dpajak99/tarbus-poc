package com.softarea.mpktarnow.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.activities.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotificationsFragment extends Fragment {
  EditText endPoint;
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_notifications, container, false);

    EditText startPoint = root.findViewById(R.id.search_startPoint);
    endPoint = root.findViewById(R.id.search_endPoint);
    Button buttonSearch = root.findViewById(R.id.button_search_connection);

    FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
    }
    Log.i("TEST", "BEFORE");

    List<Double> currLocation = new ArrayList<>();

    try {
      fusedLocationClient.getLastLocation()
        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
          @Override
          public void onSuccess(Location location) {
            Log.i("TEST", "SUCCESS");
            if (location != null) {
              Log.i("TEST", "LOKALIZACJA : " + location.getLatitude() + ", " + location.getLongitude());
              currLocation.clear();
              currLocation.add(location.getLatitude());
              currLocation.add(location.getLongitude());

              startPoint.setText(location.getLatitude() + ", " + location.getLongitude());
            } else {
              Log.i("TEST", "Location is null");
            }
          }
        });
    } catch (Exception e) {
      Log.i("TEST", "ERROR: " + e.toString());
    }
    Log.i("TEST", "AFTER");

    Button buttonSearchOnMap = root.findViewById(R.id.button_search_location);
    buttonSearchOnMap.setOnClickListener(view -> {
      Bundle result = new Bundle();
      result.putString("key", "connectionSearch");
      Navigation.findNavController(root).navigate(R.id.navigation_map, result);
    });

    buttonSearch.setOnClickListener(view -> {
      Bundle result = new Bundle();
      result.putDouble("lng1", MainActivity.lat);
      result.putDouble("lng2", currLocation.get(0));
      result.putDouble("lat1", MainActivity.lng);
      result.putDouble("lat2", currLocation.get(1));
      Navigation.findNavController(root).navigate(R.id.navigation_dashboard, result);
    });
    return root;
  }

  @Override
  public void onResume() {
    if( MainActivity.lat != null &&  MainActivity.lng != null ) {
      Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
      List<Address> addresses = null;
      try {
        addresses = geocoder.getFromLocation(MainActivity.lat , MainActivity.lng, 1);
      } catch (IOException e) {
        Log.i("TEST", "ERROR" + e);
      }
      String cityName = addresses.get(0).getAddressLine(0);
      endPoint.setText(cityName);
      }
    super.onResume();
  }
}
