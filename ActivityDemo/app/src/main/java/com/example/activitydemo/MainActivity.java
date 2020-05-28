package com.example.activitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview= (ListView) findViewById(R.id.listview);

        final ArrayList<String> city= new ArrayList<>();
        city.add("San Francisco");
        city.add("san diego");
        city.add("Las Vegas");
        city.add("columbus");
        city.add("troy");

        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,city);

        listview.setAdapter(arrayAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i= new Intent(MainActivity.this,CityActivity.class);
                i.putExtra("City",city.get(position));
                startActivity(i);

            }
        });

    }
}
