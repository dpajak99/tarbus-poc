package com.softarea.mpktarnow.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoUtils {
  public static String getPlaceFromCoords(Context context, LatLng coords ) throws IOException {
    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
    List<Address> addresses = geocoder.getFromLocation(coords.latitude, coords.longitude, 1);
    return addresses.get(0).getAddressLine(0);
  }

  @SuppressLint("MissingPermission")
  public static void getCurrentLocation(Handler handler, Context context) {
    FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    if (PermissionUtils.checkPermissions(context)) {
    }
    try {
      fusedLocationClient.getLastLocation()
        .addOnSuccessListener(location -> {
          if (location != null) {
            sendMessageToUi(handler, new LatLng(location.getLatitude(), location.getLongitude()));
          } else {
            Log.i("TEST", "Location is null");
          }
        });
    } catch (Exception e) {
      Log.i("TEST", "ERROR: " + e.toString());
    }
  }

  private static void sendMessageToUi(Handler handler, Object s) {
    Message message = handler.obtainMessage();
    message.obj = s;
    handler.sendMessage(message);
  }
}
