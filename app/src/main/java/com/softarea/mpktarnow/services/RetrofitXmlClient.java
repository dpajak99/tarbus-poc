package com.softarea.mpktarnow.services;

import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import retrofit2.Retrofit;

public class RetrofitXmlClient {
  private static RetrofitXmlClient INSTANCE = null;

  private MpkService mpkService;

  public static RetrofitXmlClient getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new RetrofitXmlClient();
    }

    return INSTANCE;
  }

  private RetrofitXmlClient() {
    TikXml tikxml = new TikXml.Builder().exceptionOnUnreadXml(true).build();
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("http://rozklad.komunikacja.tarnow.pl/Home/")
      .addConverterFactory(TikXmlConverterFactory.create(tikxml))
      .build();

    this.mpkService = retrofit.create(MpkService.class);
  }

  public MpkService getMPKService() {
    return this.mpkService;
  }
}
