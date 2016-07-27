package com.example.guest.farmersmarket.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.guest.farmersmarket.models.Market;
import com.example.guest.farmersmarket.ui.MarketDetailFragment;

import java.util.ArrayList;

/**
 * Created by Guest on 7/27/16.
 */
public class MarketPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Market> mMarkets;

    public MarketPagerAdapter(FragmentManager fm, ArrayList<Market> markets) {
        super(fm);
        mMarkets = markets;
    }

    @Override
    public Fragment getItem(int position) {
        return MarketDetailFragment.newInstance(mMarkets.get(position));
    }

    @Override
    public int getCount() {
        return mMarkets.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMarkets.get(position).getMarketName();
    }
}
