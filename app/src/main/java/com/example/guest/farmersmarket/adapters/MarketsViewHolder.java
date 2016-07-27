package com.example.guest.farmersmarket.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.guest.farmersmarket.Market;
import com.example.guest.farmersmarket.R;
import com.example.guest.farmersmarket.ui.ReviewsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;


public class MarketsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    View mView;
    Context mContext;

    public MarketsViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindMarket(Market market){
        TextView marketName = (TextView) mView.findViewById(R.id.marketName);
        marketName.setText(market.getMarketName());
    }

    @Override public void onClick(View view){
        final ArrayList<Market> markets = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference("Market");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    markets.add(snapshot.getValue(Market.class));
                }

                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, ReviewsActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("markets", Parcels.wrap(markets));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
