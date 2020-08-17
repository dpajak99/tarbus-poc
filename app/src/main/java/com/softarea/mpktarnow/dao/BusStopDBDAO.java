package com.softarea.mpktarnow.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.softarea.mpktarnow.database.model.BusStopDB;

import java.util.List;

@Dao
public interface BusStopDBDAO {
  @Query("SELECT * FROM BusStops")
  List<BusStopDB> getAll();

  @Query("SELECT * FROM BusStops WHERE id = :id")
  BusStopDB getBusStopById( int id );
  @Insert
  void insert( BusStopDB busStop );
}
