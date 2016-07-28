package com.example.guest.farmersmarket.models;

/**
 * Created by Guest on 7/27/16.
 */
public class Review {
    String marketId;
    String reviewText;
    String marketName;

    public Review(){}

    public Review(String marketId, String reviewText, String marketName){
        this.marketId = marketId;
        this.reviewText = reviewText;
        this.marketName = marketName;
    }

    public String getMarketName() {
        return marketName;
    }

    public String getMarketId() {
        return marketId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }
}
