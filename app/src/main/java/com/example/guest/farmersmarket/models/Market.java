package com.example.guest.farmersmarket.models;

/**
 * Created by Guest on 7/26/16.
 */

public class Market {
    private String id;
    private String marketName;

    public Market(){}

    public Market(String marketName, String id) {
        this.id = id;
        this.marketName = marketName;

    }

    public String getId() {
        return this.id;
    }

    public String getMarketName() {
        return this.marketName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

}
