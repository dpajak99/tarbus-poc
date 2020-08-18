package com.softarea.mpktarnow.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.softarea.mpktarnow.dao.ScheduleTokenDBDAO;
import com.softarea.mpktarnow.database.dao.DbBusStopDAO;
import com.softarea.mpktarnow.database.dao.DbCityDAO;
import com.softarea.mpktarnow.database.model.ScheduleTokenDB;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.model.City;

@Database(entities = {City.class, BusStop.class, ScheduleTokenDB.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
  public abstract DbCityDAO dbCitiesDAO();
  public abstract DbBusStopDAO dbBusStopDAO();
  public abstract ScheduleTokenDBDAO scheduleTokenDBDAO();
}
