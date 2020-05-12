package com.example.jsondata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class DownloadInfo extends AsyncTask<String ,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result="";
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char c = (char) data;
                    result += c;
                    data = reader.read();
                }
                return result;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("Json",s);

            try {
                JSONObject jsonObject= new JSONObject(s);
                String weather= jsonObject.getString("weather");
                Log.i("weather",weather);

                JSONArray data= new JSONArray(weather);
                for(int i=0;i<data.length();i++){
                    JSONObject jsonObject1=data.getJSONObject(i);
                    Log.i("main",jsonObject1.getString("main"));
                    Log.i("description",jsonObject1.getString("description"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadInfo di= new DownloadInfo();
        di.execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=439d4b804bc8187953eb36d2a8c26a02");
      //  https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=439d4b804bc8187953eb36d2a8c26a02
    }
}
