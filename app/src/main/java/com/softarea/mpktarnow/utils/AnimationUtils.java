package com.softarea.mpktarnow.utils;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

public class AnimationUtils {
  public static void createPulsarLogo(View view) {
    ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
      view,
      PropertyValuesHolder.ofFloat("scaleX", 1.03f),
      PropertyValuesHolder.ofFloat("scaleY", 1.03f));
    scaleDown.setDuration(500);

    scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
    scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

    scaleDown.start();
  }
}
