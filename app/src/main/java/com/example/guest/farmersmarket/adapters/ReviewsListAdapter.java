package com.example.guest.farmersmarket.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guest.farmersmarket.R;
import com.example.guest.farmersmarket.models.Market;
import com.example.guest.farmersmarket.models.Review;
import com.example.guest.farmersmarket.ui.MarketDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 7/28/16.
 */
public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListAdapter.ReviewsViewHolder>{
    private ArrayList<Review> mReviews = new ArrayList<>();
    private Context mContext;

    public ReviewsListAdapter(Context context, ArrayList<Review> reviews) {
        System.out.println("YOOO " + reviews);
        mContext = context;
        mReviews = reviews;
    }

    @Override
    public ReviewsListAdapter.ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        ReviewsViewHolder viewHolder = new ReviewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewsListAdapter.ReviewsViewHolder holder, int position) {
        holder.bindReview(mReviews.get(position));

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.marketNameTextView) TextView mMarketNameTextView;
        @Bind(R.id.reviewTextView) TextView mReviewTextView;

        private Context mContext;


        public ReviewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

        }

        public void bindReview(Review review) {
            mMarketNameTextView.setText(review.getMarketName());
            mReviewTextView.setText(review.getReviewText());
        }
    }

}
