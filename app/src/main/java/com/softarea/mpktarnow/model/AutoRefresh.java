package com.softarea.mpktarnow.model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AutoRefresh {
  private ScheduledExecutorService e;
  public abstract void refreshFunction();

  public AutoRefresh( ) {
    e = Executors.newSingleThreadScheduledExecutor();
    e.scheduleAtFixedRate(this::refreshFunction, 0, 10, TimeUnit.SECONDS);
  }

  public void stopRefreshing() {
    e.shutdown();
  }
}
