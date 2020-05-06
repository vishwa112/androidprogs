package com.example.fadeimageanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class BasicAnimation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_animation);
        Intent intent1= getIntent();
        ImageView finalImage= (ImageView)findViewById(R.id.finalimage);
        finalImage.setX(-1000);
        finalImage.animate().translationXBy(1000).rotationX(1800).setDuration(2000);
    }
}
