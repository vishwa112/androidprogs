package com.example.listviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    ListView myList= findViewById(R.id.myListView);
     final ArrayList<String> girls= new ArrayList<>();
     girls.add("Mahi");
     girls.add("Kavya");
     girls.add("Purvi");
     girls.add("dolly");

     ArrayAdapter<String> myadapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,girls);

     myList.setAdapter(myadapter);

     myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

             Toast.makeText(getApplicationContext(),girls.get(position).toString(),Toast.LENGTH_SHORT).show();
         }
     });
    }
}
