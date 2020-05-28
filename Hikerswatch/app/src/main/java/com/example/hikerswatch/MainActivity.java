package com.example.hikerswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    LocationManager lm;
    LocationListener ll;
    TextView latlngtextview,acctextview,alttextview,adtextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity.this);


        lm= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        latlngtextview=findViewById(R.id.lltextview);
        acctextview=findViewById(R.id.acctextView);
        alttextview=findViewById(R.id.alttextView);
        adtextview=findViewById(R.id.adtextView);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;

        ll=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng current= new LatLng(location.getLatitude(),location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(current).title("Marker"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current,10));
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


                Geocoder geocoder= new Geocoder(getApplicationContext(), Locale.getDefault());
                String address="Couldn't find Address";
                try {
                    List<Address> addressList=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                  if(addressList.size()>0) {
                      address = "";
                      if (addressList.get(0).getThoroughfare() != null) {
                          address += addressList.get(0).getThoroughfare()+" ";
                      }
                      if (addressList.get(0).getAdminArea() != null) {
                          address += addressList.get(0).getAdminArea()+" ";
                      }
                      if (addressList.get(0).getCountryName() != null) {
                          address += addressList.get(0).getCountryName();
                      }
                  }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String latlan= "lattitude: "+Double.toString(location.getLatitude())+"\nlongitude: "+Double.toString(location.getLongitude());

                adtextview.setText("Address:\n "+address);
                latlngtextview.setText(latlan);
                acctextview.setText("Accuracy: "+Double.toString(location.getAccuracy()));
                alttextview.setText("Altitude: "+(location.getAltitude()));

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


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else{
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,ll);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1){
            if(grantResults!=null && grantResults.length>=0){
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,ll);
                }
            }
        }
    }
}
