package com.example.guest.farmersmarket.ui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.guest.farmersmarket.R;
import android.location.LocationListener;
import android.widget.Toast;

import android.location.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    public double userLat;
    public double userLong;
    public LatLng userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    private final LocationListener listener = new LocationListener() {
        public void onLocationChanged(Location location) {

            userLong = location.getLongitude();
            userLat = location.getLatitude();

            System.out.println("HI " + location.getLatitude());


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ///REFRESHING LOCATION AND CURRENT MESSAGES VISIBLE.
                    userLocation = new LatLng(userLat, userLong);
                    System.out.println("HELLO " + userLocation);
                    // Add a marker in Sydney and move the camera
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


    public String getLocationInfo() {
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        ArrayList name = new ArrayList();
        try {
            List<Address> address = geocoder.getFromLocation(userLat, userLong, 1);
            Address userLocationInfo = address.get(0);
            name.add(userLocationInfo.getAddressLine(0));
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Location not found.", Toast.LENGTH_SHORT).show();
        }
        return name.get(0).toString();
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
