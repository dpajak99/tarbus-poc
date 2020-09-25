package com.softarea.mpktarnow.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.softarea.mpktarnow.model.db.Route;

import java.util.List;

@Dao
public interface DbRouteDAO {
  @Query("SELECT * FROM Autobus")
  List<Route> getAll();

  @Query("SELECT * FROM Autobus WHERE numer_linii = :id")
  Route getRouteByBusLine(int id);

  @Insert
  void insert(Route busItem);
}
