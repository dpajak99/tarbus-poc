package com.softarea.mpktarnow.dao;

import com.softarea.mpktarnow.model.ScheduleMediator;
import com.softarea.mpktarnow.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
  public static List<String> getAllRouteVariants(List<List<ScheduleMediator>> schedule) {
    List<String> result = new ArrayList<>();
    for (List<ScheduleMediator> scheduleMediatorList : schedule) {
      for (ScheduleMediator scheduleMediator : scheduleMediatorList) {
        String[] seperatedList = scheduleMediator.getVariants().split(",");
        for (String s : seperatedList) {
          if (!StringUtils.isStringAddedToStringTab(s, result) && s.length() != 0) {
            result.add(s);
          }
        }
      }
    }
    return result;
  }
}
