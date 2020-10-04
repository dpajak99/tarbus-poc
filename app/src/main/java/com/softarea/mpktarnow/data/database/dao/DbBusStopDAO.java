package com.softarea.mpktarnow.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.softarea.mpktarnow.model.ScheduleMediator;
import com.softarea.mpktarnow.model.db.BusStopListItem;
import com.softarea.mpktarnow.model.db.DatabaseDepartue;

import java.util.List;

@Dao
public interface DbBusStopDAO {
  @Query("SELECT * FROM Przystanek_autobusowy")
  List<BusStopListItem> getAll();

  @Query("SELECT * FROM Przystanek_autobusowy WHERE id_przystanku = :id")
  BusStopListItem getBusStopById(int id);

  @Query("SELECT * FROM Przystanek_autobusowy WHERE id_przystanku = :id")
  BusStopListItem getBusStopById(String id);

  @Query("SELECT DISTINCT numer_linii FROM Autobus, Odjazd WHERE Odjazd.id_przystanku = :busStopId AND Autobus.id_autobusu = Odjazd.id_autobusu")
  List<String> getBusLines(int busStopId);

  @Query("SELECT numer_linii FROM Autobus WHERE id_autobusu = :lineId")
  int getBusLineFromId(int lineId);

  @Query("SELECT id_autobusu FROM Autobus WHERE numer_linii = :line")
  int getBusLineId(String line);

  @Query("SELECT godzina_odjazdu_in_sec, dodatkowe_oznaczenia_string FROM Odjazd WHERE id_przystanku = :busStopId AND id_autobusu = :busLineId AND typ_dnia = :dayType")
  List<ScheduleMediator> getSchedule(int busStopId, int busLineId, String dayType);

  @Query("SELECT * FROM Odjazd WHERE id_przystanku = :busStopId AND typ_dnia = :dayType AND godzina_odjazdu_in_sec > :currentTime ORDER BY godzina_odjazdu_in_sec LIMIT 12")
  List<DatabaseDepartue> getNextDepartues(int busStopId, int currentTime, String dayType);

  @Query("SELECT opis_trasy FROM Wariant_trasy WHERE id_przystanku = :busStopId AND id_wariant_trasy = :wariantTrasy ")
  String getDestinationByRouteId(int busStopId, int wariantTrasy);

  @Insert
  void insert(BusStopListItem busStop);

}
