package com.example.downloadwebcontent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    public class downloadweb extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {

            URL url;
            HttpsURLConnection httpsurl=null;
            String result = "";

            try {
                url=new URL(urls[0]);
                httpsurl= (HttpsURLConnection) url.openConnection();
                InputStream is= httpsurl.getInputStream();
                InputStreamReader reader= new InputStreamReader(is);
                int data=reader.read();

                while (data!=-1){
                    char c= (char) data;
                    result+=c;
                   data= reader.read();

                }
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "urlexception";
            } catch (IOException e) {
                e.printStackTrace();
                return "ioexception";
            }


        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadweb dw= new downloadweb();
        String r=null;
        try {
            r=dw.execute("https://www.zappycode.com").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("r",r);
    }
}
