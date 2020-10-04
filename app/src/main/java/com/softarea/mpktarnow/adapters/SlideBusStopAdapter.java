package com.softarea.mpktarnow.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.softarea.mpktarnow.ui.BusStopDetailsFragment;
import com.softarea.mpktarnow.ui.ErrorFragment;
import com.softarea.mpktarnow.ui.ScheduleFragment;

public class SlideBusStopAdapter extends FragmentStateAdapter {
  private static final int NUM_PAGES = 2;
  public SlideBusStopAdapter(@NonNull Fragment fragment) {
    super(fragment);
  }
  @NonNull
  @Override
  public Fragment createFragment(int position) {
    switch(position) {
      case 0:
        return new BusStopDetailsFragment();
      case 1:
        return new ScheduleFragment();
      default:
        return new ErrorFragment();
    }
  }

  @Override
  public int getItemCount() {
    return NUM_PAGES;
  }
}
