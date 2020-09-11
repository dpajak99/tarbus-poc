package com.softarea.mpktarnow.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.activities.MainActivity;
import com.softarea.mpktarnow.adapters.NearBusStopAdapter;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.model.NearBusStop;
import com.softarea.mpktarnow.utils.DatabaseUtils;
import com.softarea.mpktarnow.utils.GeoUtils;
import com.softarea.mpktarnow.utils.ListUtils;
import com.softarea.mpktarnow.utils.MathUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NearFragment extends Fragment implements SensorEventListener {
  NearBusStopAdapter nearBusStopAdapter;
  List<BusStop> busStops;
  GeomagneticField geoField;

  int interval = 1100; // read sensor data each 1000 ms
  boolean flag = false;
  boolean isHandlerLive = false;
  private Sensor mGravity;
  private SensorManager mSensorManager;
  private LinearLayoutManager llm;
  private float currentDegree = 0f;


  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_near, container, false);

    llm = new LinearLayoutManager(getActivity());
    RecyclerView nearBusStopList = root.findViewById(R.id.list_nearest_busstops);
    nearBusStopAdapter = new NearBusStopAdapter(getActivity());
    nearBusStopList.setHasFixedSize(true);
    nearBusStopList.setLayoutManager(llm);
    nearBusStopList.setAdapter(nearBusStopAdapter);

    busStops = DatabaseUtils.getDatabase(getContext()).dbBusStopDAO().getAll();

    mSensorManager = (SensorManager) requireActivity().getApplication().getSystemService(
      Context.SENSOR_SERVICE);
    mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    GeoUtils.getCurrentLocation(handler, getContext(), GeoUtils.STATUS_CURRENT_POSITION);

    return root;
  }

  @Override
  public void onResume() {
    super.onResume();
    mSensorManager.registerListener(this, mGravity,
      SensorManager.SENSOR_DELAY_NORMAL);

    handler.post(processSensors);
  }

  @Override
  public void onSensorChanged(SensorEvent event) {

    if (flag) {
      float degree = Math.round(event.values[0]);

      currentDegree = -degree;
      flag = false;
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int i) {

  }

  @Override
  public void onPause() {
    handler.removeCallbacks(processSensors);

    super.onPause();
  }

  int counter = 0;

  private final Runnable processSensors = new Runnable() {
    @Override
    public void run() {
      if (MainActivity.lat_current != null) {
        Log.i("TEST", "counter: " + counter);
        if (counter == 0) {
          updateView();
          counter += 1;
        } else {
          updateNearestBusStops();
          counter += 1;
          if (counter == 10) {
            counter = 0;
          }
          flag = true;
        }
      }
      // Do work with the sensor values.


      // The Runnable is posted to run again here:
      handler.postDelayed(this, interval);
    }
  };

  private void updateView() {
    showNearestBusStops();
  }

  private void showNearestBusStops() {
    List<NearBusStop> nearBusStops = new ArrayList<>();
    nearBusStops.clear();
    List<Float> directions = new ArrayList<>();
    List<Integer> meters = new ArrayList<>();
    for (BusStop busStop : busStops) {
      int imeters = MathUtils.calcDistanse(MainActivity.lat_current, MainActivity.lng_current, busStop.getLongitude(), busStop.getLatitude());
      float idir = (float) (MathUtils.calcBearing(MainActivity.lat_current, MainActivity.lng_current, busStop.getLongitude(), busStop.getLatitude()) - currentDegree);
      nearBusStops.add(new NearBusStop(
        imeters,
        idir,
        busStop
      ));
    }
    Collections.sort(nearBusStops, new ListUtils.SortByTime());
    List<NearBusStop> sorted = nearBusStops.subList(0, 4);
    for (int i = 0; i < 4; i++) {
      BusStop busStop = sorted.get(i).getBusStop();
      directions.add((float) (MathUtils.calcBearing(MainActivity.lat_current, MainActivity.lng_current, busStop.getLongitude(), busStop.getLatitude()) - currentDegree));
      meters.add(MathUtils.calcDistanse(MainActivity.lat_current, MainActivity.lng_current, busStop.getLongitude(), busStop.getLatitude()));
    }
    nearBusStopAdapter.setMeters(meters);
    nearBusStopAdapter.setDirections(directions);
    nearBusStopAdapter.update(sorted);
  }


  private void updateNearestBusStops() {
    List<Float> directions = new ArrayList<>();
    List<Integer> meters = new ArrayList<>();

    for (int i = 0; i < 4; i++) {
      BusStop busStop = nearBusStopAdapter.getNearBusStopsSorted().get(i).getBusStop();
      directions.add((float) (MathUtils.calcBearing(MainActivity.lat_current, MainActivity.lng_current, busStop.getLongitude(), busStop.getLatitude()) - currentDegree));
      meters.add(MathUtils.calcDistanse(MainActivity.lat_current, MainActivity.lng_current, busStop.getLongitude(), busStop.getLatitude()));
    }

    nearBusStopAdapter.setDirections(directions);
    nearBusStopAdapter.setMeters(meters);
  }

  @SuppressLint("HandlerLeak")
  private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case GeoUtils.STATUS_CURRENT_POSITION:
          LatLng pos = (LatLng) msg.obj;
          MainActivity.lat_current = pos.latitude;
          MainActivity.lng_current = pos.longitude;

          updateView();
          break;
      }
    }
  };
}
