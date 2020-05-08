package com.example.timestables;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SeekBar nseek;
    ListView mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mylist= findViewById(R.id.myListView);
        nseek= findViewById(R.id.seekBar);

        int max=20;
        int current=10;
        nseek.setMax(max);
        nseek.setProgress(current);
        calculate(current);

        nseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           int n;
           int min=1;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress<min){
                    n= min;
                }else{
                    n=progress;
                }

               // Toast.makeText(MainActivity.this,"n value is "+n,Toast.LENGTH_SHORT).show();
                calculate(n);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void calculate(int n) {
        ArrayList<Integer> xx=new ArrayList<>();
        for(int i=1;i<=10;i++){
            xx.add(n*i);
        }
        ArrayAdapter<Integer> myAdapter= new ArrayAdapter<Integer>(MainActivity.this, android.R.layout.simple_list_item_1,xx);
        mylist.setAdapter(myAdapter);

    }
}
