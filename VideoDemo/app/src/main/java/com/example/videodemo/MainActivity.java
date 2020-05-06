package com.example.videodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoView vv=(VideoView)findViewById(R.id.videoView);
        vv.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.sample);
        MediaController mediaController=new MediaController(this);
        vv.setMediaController(mediaController);
        vv.start();
    }
}
