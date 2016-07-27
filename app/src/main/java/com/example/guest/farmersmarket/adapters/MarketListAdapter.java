package com.example.guest.farmersmarket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guest.farmersmarket.R;
import com.example.guest.farmersmarket.models.Market;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 7/27/16.
 */
public class MarketListAdapter extends RecyclerView.Adapter<MarketListAdapter.MarketViewHolder>{

    private ArrayList<Market> mMarkets = new ArrayList<>();
    private Context mContext;

    public MarketListAdapter(Context context, ArrayList<Market> markets) {
        mContext = context;
        mMarkets = markets;
    }

    @Override
    public MarketListAdapter.MarketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_list_item, parent, false);
        MarketViewHolder viewHolder = new MarketViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MarketListAdapter.MarketViewHolder holder, int position) {
        holder.bindMarket(mMarkets.get(position));

    }

    @Override
    public int getItemCount() {
        return mMarkets.size();
    }

    public class MarketViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.marketNameTextView)
        TextView mMarketNameTextView;

        private Context mContext;


        public MarketViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindMarket(Market market) {
            mMarketNameTextView.setText(market.getMarketName());
        }
    }
}
