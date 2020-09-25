package com.softarea.mpktarnow.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.model.SearchResultPoint;

import java.util.List;

public class MainActivity extends AppCompatActivity {

  public static Double lat_from;
  public static Double lng_from;
  public static Double lat_to;
  public static Double lng_to;

  public static Double lat_current;
  public static Double lng_current;

  public static List<SearchResultPoint> searchConnectionList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    BottomNavigationView navView = findViewById(R.id.nav_view);
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

    NavigationUI.setupWithNavController(navView, navController);
  }

}
