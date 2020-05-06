package com.example.fadeimageanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void fade(View view){
        ImageView imageView1=(ImageView)findViewById(R.id.imageView);
        ImageView imageView2=(ImageView)findViewById(R.id.imageView2);


        if( imageView2.getAlpha()==0){
            imageView1.animate().alpha(0).setDuration(2000);
            imageView2.animate().alpha(1).setDuration(2000);
        }
        else{
            imageView2.animate().alpha(0).setDuration(2000);
            imageView1.animate().alpha(1).setDuration(2000);
        }

    }


    public  void nextanim(View view){
        Intent i= new Intent(MainActivity.this,XyActivity.class);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
