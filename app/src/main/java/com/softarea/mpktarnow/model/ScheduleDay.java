package com.softarea.mpktarnow.model;

import com.softarea.mpktarnow.utils.StringUtils;
import com.softarea.mpktarnow.utils.TimeUtils;

import java.util.List;

public class ScheduleDay {
  private List<ScheduleMediator> scheduleMediatorList;
  private String dayType;

  public ScheduleDay(List<ScheduleMediator> scheduleMediatorList, String dayType) {
    this.scheduleMediatorList = scheduleMediatorList;
    this.dayType = dayType;
  }

  public List<ScheduleMediator> getScheduleMediatorList() {
    return scheduleMediatorList;
  }

  public String getDayType() {
    return dayType;
  }

  public String getDayString() {
    StringBuilder result = new StringBuilder();
    result.append(StringUtils.NEW_LINE);
    result.append(TimeUtils.translateDayShortcutToDayName(dayType));
    result.append(StringUtils.NEW_LINE);
    for (int i = 0; i < scheduleMediatorList.size(); i++) {
      result.append(scheduleMediatorList.get(i).getDepartue() + " ");
    }
    result.append(StringUtils.NEW_LINE);

    return result.toString();
  }
}
