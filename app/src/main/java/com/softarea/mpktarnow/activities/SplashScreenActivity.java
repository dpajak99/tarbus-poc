package com.softarea.mpktarnow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.utils.AnimationUtils;

public class SplashScreenActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);

    ImageView logoView = findViewById(R.id.logo);
    AnimationUtils.createPulsarLogo(logoView);


    final Handler handler = new Handler();
    handler.postDelayed(() -> {
      Intent intent = new Intent(getApplicationContext(), MainActivity.class);
      startActivity(intent);
      finish();
    }, 1);
  }
}
