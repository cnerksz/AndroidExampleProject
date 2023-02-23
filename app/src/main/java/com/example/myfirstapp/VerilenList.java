package com.example.myfirstapp;

import java.util.Date;

public class VerilenList {
    String verilen;
    int adet;
    float fiyat;
    Date gün;
    long ID;

    public VerilenList(String verilen, int adet, float fiyat, Date gün, long ID) {
        this.verilen = verilen;
        this.adet = adet;
        this.fiyat = fiyat;
        this.gün = gün;
        this.ID = ID;
    }

    public String getVerilen() {
        return verilen;
    }

    public int getAdet() {
        return adet;
    }

    public float getFiyat() {
        return fiyat;
    }

    public Date getGün() {
        return gün;
    }

    public long getID() {
        return ID;
    }
}
