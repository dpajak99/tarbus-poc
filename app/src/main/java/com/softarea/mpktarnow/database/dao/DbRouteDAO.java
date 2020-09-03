package com.softarea.mpktarnow.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.softarea.mpktarnow.model.Route;

import java.util.List;

@Dao
public interface DbRouteDAO {
  @Query("SELECT * FROM Route")
  List<Route> getAll();

  @Query("SELECT * FROM Route WHERE busLine = :id")
  Route getRouteByBusLine(int id);

  @Insert
  void insert(Route route);
}
