package com.example.guest.farmersmarket.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.guest.farmersmarket.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReviewsActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.readReviewsButton) Button mReadReviewsButton;
    @Bind(R.id.editTextZip) EditText mEditTextZip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        ButterKnife.bind(this);

        mReadReviewsButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String zip_code = mEditTextZip.getText().toString();
        Intent intent = new Intent(ReviewsActivity.this, ReviewsListActivity.class);
        intent.putExtra("zip_code", zip_code);
        startActivity(intent);

    }
}
