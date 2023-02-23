package com.example.myfirstapp;



public class MusteriList {
    public String ad;
    public String no;
    public String adres;
    public String alınan;
    public long id;

    public MusteriList(String ad, String no, String adres, String alınan, long id) {
        this.ad = ad;
        this.no = no;
        this.adres = adres;
        this.alınan = alınan;
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public String getNo() {
        return no;
    }

    public String getAdres() {
        return adres;
    }

    public String getAlınan() {
        return alınan;
    }

    public long getId() {
        return id;
    }
}
