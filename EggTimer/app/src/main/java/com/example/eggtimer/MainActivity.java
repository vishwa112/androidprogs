package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar tseek;
    ImageView eggimg;
    TextView timetext;
    Button gobutton;
    boolean timerstart=true;
    CountDownTimer count;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tseek= findViewById(R.id.seekBar);
        eggimg= findViewById(R.id.imageView);
        timetext=findViewById(R.id.timetext);
        gobutton= findViewById(R.id.gobutton);
        int max=600;
        tseek.setMax(max);
       tseek.setProgress(30);
        tseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               timeremains(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void timeremains(int progress){
        int minute=progress/60;
        int second=progress-(minute*60);
        if(second<10){
            String sstr=String.valueOf(second);
            timetext.setText(minute + ":0" + sstr);
        }
        else{
            timetext.setText(minute + ":" + second);
        }
    }


    public void startTimer(View view){
        if(timerstart) {
            tseek.setEnabled(false);
            gobutton.setText("Stop");
            mp= MediaPlayer.create(MainActivity.this, R.raw.clockticking);
            final MediaPlayer mp1 = MediaPlayer.create(MainActivity.this, R.raw.applause);
            mp.start();
            eggimg.setImageResource(R.drawable.egg1);
           count= new CountDownTimer(tseek.getProgress() * 1000, 1000) {

                public void onTick(long millis) {
                    timeremains((int) (millis / 1000));
                    String second = String.valueOf(millis / 1000);
                }

                @Override
                public void onFinish() {

                    mp.stop();
                    mp1.start();
                    eggimg.setImageResource(R.drawable.egg2);
                    resetTimer();
                }
            }.start();
            timerstart=false;
        }
        else{

         resetTimer();
        }
    }

    public void resetTimer(){
        mp.stop();
        tseek.setEnabled(true);
        timetext.setText("0:30");
        tseek.setProgress(30);
        count.cancel();
        gobutton.setText("GO");
        timerstart=true;

    }



}
