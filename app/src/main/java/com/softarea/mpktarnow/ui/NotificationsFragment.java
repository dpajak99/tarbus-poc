package com.softarea.mpktarnow.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.model.LatLng;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.activities.MainActivity;
import com.softarea.mpktarnow.utils.GeoUtils;
import com.softarea.mpktarnow.utils.TimeUtils;

import java.io.IOException;
import java.util.Calendar;

public class NotificationsFragment extends Fragment {
  EditText endPoint;
  EditText startPoint;
  DatePickerDialog datePicker;
  TimePickerDialog timePicker;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_notifications, container, false);

    startPoint = root.findViewById(R.id.search_startPoint);
    endPoint = root.findViewById(R.id.search_endPoint);
    Button buttonSearch = root.findViewById(R.id.button_search_connection);

    if (MainActivity.lat_from == null && MainActivity.lat_to == null) {
      GeoUtils.getCurrentLocation(handler, getContext(), UPDATE_FIRST);
    }

    String[] searchTypes = {"Odjazd", "Przyjazd"};
    Spinner spinner = root.findViewById(R.id.search_types);
    ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, searchTypes);
    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(arrayAdapter);

    EditText selectTime = root.findViewById(R.id.time);
    EditText selectDate = root.findViewById(R.id.date);

    selectTime.setInputType(InputType.TYPE_NULL);
    selectTime.setText(TimeUtils.getCurrentTime());
    selectTime.setOnClickListener(v -> {
      final Calendar cldr = Calendar.getInstance();
      int hour = cldr.get(Calendar.HOUR_OF_DAY);
      int minutes = cldr.get(Calendar.MINUTE);
      timePicker = new TimePickerDialog(getContext(),
        (tp, sHour, sMinute) -> selectTime.setText(sHour + ":" + sMinute), hour, minutes, true);
      timePicker.show();
    });

    selectDate.setInputType(InputType.TYPE_NULL);
    selectDate.setText(TimeUtils.getCurrentDate());
    selectDate.setOnClickListener(v -> {
      final Calendar cldr = Calendar.getInstance();
      int day = cldr.get(Calendar.DAY_OF_MONTH);
      int month = cldr.get(Calendar.MONTH);
      int year = cldr.get(Calendar.YEAR);
      datePicker = new DatePickerDialog(getContext(),
        (view, year1, monthOfYear, dayOfMonth) -> selectDate.setText(year1 + "-" + monthOfYear + "-" + dayOfMonth), year, month, day);
      datePicker.show();
    });

    Button buttonSearchOnMapTo = root.findViewById(R.id.btn_searchby_map_to);
    buttonSearchOnMapTo.setOnClickListener(view -> {
      Bundle result = new Bundle();
      result.putString("key", "getTo");
      Navigation.findNavController(root).navigate(R.id.navigation_map, result);
    });

    Button buttonSearchOnMapFrom = root.findViewById(R.id.btn_searchby_map_from);
    buttonSearchOnMapFrom.setOnClickListener(view -> {
      Bundle result = new Bundle();
      result.putString("key", "getFrom");
      Navigation.findNavController(root).navigate(R.id.navigation_map, result);
    });

    Button btnSearchbyLocationFrom = root.findViewById(R.id.btn_searchby_location_from);
    btnSearchbyLocationFrom.setOnClickListener(view -> {
      GeoUtils.getCurrentLocation(handler, getContext(), UPDATE_FIRST);
    });
    Button btnSearchbyLocationTo = root.findViewById(R.id.btn_searchby_location_to);
    btnSearchbyLocationTo.setOnClickListener(view -> {
      GeoUtils.getCurrentLocation(handler, getContext(), UPDATE_SECOND);
    });


    buttonSearch.setOnClickListener(view -> {
      Bundle result = new Bundle();
      result.putDouble("latTo", MainActivity.lat_to);
      result.putDouble("latFrom", MainActivity.lat_from);
      result.putDouble("lngTo", MainActivity.lng_to);
      result.putDouble("lngFrom", MainActivity.lng_from);
      result.putString("time", selectTime.getText().toString());
      result.putString("date", selectDate.getText().toString());

      if (spinner.getSelectedItem().toString().equals(searchTypes[0])) {
        result.putString("mode", "0");
      } else if (spinner.getSelectedItem().toString().equals(searchTypes[1])) {
        result.putString("mode", "1");
      }
      Navigation.findNavController(root).navigate(R.id.navigation_dashboard, result);
    });
    return root;
  }

  @Override
  public void onResume() {
    setPositionFrom();
    setPositionTo();

    super.onResume();
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  private static final int UPDATE_FIRST = 10;
  private static final int UPDATE_SECOND = 11;
  LatLng pos;
  @SuppressLint("HandlerLeak")
  private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case GeoUtils.STATUS_CURRENT_POSITION:
          pos = (LatLng) msg.obj;
          MainActivity.lat_current = pos.latitude;
          MainActivity.lng_current = pos.longitude;
          break;
        case UPDATE_FIRST:
          MainActivity.lat_from = MainActivity.lat_current;
          MainActivity.lng_from = MainActivity.lng_current;
          setPositionFrom();
          break;
        case UPDATE_SECOND:
          MainActivity.lat_to = MainActivity.lat_current;
          MainActivity.lng_to = MainActivity.lng_current;
          setPositionTo();
          break;
      }
    }
  };

  private void setPositionFrom() {
    if (MainActivity.lat_from != null && MainActivity.lng_from != null) {
      try {
        startPoint.setText(GeoUtils.getPlaceFromCoords(handler, getContext(), new LatLng(MainActivity.lat_from, MainActivity.lng_from)));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void setPositionTo() {
    if (MainActivity.lat_to != null && MainActivity.lng_to != null) {
      try {
        endPoint.setText(GeoUtils.getPlaceFromCoords(handler, getContext(), new LatLng(MainActivity.lat_to, MainActivity.lng_to)));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
