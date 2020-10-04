package com.softarea.mpktarnow.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.softarea.mpktarnow.data.database.dao.DbBusStopDAO;
import com.softarea.mpktarnow.data.database.dao.DbRouteDAO;
import com.softarea.mpktarnow.model.db.BusStopListItem;
import com.softarea.mpktarnow.model.db.DatabaseDepartue;
import com.softarea.mpktarnow.model.db.Route;
import com.softarea.mpktarnow.model.db.RouteVariant;

@Database(entities = {DatabaseDepartue.class, RouteVariant.class, Route.class, BusStopListItem.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
  public abstract DbRouteDAO dbRoutesDAO();
  public abstract DbBusStopDAO dbBusStopDAO();
}
