package com.example.guest.farmersmarket.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.media.MediaRouteProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.guest.farmersmarket.Market;
import com.example.guest.farmersmarket.R;
import com.example.guest.farmersmarket.adapters.MarketsViewHolder;
import com.example.guest.farmersmarket.service.ApiService;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ReviewsActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.seeReviewsButton) Button mSeeReviewsButton;
    @Bind(R.id.editTextZipCode) EditText mEditTextZipCode;
    @Bind(R.id.enterZip) RelativeLayout mEnterZip;
    @Bind(R.id.viewMarkets) RelativeLayout mViewMarkets;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    private DatabaseReference mMarketsReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    ArrayList<Market> markets = new ArrayList<Market>();

    private Boolean zipCodeVisible = true;
    private Boolean viewMarketsVisible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        ButterKnife.bind(this);

        mMarketsReference = FirebaseDatabase.getInstance().getReference().child("Markets");

        setUpFirebaseAdapter();

        mSeeReviewsButton.setOnClickListener(this);


    }

    private void setUpFirebaseAdapter(){
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Market, MarketsViewHolder>
                (Market.class, R.layout.activity_market_list, MarketsViewHolder.class, mMarketsReference) {

            @Override
            protected void populateViewHolder(MarketsViewHolder viewHolder, Market model, int position){
                viewHolder.bindMarket(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    private void getMarkets(String zip_code) {
        final ApiService apiServivce = new ApiService();

        apiServivce.findMarkets(zip_code, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    if(response.isSuccessful()) {
                        JSONObject marketsJSON = new JSONObject(jsonData);
                        JSONArray marketResults = marketsJSON.getJSONArray("results");
                        for(int i = 0; i < marketResults.length(); i++) {
                            JSONObject market = marketResults.getJSONObject(i);
                            String id = market.getString("id");
                            String marketName = market.getString("marketname");
                            Market newMarket = new Market(id, marketName);
                            mMarketsReference.push().setValue(newMarket);
                            markets.add(newMarket);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Log.d("These are the markets!", String.valueOf(markets.size()));
    }

    public void onClick(View view) {
        if(view == mSeeReviewsButton) {
            String zip_code = mEditTextZipCode.getText().toString().trim();
            getMarkets(zip_code);
            Log.d("These are the markets!", String.valueOf(markets.size()));
            if(zipCodeVisible) {
                mEnterZip.setVisibility(View.GONE);
                //mViewMarkets.setVisibility(View.VISIBLE);
            }
        }
    }

}
