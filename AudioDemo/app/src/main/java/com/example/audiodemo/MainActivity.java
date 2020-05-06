package com.example.audiodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    AudioManager am;
    boolean manual=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp= MediaPlayer.create(this,R.raw.cantinaband);
        am=(AudioManager)getSystemService(AUDIO_SERVICE);
        int maxd=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentvolume=am.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar sb= (SeekBar)findViewById(R.id.seekBar);
        final SeekBar scrub=(SeekBar)findViewById(R.id.scrubseekBar);

        sb.setMax(maxd);
        sb.setProgress(currentvolume);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("seekbar","seekbar changed"+progress);
                am.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        scrub.setMax(mp.getDuration());
        scrub.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(manual==true){mp.seekTo(progress);}
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                manual=true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                 manual=false;
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                manual=true;
               scrub.setProgress( mp.getCurrentPosition());
            }
        },0,1000);
    }



    public void onPlay(View view){
        mp.start();
    }

    public void onPause(View view){
        mp.pause();
    }
}
