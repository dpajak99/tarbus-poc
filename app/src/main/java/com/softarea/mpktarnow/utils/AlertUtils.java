package com.softarea.mpktarnow.utils;

import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

public class AlertUtils {
  public static void alert(FragmentActivity activity, String text) {
    Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
  }
}
