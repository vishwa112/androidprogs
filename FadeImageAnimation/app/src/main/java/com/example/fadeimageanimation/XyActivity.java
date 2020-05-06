package com.example.fadeimageanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

public class XyActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xy);

        Intent intent = getIntent();
        Button left = (Button) findViewById(R.id.left);
        left.setOnClickListener(this);
        Button right= (Button)findViewById(R.id.right);
        right.setOnClickListener(this);
        Button up= (Button)findViewById(R.id.up);
        up.setOnClickListener(this);
        Button down= (Button)findViewById(R.id.down);
        down.setOnClickListener(this);
        Button rotate= (Button)findViewById(R.id.rotate);
        rotate.setOnClickListener(this);
        Button shrink= (Button) findViewById(R.id.shrink);
        shrink.setOnClickListener(this);
        Button next= (Button) findViewById(R.id.next1);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);

        switch(v.getId()) {
            case (R.id.left):
                imageView3.animate().translationXBy(-200).setDuration(2000);
                Log.i("left","left button clicked");
                break;
            case (R.id.right):
                imageView3.animate().translationXBy(200).setDuration(2000);
                Log.i("right","right button clicked");
                break;
            case (R.id.up):
               imageView3.animate().translationYBy(-200).setDuration(2000);
                Log.i("up","up button clicked");
                break;
            case (R.id.down):
                imageView3.animate().translationYBy(200).setDuration(2000);
                Log.i("down","down button clicked");
                break;
            case (R.id.rotate):
                imageView3.animate().rotationX(360).setDuration(2000);
                Log.i("rotate","rotate button clicked");
                break;
            case (R.id.shrink):
                imageView3.animate().scaleX(0.5f).setDuration(2000);
                Log.i("shrink","shrink button clicked");
                break;
            case(R.id.next1):
                Intent i=new Intent(this,BasicAnimation.class);
                startActivity(i);
        }
    }



}
 /*
        Button left= (Button)findViewById(R.id.left);


        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView3=(ImageView)findViewById(R.id.imageView3);
                imageView3.animate().translationX(200).setDuration(2000);
                moving=false;
            }
        });

        Button right= (Button)findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView3=(ImageView)findViewById(R.id.imageView3);
                imageView3.animate().translationXBy(200).setDuration(2000);
                moving=false;
            }
        });

        Button up= (Button)findViewById(R.id.up);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView3=(ImageView)findViewById(R.id.imageView3);
                imageView3.animate().translationYBy(200).setDuration(2000);
                moving=false;
            }
        });
        Button down= (Button)findViewById(R.id.down);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView3=(ImageView)findViewById(R.id.imageView3);
                imageView3.animate().translationY(200).setDuration(2000);

            }
        });
    }*/
