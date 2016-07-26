package com.example.guest.farmersmarket.service;

import com.example.guest.farmersmarket.Constants;
import com.example.guest.farmersmarket.Market;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Guest on 7/26/16.
 */
public class MarketService {
    public static void findMarketDetails(Market market, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_MARKET_DETAILS).newBuilder();
        urlBuilder.addQueryParameter("id", market.getId());
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
