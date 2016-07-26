package com.example.guest.farmersmarket.service;

import com.example.guest.farmersmarket.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Guest on 7/26/16.
 */
public class ApiService {
    public static void findMarkets(String longitude, String latitude, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.API_LAT_PARAMETER, longitude);
        urlBuilder.addQueryParameter(Constants.API_LNG_PARAMETER, latitude);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}