package com.example.guest.farmersmarket.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.guest.farmersmarket.R;
import com.example.guest.farmersmarket.models.Market;
import com.example.guest.farmersmarket.models.MarketDetails;
import com.example.guest.farmersmarket.service.ApiService;
import com.example.guest.farmersmarket.service.MarketService;
import com.example.guest.farmersmarket.ui.LoginActivity;
import com.example.guest.farmersmarket.ui.NearbyMarketsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    public double userLat;
    public double userLong;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public ArrayList<Market> markets = new ArrayList<Market>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_market);
//        mMarketDetails=marketdetails;
        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void getNewUserLocation(String newLocation) {

        Geocoder coder = new Geocoder(MapsActivity.this);
        List<Address> address;

        LatLng newLatLng;

        try {
            address = coder.getFromLocationName(newLocation, 5);

            if (address.size() != 0) {
                Address location = address.get(0);
                newLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(newLatLng).title("Marker at " + newLocation));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLng));
            } else {
                String epicodus = "400 SW 6th Avenue, Portland, Oregon";
                address = coder.getFromLocationName(epicodus, 5);
                Address location = address.get(0);
                newLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(newLatLng).title("Epicodus at: " + epicodus));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLng));
            }
        } catch (IOException e) {

            Toast.makeText(MapsActivity.this, "Location not found.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        getNewUserLocation(address);

    }
}

