package com.example.guest.farmersmarket;

/**
 * Created by Guest on 7/26/16.
 */
public class MarketDetails {
    private String address;
    private String products;
    private String schedule;

    public MarketDetails () {}

    public MarketDetails(String address, String products, String schedule) {
        this.address = address;
        this.products = products;
        this.schedule = schedule;
    }

    public String getAddress(){
        return this.address;
    }

    public String getProducts() {
        return this.products;
    }

    public String getSchedule() {
        return this.schedule;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
