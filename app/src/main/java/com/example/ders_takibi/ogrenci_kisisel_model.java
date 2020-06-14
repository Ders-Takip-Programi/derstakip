package com.example.ders_takibi;

public class ogrenci_kisisel_model {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getGun() {
        return gun;
    }

    public void setGun(String gun) {
        this.gun = gun;
    }

    public String getYapilan() {
        return yapilan;
    }

    public void setYapilan(String yapilan) {
        this.yapilan = yapilan;
    }

    public String getSsayisi() {
        return ssayisi;
    }

    public void setSsayisi(String ssayisi) {
        this.ssayisi = ssayisi;
    }

    public String getPuan() {
        return puan;
    }

    public void setPuan(String puan) {
        this.puan = puan;
    }

    public String getArtieksi() {
        return artieksi;
    }

    public void setArtieksi(String artieksi) {
        this.artieksi = artieksi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    String tarih;
    String gun;
    String yapilan;
    String ssayisi;
    String puan;
    String artieksi;
    String aciklama;

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public String getBool_yapti() {
        return bool_yapti;
    }

    public void setBool_yapti(String bool_yapti) {
        this.bool_yapti = bool_yapti;
    }

    String saat,bool_yapti;
    public ogrenci_kisisel_model(int id, String tarih, String gun, String yapilan, String ssayisi, String puan, String artieksi, String aciklama,String saat,String bool_yapti) {
        this.id = id;
        this.tarih = tarih;
        this.gun = gun;
        this.yapilan = yapilan;
        this.ssayisi = ssayisi;
        this.puan = puan;
        this.artieksi = artieksi;
        this.aciklama = aciklama;
        this.saat=saat;
        this.bool_yapti=bool_yapti;
    }


}
