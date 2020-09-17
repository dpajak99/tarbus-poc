package com.softarea.mpktarnow.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.softarea.mpktarnow.model.BusStop;

import java.util.List;

@Dao
public interface DbBusStopDAO {
  @Query("SELECT * FROM BusStop")
  List<BusStop> getAll();

  @Query("SELECT * FROM BusStop WHERE id = :id")
  BusStop getBusStopById(int id);

  @Query("SELECT * FROM BusStop WHERE id = :id")
  BusStop getBusStopById(String id);

  @Query("UPDATE BusStop SET latitude = :latitude, longitude = :longitude WHERE id = :id")
  void updateCoords(String latitude, String longitude, int  id);

  @Insert
  void insert(BusStop busStop);

}
