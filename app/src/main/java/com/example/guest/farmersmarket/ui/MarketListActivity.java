package com.example.guest.farmersmarket.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.guest.farmersmarket.R;
import com.example.guest.farmersmarket.service.ApiService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MarketListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_list);

        Intent intent = getIntent();
        String zip_code = intent.getStringExtra("zip_code");
        getMarkets(zip_code);

    }

    private void getMarkets(String zip_code){
        final ApiService apiService = new ApiService();
        apiService.findMarkets(zip_code, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v("JsonData is", jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

}
