package com.softarea.mpktarnow.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.softarea.mpktarnow.dao.BusStopDBDAO;
import com.softarea.mpktarnow.dao.ScheduleTokenDBDAO;
import com.softarea.mpktarnow.database.model.BusStopDB;
import com.softarea.mpktarnow.database.model.ScheduleTokenDB;

@Database(entities = {BusStopDB.class, ScheduleTokenDB.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
  public abstract BusStopDBDAO busStopDBDAO();
  public abstract ScheduleTokenDBDAO scheduleTokenDBDAO();
}
