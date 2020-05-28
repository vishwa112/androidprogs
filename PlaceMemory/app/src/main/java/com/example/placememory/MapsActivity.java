package com.example.placememory;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.placememory.MainActivity.locations;
import static com.example.placememory.MainActivity.places;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    LocationManager lm;
    LocationListener ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent i= getIntent();
        int position;
        position = i.getIntExtra("placenumber",0);

        Log.i("positions",String.valueOf(position));
       // Toast.makeText(this,Integer.toString(position),Toast.LENGTH_SHORT).show();

        if (position == 0) {
            lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            ll = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mapLocation(location, "Your location");
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
              //  lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,ll);
                Location lastlocation=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                mapLocation(lastlocation,"last location");

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }

        } else {
            Location memomries = new Location(LocationManager.GPS_PROVIDER);
            LatLng lng = MainActivity.locations.get(position);
            String s = places.get(position);
            memomries.setLatitude(lng.latitude);
            memomries.setLongitude(lng.longitude);
            mapLocation(memomries, s);
            Toast.makeText(getApplicationContext(), s , Toast.LENGTH_LONG).show();


        }



        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)  {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                     lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
                    Location lastKnownLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    mapLocation(lastKnownLocation, "my Location");
                }
            }
        }
    }


    public void mapLocation(Location location, String s) {
        if (location != null) {
            mMap.clear();
            LatLng UpdatedLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(UpdatedLocation).title(s));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UpdatedLocation,10));


        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.clear();

        String address="";
        Geocoder geocoder= new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addressList= geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            if (addressList != null && addressList.size()>0) {
                Log.i("address",addressList.toString());

                if(addressList.get(0).getThoroughfare()!=null){
                    address+=addressList.get(0).getThoroughfare()+" ";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(address.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyyy-MM-dd");

            address += sdf.format(new Date());
        }

        mMap.addMarker(new MarkerOptions().position(latLng).title(address));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        MainActivity.places.add(address);
        MainActivity.locations.add(latLng);
        Log.i("latlang",latLng.toString());
        Toast.makeText(MapsActivity.this,address,Toast.LENGTH_SHORT).show();
        MainActivity.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences= this.getSharedPreferences("com.example.placememory",Context.MODE_PRIVATE);

        try {
            ArrayList<String> lattitudes=new ArrayList<>();
            ArrayList<String> longitudes=new ArrayList<>();

            for (LatLng cord:MainActivity.locations){
                lattitudes.add(Double.toString(cord.latitude));
                longitudes.add(Double.toString(cord.longitude));
            }
            sharedPreferences.edit().putString("places",ObjectSerializer.serialize(MainActivity.places)).apply();
            sharedPreferences.edit().putString("lat",ObjectSerializer.serialize(lattitudes)).apply();
            sharedPreferences.edit().putString("lon",ObjectSerializer.serialize(longitudes)).apply();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}






