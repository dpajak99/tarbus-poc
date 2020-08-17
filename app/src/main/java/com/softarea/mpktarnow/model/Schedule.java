package com.softarea.mpktarnow.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "Schedule")
public class Schedule implements Parcelable {
  @Attribute(name = "time")
  String time;
  @Element(name = "Stop")
  BusStop stop;

  public String getTime() {
    return time;
  }

  public Schedule() {
  }

  public BusStop getBusStop() {
    return stop;
  }

  public Schedule(String time, BusStop stop) {
    this.time = time;
    this.stop = stop;
  }

  @Override
  public String toString() {
    return "Schedule{" +
      "time='" + time + '\'' +
      ", stop=" + stop +
      '}';
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(time);
    parcel.writeValue(stop);
  }

  public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
    public Schedule createFromParcel(Parcel in) {
      return new Schedule(in);
    }

    public Schedule[] newArray(int size) {
      return new Schedule[size];
    }
  };

  public Schedule(Parcel in){
    this.time = in.readString();
    this.stop = (BusStop) in.readValue(getClass().getClassLoader());
  }
}
