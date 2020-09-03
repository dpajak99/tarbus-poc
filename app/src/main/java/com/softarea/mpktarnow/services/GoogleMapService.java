package com.softarea.mpktarnow.services;

import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;

public class GoogleMapService {
  GoogleMap mMap;
  View view;
  FragmentActivity activity;

  public GoogleMapService(FragmentActivity activity, View view, GoogleMap googleMap ) {
    this.activity = activity;
    this.view = view;
    this.mMap = googleMap;
  }

  public GoogleMap getMap() {
    return mMap;
  }

  public FragmentActivity getActivity() {
    return activity;
  }

  public View getView() {
    return view;
  }
}
