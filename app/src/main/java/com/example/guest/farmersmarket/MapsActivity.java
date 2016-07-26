package com.example.guest.farmersmarket;

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

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener{
    private GoogleMap mMap;
    private LocationManager locationManager;
    public double userLat;
    public double userLong;
    public LatLng userLocation;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public ArrayList<Market> markets = new ArrayList<Market>();


    @Bind(R.id.submitMarket) Button mSubmitMarket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_market);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                            , 10);
                }
                return;
            }
            locationManager.requestLocationUpdates(provider, 1000, 0, listener);
        }

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };

        mSubmitMarket.setOnClickListener(this);
    }

    private void getMarkets(double latitude, double longitude){
        final ApiService apiService = new ApiService();
        apiService.findMarkets(String.valueOf(longitude), String.valueOf(latitude), new Callback() {
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
                        JSONArray marketsJson = marketJson.getJSONArray("results");
                        for (int i = 0; i < marketsJson.length(); i++) {
                            JSONObject marketObject = marketsJson.getJSONObject(i);
                            String id = marketObject.getString("id");
                            String marketname = marketObject.getString("marketname");
                            Market market = new Market(id, marketname);
                            markets.add(market);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ArrayList<MarketDetails> getMarketDetails(ArrayList<Market> markets) {
        final ArrayList<MarketDetails> marketDetailsArray = new ArrayList<MarketDetails>();
        final MarketService marketService = new MarketService();
        for(Market market : markets) {
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
                            int parenthesisIndex1 = oldAddress.indexOf("(");
                            int parenthesisIndex2 = oldAddress.indexOf(")");
                            String replace = oldAddress.substring(parenthesisIndex1 -1, parenthesisIndex2 + 1);
                            String address = oldAddress.replace(replace, "");

                            String products = marketDetailsJson.getString("Products");
                            String schedule = marketDetailsJson.getString("Schedule");
                            MarketDetails marketDetails = new MarketDetails(address, products, schedule);
                            marketDetailsArray.add(marketDetails);
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

        System.out.println("YO " + marketDetailsArray.size());
        return marketDetailsArray;
    }

    public void onClick(View view){
        if(view == mSubmitMarket){
            ArrayList<MarketDetails> marketDetails = getMarketDetails(markets);
            if(marketDetails.size() > 0){
                LatLng newLatLng = getNewUserLocation(marketDetails.get(0).getAddress());
                mMap.addMarker(new MarkerOptions().position(newLatLng).title("Marker at " + newLatLng));
//                for(MarketDetails md : marketDetails){
//                    LatLng newLatLng = getNewUserLocation(md.getAddress());
//                    mMap.addMarker(new MarkerOptions().position(newLatLng).title("Marker at " + md.getAddress()));
//                }
            }
        }
    }

    private final LocationListener listener = new LocationListener() {
        public void onLocationChanged(Location location) {

            userLong = location.getLongitude();
            userLat = location.getLatitude();


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getMarkets(userLong, userLat);
                    ///REFRESHING LOCATION AND MARKER
                    userLocation = new LatLng(userLat, userLong);
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(userLocation).title("Marker at " + getLocationInfo()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public LatLng getNewUserLocation(String newLocation) {
        Geocoder coder = new Geocoder(MapsActivity.this);
        List<Address> address = new ArrayList<Address>();

        List<LatLng> stuff = new ArrayList<LatLng>();
        LatLng newLatLng;

        try {
            address = coder.getFromLocationName(newLocation, 5);
            if (address == null) {
                Toast.makeText(MapsActivity.this, "Location not found.", Toast.LENGTH_SHORT).show();
            }
            Address location = address.get(0);
            newLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            stuff.add(newLatLng);
        } catch (IOException e) {
            Toast.makeText(MapsActivity.this, "Location not found.", Toast.LENGTH_SHORT).show();
        }
        return stuff.get(0);
    }


    public String getLocationInfo() {
        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
        ArrayList name = new ArrayList();
        try {
            List<Address> address = geocoder.getFromLocation(userLat, userLong, 5);
            Address userLocationInfo = address.get(0);
            name.add(userLocationInfo.getAddressLine(0));
        } catch (IOException e) {
            Toast.makeText(MapsActivity.this, "Location not found.", Toast.LENGTH_SHORT).show();
        }
        return name.get(0).toString();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

