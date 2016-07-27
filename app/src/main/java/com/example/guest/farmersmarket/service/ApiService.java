package com.example.guest.farmersmarket.service;

import android.util.ArrayMap;
import android.util.Log;

import com.example.guest.farmersmarket.Constants;
import com.example.guest.farmersmarket.models.Market;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Guest on 7/26/16.
 */
public class ApiService {
    public static void findMarkets(String zip_code, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.API_ZIP_PARAMETER, zip_code);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Log.d("findMarkets: ", url);

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Market> processResults(Response response){
        ArrayList<Market> markets = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject apiJSON = new JSONObject(jsonData);
                JSONArray resultsJSON = apiJSON.getJSONArray("results");
                for (int i = 0; i < resultsJSON.length(); i++) {
                    JSONObject marketJSON = resultsJSON.getJSONObject(i);
                    String marketName = marketJSON.getString("marketname");
                    String marketId = marketJSON.getString("id");

                    Market market = new Market(marketName, marketId);
                    markets.add(market);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return markets;

    }
}
