package com.example.guest.farmersmarket.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.guest.farmersmarket.R;
import com.example.guest.farmersmarket.adapters.MarketListAdapter;
import com.example.guest.farmersmarket.adapters.ReviewsListAdapter;
import com.example.guest.farmersmarket.models.Market;
import com.example.guest.farmersmarket.models.Review;
import com.example.guest.farmersmarket.service.ApiService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ReviewsListActivity extends AppCompatActivity {

    public ArrayList<Review> mReviews = new ArrayList<>();
    public ArrayList<Market> mMarkets = new ArrayList<>();
    private ReviewsListAdapter mAdapter;
    @Bind(R.id.reviewsRecyclerView) RecyclerView mRecyclerView;

    private DatabaseReference mReviewsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_list);
        ButterKnife.bind(this);

        mReviewsReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Review");

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

                mReviewsReference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                            Review review = reviewSnapshot.getValue(Review.class);
                            for(Market market: mMarkets) {
                                if(market.getId().equals(review.getMarketId())) {
                                    mReviews.add(review);
                                }
                            }
                        }

                        ReviewsListActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter = new ReviewsListAdapter(getApplicationContext(), mReviews);
                                mRecyclerView.setAdapter(mAdapter);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReviewsListActivity.this);
                                mRecyclerView.setLayoutManager(layoutManager);
                                mRecyclerView.setHasFixedSize(true);
                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

        });
    }


}
