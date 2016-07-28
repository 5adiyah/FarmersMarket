package com.example.guest.farmersmarket.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.guest.farmersmarket.MarketDetails;
import com.example.guest.farmersmarket.R;
import com.example.guest.farmersmarket.models.Market;
import com.example.guest.farmersmarket.models.Review;
import com.example.guest.farmersmarket.service.MarketService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class MarketDetailFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.reviewText) TextView mReviewText;
    @Bind(R.id.marketNameTextView) TextView mMarketNameTextView;
    @Bind(R.id.marketScheduleTextView) TextView mMarketScheduleTextView;
    @Bind(R.id.marketAddressTextView) TextView mMarketAddressTextView;
    @Bind(R.id.marketProductsTextView) TextView mMarketProductsTextView;
    @Bind(R.id.submitReviewButton) Button mSubmitReviewButton;

    private Market mMarket;
    private DatabaseReference mEventReference;

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
        getMarketDetails(mMarket);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_detail, container, false);
        ButterKnife.bind(this, view);

        mEventReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Review");

        mMarketNameTextView.setText(mMarket.getMarketName()); //this changes the title
        mSubmitReviewButton.setOnClickListener(this);
        return view;
    }

    public void onClick(View v){
        if(v==mSubmitReviewButton){
            String marketId = mMarket.getId();
            String reviewText = mReviewText.getText().toString();
            Review review = new Review(marketId, reviewText, mMarket.getMarketName());
            saveReviewToFirebase(review);

        }
    }

    public void saveReviewToFirebase(Review review){
        mEventReference.push().setValue(review);
    }

    private void getMarketDetails(Market market) {
        final MarketService marketService = new MarketService();
        marketService.findMarketDetails(market, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {
                        JSONObject marketJson = new JSONObject(jsonData);
                        JSONObject marketDetailsJson = marketJson.getJSONObject("marketdetails");

                        String oldAddress = marketDetailsJson.getString("Address");
//                        int parenthesisIndex1 = oldAddress.indexOf("(");
//                        int parenthesisIndex2 = oldAddress.indexOf(")");
//                        String replace = oldAddress.substring(parenthesisIndex1 -1, parenthesisIndex2 + 1);
//                        String address = oldAddress.replace(replace, "");

                        String products = marketDetailsJson.getString("Products");
                        String schedule = marketDetailsJson.getString("Schedule");
                        final MarketDetails marketDetails = new MarketDetails(oldAddress, products, schedule);
                        Log.d("marketDetails address", marketDetails.getAddress());

//                        mMarketDetailsArray.add(marketDetails);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMarketAddressTextView.setText(marketDetails.getAddress());
                                mMarketScheduleTextView.setText(marketDetails.getSchedule());
                                mMarketProductsTextView.setText(marketDetails.getProducts());
                            }
                        });

//                        Log.d("array", String.valueOf(mMarketDetailsArray.size()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (StringIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
