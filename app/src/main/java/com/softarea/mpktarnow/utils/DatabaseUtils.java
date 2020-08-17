package com.softarea.mpktarnow.utils;

import android.content.Context;

import androidx.room.Room;

import com.softarea.mpktarnow.database.AppDatabase;

public class DatabaseUtils {
  public static AppDatabase getDatabase(Context context ) {
    AppDatabase db = Room.databaseBuilder(context,
      AppDatabase.class, "mpktarnow.db").fallbackToDestructiveMigration().allowMainThreadQueries().build();

    return db;
  }
}
