package com.example.guest.farmersmarket;

/**
 * Created by Guest on 7/26/16.
 */
public class Constants {
    public static final String API_BASE_URL = "http://search.ams.usda.gov/farmersmarkets/v1/data.svc/locSearch?";
    public static final String API_LAT_PARAMETER = "lat";
    public static final String API_LNG_PARAMETER = "lng";
    public static final String API_MARKET_DETAILS="http://search.ams.usda.gov/farmersmarkets/v1/data.svc/mktDetail?id=";
}

// url: "http://search.ams.usda.gov/farmersmarkets/v1/data.svc/locSearch?lat=" + lat + "&lng=" + lng,