package com.example.guest.farmersmarket.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.guest.farmersmarket.R;
import com.example.guest.farmersmarket.adapters.MarketListAdapter;
import com.example.guest.farmersmarket.models.Market;
import com.example.guest.farmersmarket.service.ApiService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MarketListActivity extends AppCompatActivity {
    public ArrayList<Market> mMarkets = new ArrayList<>();
    private MarketListAdapter mAdapter;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String zip_code = intent.getStringExtra("zip_code");
        getMarkets(zip_code);

    }

    private void getMarkets(String zip_code) {
        final ApiService apiService = new ApiService();
        apiService.findMarkets(zip_code, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {

                mMarkets = apiService.processResults(response);
                MarketListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new MarketListAdapter(getApplicationContext(), mMarkets);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(MarketListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });


            }

        });
    }

}
