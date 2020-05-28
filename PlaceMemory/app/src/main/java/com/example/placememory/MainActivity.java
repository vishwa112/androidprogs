package com.example.placememory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> places=new ArrayList<>();
    static ArrayList<LatLng> locations= new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences= this.getSharedPreferences("com.example.placememory", Context.MODE_PRIVATE);
        ArrayList<String> lat=new ArrayList<>();
        ArrayList<String> lon=new ArrayList<>();
        places.clear();
        lat.clear();
        lon.clear();
        locations.clear();
        try {
            places= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new ArrayList<String>())));
            lat=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lat",ObjectSerializer.serialize(new ArrayList<String>())));
            lon= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lon",ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(places.size()>0 && lat.size()>0 && lon.size()>0){
            if(places.size()==lat.size() && places.size()==lon.size()){
                for(int i=0;i<places.size();i++){
                    locations.add(new LatLng(Double.parseDouble(lat.get(i)), Double.parseDouble(lon.get(i))));

                }
            }
        }else{
            places.add("Add Your Memorable Place");
            locations.add(new LatLng(0,0));
        }




        ListView listView=(ListView)findViewById(R.id.listview);

        arrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,places);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i= new Intent(MainActivity.this,MapsActivity.class);
                i.putExtra("placenumber",position);
                startActivity(i);
            }
        });
        listView.setAdapter(arrayAdapter);
    }
}
