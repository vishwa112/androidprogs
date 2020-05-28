package com.example.sharedpreferencedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences= this.getSharedPreferences("com.example.sharedpreferencedemo", Context.MODE_PRIVATE);

        ArrayList<String> names= new ArrayList<>();
        names.add("vish");
        names.add("purvi");
        names.add("jalpit");
        names.add("smit");
       for(String s:names){
           try {
               sharedPreferences.edit().putString("names",ObjectSerializer.serialize(names)).apply();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

    ArrayList<String> newnames= new ArrayList<>();

           try {
               newnames=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("names",ObjectSerializer.serialize(new ArrayList<String>())));
           } catch (IOException e) {
               e.printStackTrace();
           }

        Log.i("newnames",names.toString());
    }
}