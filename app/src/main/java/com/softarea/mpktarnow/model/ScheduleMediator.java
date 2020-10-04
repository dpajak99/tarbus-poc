package com.softarea.mpktarnow.model;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import com.softarea.mpktarnow.utils.StringUtils;
import com.softarea.mpktarnow.utils.TimeUtils;

public class ScheduleMediator {
  @ColumnInfo(name = "godzina_odjazdu_in_sec")
  public int departue;
  @ColumnInfo(name = "dodatkowe_oznaczenia_string")
  private String variants;
  @Ignore
  private String variantsShortcut;

  public ScheduleMediator(int departue, String variants) {
    this.departue = departue;
    this.variants = variants;
    this.variantsShortcut = StringUtils.getFirstLetterFromWordsSeperatedBySemicolon(variants);
  }


  public String getDepartue() {
    return TimeUtils.min2HHMM(departue);
  }

  public String getVariants() {
    return variants;
  }

  public String getVariantsShortcut() {
    return variantsShortcut;
  }
}
