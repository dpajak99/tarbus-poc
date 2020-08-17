package com.softarea.mpktarnow.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ScheduleToken")
public class ScheduleTokenDB {
  @PrimaryKey(autoGenerate = true)
  private int id;
  @ColumnInfo(name = "token")
  private int token;

  public ScheduleTokenDB(int id, int token) {
    this.id = id;
    this.token = token;
  }

  public int getId() {
    return id;
  }

  public int getToken() {
    return token;
  }
}
