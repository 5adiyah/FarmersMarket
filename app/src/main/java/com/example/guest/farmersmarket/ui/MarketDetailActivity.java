package com.example.guest.farmersmarket.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.guest.farmersmarket.models.MarketDetails;
import com.example.guest.farmersmarket.R;
import com.example.guest.farmersmarket.adapters.MarketPagerAdapter;
import com.example.guest.farmersmarket.models.Market;
import com.example.guest.farmersmarket.service.MarketService;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MarketDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    private MarketPagerAdapter adapterViewPager;
    ArrayList<Market> mMarkets = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_detail);
        ButterKnife.bind(this);

        mMarkets = Parcels.unwrap(getIntent().getParcelableExtra("marketName"));
        int startingPosition = getIntent().getIntExtra("position", 0);
        Log.d("the id", String.valueOf(mMarkets.get(startingPosition).getId()));


        adapterViewPager = new MarketPagerAdapter(getSupportFragmentManager(), mMarkets);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);

    }


    //API GOES HERE!

}
