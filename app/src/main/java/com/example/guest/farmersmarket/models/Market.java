package com.example.guest.farmersmarket.models;

import org.parceler.Parcel;


@Parcel
public class Market {
    String id;
    String marketName;

    public Market(){}

    public Market(String marketName, String id) {
        this.id = id;
        this.marketName = marketName;

    }

    public String getId() {
        return id;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

}
