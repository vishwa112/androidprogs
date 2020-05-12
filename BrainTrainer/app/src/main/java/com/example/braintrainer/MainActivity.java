package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button b1,b2,b3,b4;
    Button play;
    TextView timerText,equationText,attemptText,resulttext;
    CountDownTimer cdt;
    ArrayList<Integer> answers= new ArrayList<Integer>();
    int LocationofAnswer;
    Random rand;
    int score,attempts,id;
    ConstraintLayout gameconstraint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameconstraint=findViewById(R.id.gameConstraints);
        b1=findViewById(R.id.button1);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        b4=findViewById(R.id.button4);
        timerText=findViewById(R.id.timertextview);
        equationText= findViewById(R.id.equationtextview);
        attemptText=findViewById(R.id.attempttextview);
        resulttext=findViewById(R.id.resulttextview);

        gameconstraint.setVisibility(View.INVISIBLE);


        play= findViewById(R.id.playButton);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setVisibility(View.INVISIBLE);
                gameconstraint.setVisibility(View.VISIBLE);

                score=0;
                attempts=0;
                b1.setClickable(true);
                b2.setClickable(true);
                b3.setClickable(true);
                b4.setClickable(true);

                cdt= new CountDownTimer(30000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                         String second= String.valueOf(millisUntilFinished/1000);
                         timerText.setText(second+"s");
                    }

                    @Override
                    public void onFinish() {
                        b1.setClickable(false);
                        b2.setClickable(false);
                        b3.setClickable(false);
                        b4.setClickable(false);
                        attemptText.setText("0/0");
                        equationText.setText("x+y");
                        resulttext.setText("Time's up");
                        play.setText("Play Again!");
                        play.setVisibility(View.VISIBLE);

                    }
                }.start();
                startPlaying();
            }
        });


    }

    public void startPlaying(){
        rand= new Random();
        int x=rand.nextInt(21);
        int y= rand.nextInt(21);

        equationText.setText(x+"+"+y);

        LocationofAnswer=rand.nextInt(4);
        int correctans= x+y;
        answers.clear();
        for(int i=0;i<4;i++){

            if(i==LocationofAnswer){
                answers.add(correctans);
            }
            else {
                int wrongans = rand.nextInt(41);
                while (wrongans == correctans) {
                    wrongans = rand.nextInt(41);
                }
                answers.add(wrongans);
            }
        }

        Log.i("answers",answers.toString());
        b1.setText(Integer.toString(answers.get(0)));
        b2.setText(Integer.toString(answers.get(1)));
        b3.setText(Integer.toString(answers.get(2)));
        b4.setText(Integer.toString(answers.get(3)));
    }


    public void chosenAns(View view){
        if(view.getTag().toString().equals(Integer.toString(LocationofAnswer))){
            Log.i("correct","you are right");

            resulttext.setText("Correct");
            score++;

        }else{
            Log.i("wrong","oops");
            resulttext.setText("Incorrect");
        }
        id=view.getId();
        attempts++;
        attemptText.setText(score+"/"+attempts);

        startPlaying();

    }
}
