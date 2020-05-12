package com.example.downloadimagedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public class DownloadImg extends AsyncTask<String,Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url= new URL(urls[0]);
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream is= urlConnection.getInputStream();
                Bitmap bitimg= BitmapFactory.decodeStream(is);
                return bitimg;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DownloadImg di= new DownloadImg();
        Button downloadbutton= findViewById(R.id.button);
        final ImageView imageView=findViewById(R.id.imageView);

        downloadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap bitimg1= di.execute("https://upload.wikimedia.org/wikipedia/en/f/f6/Tom_Tom_and_Jerry.png").get();
                    imageView.setImageBitmap(bitimg1);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
