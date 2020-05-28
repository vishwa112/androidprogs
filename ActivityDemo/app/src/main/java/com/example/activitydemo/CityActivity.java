package com.example.activitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        TextView textView= findViewById(R.id.citynametextView);

        Intent intent = getIntent();
        String cityname= intent.getStringExtra("City");
        Log.i("cityname",cityname);
        textView.setText(cityname);
    }
}
