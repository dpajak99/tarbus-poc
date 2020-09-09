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
  public static final int STATUS_CURRENT_POSITION = 0;
  public static final int UPDATE_POSITION = 1;

  public static String getPlaceFromCoords(Handler handler, Context context, LatLng coords ) throws IOException {
    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
    List<Address> addresses = geocoder.getFromLocation(coords.latitude, coords.longitude, 1);
    return addresses.get(0).getAddressLine(0);
  }

  @SuppressLint("MissingPermission")
  public static void getCurrentLocation(Handler handler, Context context, int status ) {
    FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    if (PermissionUtils.checkPermissions(context)) {
    }
    try {
      fusedLocationClient.getLastLocation()
        .addOnSuccessListener(location -> {
          if (location != null) {
            sendMessageToUi(handler, GeoUtils.STATUS_CURRENT_POSITION, new LatLng(location.getLatitude(), location.getLongitude()));
            sendMessageToUi(handler, status, new LatLng(location.getLatitude(), location.getLongitude()));
          } else {
            Log.i("TEST", "Location is null");
          }
        });
    } catch (Exception e) {
      Log.i("TEST", "ERROR: " + e.toString());
    }
  }

  private static void sendMessageToUi(Handler handler, int what, Object s) {
    Message message = handler.obtainMessage();
    message.what = what;
    message.obj = s;
    handler.sendMessage(message);
  }
}
