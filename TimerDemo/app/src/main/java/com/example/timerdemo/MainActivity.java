package com.example.timerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this,1000);
                Log.i("timer","A second passed by");
            }
        };
        handler.post(run);*/

      new CountDownTimer(10000,1000){


          @Override
          public void onTick(long millisUntilFinished) {
              Log.i("onTick",String.valueOf( millisUntilFinished/1000));
          }

          @Override
          public void onFinish() {
            Log.i("onFinish","we're done");
          }
      }.start();

    }
}
