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

public class AddLocationActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.findMarketButton) Button mFindMarketButton;
    @Bind(R.id.zipCodeEditText)
    EditText mZipCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        ButterKnife.bind(this);
        mFindMarketButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        String zip_code = mZipCodeEditText.getText().toString();
        Intent intent = new Intent(AddLocationActivity.this, MarketListActivity.class);
        intent.putExtra("zip_code", zip_code);
        startActivity(intent);

    }
}
