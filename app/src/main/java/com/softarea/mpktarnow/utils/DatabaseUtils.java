package com.softarea.mpktarnow.utils;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.softarea.mpktarnow.database.AppDatabase;

public class DatabaseUtils {
  public static AppDatabase getDatabase(Context context ) {
    Log.i("TEST", "Database initialization");
    AppDatabase db = Room.databaseBuilder(context,
      AppDatabase.class, "mpktarnow.db").fallbackToDestructiveMigration().allowMainThreadQueries().build();

    return db;
  }
}
