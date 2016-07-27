package com.example.guest.farmersmarket.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guest.farmersmarket.R;
import com.example.guest.farmersmarket.adapters.MarketPagerAdapter;
import com.example.guest.farmersmarket.models.Market;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

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

        adapterViewPager = new MarketPagerAdapter(getSupportFragmentManager(), mMarkets);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
