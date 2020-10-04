package com.softarea.mpktarnow.data.remote.dao;

import com.softarea.mpktarnow.model.Vehicle;

import org.json.JSONArray;
import org.json.JSONException;


public class MpkDAO {
  public static Vehicle parseJsonToVehicle(String jsonVehicles) {
    JSONArray jsonArray = null;
    Vehicle vehicle = new Vehicle();
    try {
      jsonArray = new JSONArray(jsonVehicles);

      vehicle = new Vehicle(
        jsonArray.getInt(0),
        jsonArray.getInt(1),
        jsonArray.getString(2),
        jsonArray.getString(3),
        jsonArray.getString(4),
        jsonArray.getInt(5),
        jsonArray.getInt(6),
        jsonArray.getInt(7),
        jsonArray.getInt(8),
        jsonArray.getDouble(9),
        jsonArray.getDouble(10),
        jsonArray.getDouble(11),
        jsonArray.getDouble(12),
        jsonArray.getInt(13),
        jsonArray.getString(14),
        jsonArray.getInt(15),
        jsonArray.getString(16),
        jsonArray.getInt(17),
        jsonArray.getString(18),
        jsonArray.getString(19),
        jsonArray.getString(20),
        jsonArray.getString(21),
        jsonArray.getInt(22),
        jsonArray.getString(23),
        jsonArray.getString(24),
        jsonArray.getString(25),
        jsonArray.getString(26),
        jsonArray.getInt(27)
      );
    } catch (JSONException e1) {
      e1.printStackTrace();
    }

    return vehicle;
  }
}
