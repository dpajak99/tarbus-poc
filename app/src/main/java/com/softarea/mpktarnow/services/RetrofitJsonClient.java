package com.softarea.mpktarnow.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitJsonClient {
  private static RetrofitJsonClient INSTANCE = null;

  private MpkService mpkService;

  public static RetrofitJsonClient getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new RetrofitJsonClient();
    }

    return INSTANCE;
  }

  private RetrofitJsonClient() {
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("http://rozklad.komunikacja.tarnow.pl/Home/")
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    this.mpkService = retrofit.create(MpkService.class);
  }

  public MpkService getMPKService() {
    return this.mpkService;
  }
}
