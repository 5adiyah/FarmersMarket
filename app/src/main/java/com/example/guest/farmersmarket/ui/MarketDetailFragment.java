package com.example.guest.farmersmarket.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guest.farmersmarket.R;
import com.example.guest.farmersmarket.models.Market;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarketDetailFragment extends Fragment {
    @Bind(R.id.marketNameTextView)
    TextView mMarketNameTextView;

    private Market mMarket;

    public static MarketDetailFragment newInstance(Market market) {
        MarketDetailFragment marketDetailFragment = new MarketDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("marketName", Parcels.wrap(market));
        marketDetailFragment.setArguments(args);
        return marketDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMarket = Parcels.unwrap(getArguments().getParcelable("marketName"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_detail, container, false);
        ButterKnife.bind(this, view);

        mMarketNameTextView.setText(mMarket.getMarketName());

        return view;
    }

}
