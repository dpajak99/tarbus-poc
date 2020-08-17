package com.softarea.mpktarnow.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.softarea.mpktarnow.database.model.ScheduleTokenDB;

@Dao
public interface ScheduleTokenDBDAO {
  @Query("SELECT Count(*) FROM ScheduleToken")
  int getScheduleTokenCount();

  @Insert
  void setScheduleToken(ScheduleTokenDB scheduleTokenDB);
}
