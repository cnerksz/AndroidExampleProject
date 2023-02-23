package com.example.myfirstapp;

import java.util.Date;

public class AlinanList {
    public String alınan;
    public Float fiyat;
    public Date gün;
    public long alınanId;
    public AlinanList(String alınan, Float fiyat, Date gün,long alınanId) {
        this.alınan = alınan;
        this.fiyat = fiyat;
        this.gün = gün;
        this.alınanId=alınanId;
    }
    public String getAlınan() {
        return alınan;
    }

    public Float getFiyat() {
        return fiyat;
    }

    public Date getGün() {
        return gün;
    }

    public long getAlınanId() {
        return alınanId;
    }
}
