package com.softarea.mpktarnow.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.softarea.mpktarnow.model.City;

import java.util.List;

@Dao
public interface DbCityDAO {
  @Query("SELECT * FROM City")
  List<City> getAll();

  @Query("SELECT * FROM City WHERE id = :id")
  City getCityById(int id);

  @Insert
  void insert(City city);
}
