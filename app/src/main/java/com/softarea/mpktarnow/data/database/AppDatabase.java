package com.softarea.mpktarnow.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.softarea.mpktarnow.dao.ScheduleTokenDBDAO;
import com.softarea.mpktarnow.data.database.dao.DbBusStopDAO;
import com.softarea.mpktarnow.data.database.dao.DbCityDAO;
import com.softarea.mpktarnow.data.database.dao.DbRouteDAO;
import com.softarea.mpktarnow.data.database.model.ScheduleTokenDB;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.model.City;
import com.softarea.mpktarnow.model.Route;

@Database(entities = {City.class, Route.class, BusStop.class, ScheduleTokenDB.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
  public abstract DbCityDAO dbCitiesDAO();
  public abstract DbRouteDAO dbRoutesDAO();
  public abstract DbBusStopDAO dbBusStopDAO();
  public abstract ScheduleTokenDBDAO scheduleTokenDBDAO();
}
