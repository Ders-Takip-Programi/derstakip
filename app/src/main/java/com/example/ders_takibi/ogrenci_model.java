package com.example.ders_takibi;

public class ogrenci_model {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getVeli_isim() {
        return veli_isim;
    }

    public void setVeli_isim(String veli_isim) {
        this.veli_isim = veli_isim;
    }

    public String getVeli_tel() {
        return veli_tel;
    }

    public void setVeli_tel(String veli_tel) {
        this.veli_tel = veli_tel;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    String isim;
    String soyisim;
    String tc;
    String veli_isim;
    String veli_tel;
    String adres;

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    String saat;
    public ogrenci_model(int id, String isim, String soyisim, String tc, String veli_isim, String veli_tel, String adres,String saat) {
        this.id = id;
        this.isim = isim;
        this.soyisim = soyisim;
        this.tc = tc;
        this.veli_isim = veli_isim;
        this.veli_tel = veli_tel;
     this.saat=saat;
        this.adres = adres;
    }



}
