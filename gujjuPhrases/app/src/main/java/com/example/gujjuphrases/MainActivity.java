package com.example.gujjuphrases;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playPhrase(View view){
        Button b= (Button) view;
        Log.i("btag",b.getTag().toString());

        MediaPlayer mPlayer = MediaPlayer.create(this, getResources().getIdentifier(b.getTag().toString(), "raw", getPackageName()));
        mPlayer.start();
    }
}
