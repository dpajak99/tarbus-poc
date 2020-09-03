package com.softarea.mpktarnow.model;

public class Vehicle {
  int nrRadia;
  int nb;
  String numerLini;
  String warTrasy;
  String kierunek;
  int idKursu;
  int lpPrzyst;
  int drogaPlan;
  int drogaWyko;
  double dlugosc;
  double szerokosc;
  double prevDlugosc;
  double prevSzerokosc;
  int odchylenie;
  String odchylenieStr;
  int stan;
  String planGodzRozp;
  int nastIdKursu;
  String nastPlanGodzRozp;
  String nastNumLini;
  String nastWarTrasy;
  String nastKierunek;
  int ileSekDoOdjazdu;
  String typPojazdu;
  String cechy1;
  String destination;
  String cechy3;
  int wektor;

  public Vehicle(int nrRadia, int nb, String numerLini, String warTrasy, String kierunek, int idKursu, int lpPrzyst, int drogaPlan, int drogaWyko, double dlugosc, double szerokosc, double prevDlugosc, double prevSzerokosc, int odchylenie, String odchylenieStr, int stan, String planGodzRozp, int nastIdKursu, String nastPlanGodzRozp, String nastNumLini, String nastWarTrasy, String nastKierunek, int ileSekDoOdjazdu, String typPojazdu, String cechy1, String destination, String cechy3, int wektor) {
    this.nrRadia = nrRadia;
    this.nb = nb;
    this.numerLini = numerLini;
    this.warTrasy = warTrasy;
    this.kierunek = kierunek;
    this.idKursu = idKursu;
    this.lpPrzyst = lpPrzyst;
    this.drogaPlan = drogaPlan;
    this.drogaWyko = drogaWyko;
    this.dlugosc = dlugosc;
    this.szerokosc = szerokosc;
    this.prevDlugosc = prevDlugosc;
    this.prevSzerokosc = prevSzerokosc;
    this.odchylenie = odchylenie;
    this.odchylenieStr = odchylenieStr;
    this.stan = stan;
    this.planGodzRozp = planGodzRozp;
    this.nastIdKursu = nastIdKursu;
    this.nastPlanGodzRozp = nastPlanGodzRozp;
    this.nastNumLini = nastNumLini;
    this.nastWarTrasy = nastWarTrasy;
    this.nastKierunek = nastKierunek;
    this.ileSekDoOdjazdu = ileSekDoOdjazdu;
    this.typPojazdu = typPojazdu;
    this.cechy1 = cechy1;
    this.destination = destination;
    this.cechy3 = cechy3;
    this.wektor = wektor;
  }

  public Vehicle() {
  }

  @Override
  public String toString() {
    return "Vehicle{" +
      "nrRadia=" + nrRadia +
      ", nb=" + nb +
      ", numerLini='" + numerLini + '\'' +
      ", warTrasy='" + warTrasy + '\'' +
      ", kierunek='" + kierunek + '\'' +
      ", idKursu=" + idKursu +
      ", lpPrzyst=" + lpPrzyst +
      ", drogaPlan=" + drogaPlan +
      ", drogaWyko=" + drogaWyko +
      ", dlugosc=" + dlugosc +
      ", szerokosc=" + szerokosc +
      ", prevDlugosc=" + prevDlugosc +
      ", prevSzerokosc=" + prevSzerokosc +
      ", odchylenie=" + odchylenie +
      ", odchylenieStr='" + odchylenieStr + '\'' +
      ", stan=" + stan +
      ", planGodzRozp='" + planGodzRozp + '\'' +
      ", nastIdKursu=" + nastIdKursu +
      ", nastPlanGodzRozp='" + nastPlanGodzRozp + '\'' +
      ", nastNumLini='" + nastNumLini + '\'' +
      ", nastWarTrasy='" + nastWarTrasy + '\'' +
      ", nastKierunek='" + nastKierunek + '\'' +
      ", ileSekDoOdjazdu=" + ileSekDoOdjazdu +
      ", typPojazdu='" + typPojazdu + '\'' +
      ", cechy1='" + cechy1 + '\'' +
      ", cechy2='" + destination + '\'' +
      ", cechy3='" + cechy3 + '\'' +
      ", wektor=" + wektor +
      '}';
  }

  public int getNrRadia() {
    return nrRadia;
  }

  public int getNb() {
    return nb;
  }

  public String getNumerLini() {
    return numerLini;
  }

  public String getWarTrasy() {
    return warTrasy;
  }

  public String getKierunek() {
    return kierunek;
  }

  public int getIdKursu() {
    return idKursu;
  }

  public int getLpPrzyst() {
    return lpPrzyst;
  }

  public int getDrogaPlan() {
    return drogaPlan;
  }

  public int getDrogaWyko() {
    return drogaWyko;
  }

  public double getDlugosc() {
    return dlugosc;
  }

  public double getSzerokosc() {
    return szerokosc;
  }

  public double getPrevDlugosc() {
    return prevDlugosc;
  }

  public double getPrevSzerokosc() {
    return prevSzerokosc;
  }

  public int getOdchylenie() {
    return odchylenie;
  }

  public String getOdchylenieStr() {
    return odchylenieStr;
  }

  public int getStan() {
    return stan;
  }

  public String getPlanGodzRozp() {
    return planGodzRozp;
  }

  public int getNastIdKursu() {
    return nastIdKursu;
  }

  public String getNastPlanGodzRozp() {
    return nastPlanGodzRozp;
  }

  public String getNastNumLini() {
    return nastNumLini;
  }

  public String getNastWarTrasy() {
    return nastWarTrasy;
  }

  public String getNastKierunek() {
    return nastKierunek;
  }

  public int getIleSekDoOdjazdu() {
    return ileSekDoOdjazdu;
  }

  public String getTypPojazdu() {
    return typPojazdu;
  }

  public String getCechy1() {
    return cechy1;
  }

  public String getDestination() {
    return destination;
  }

  public String getCechy3() {
    return cechy3;
  }

  public int getWektor() {
    return wektor;
  }
}
