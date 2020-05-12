package com.example.cityweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    Button enter;
    EditText cityname;
    TextView citywethertext;
    public class DownloadInfo extends AsyncTask<String ,Void,String> {

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

                    String wmain=jsonObject1.getString("main");
                    String wdescription=jsonObject1.getString("description");

                    if(!wmain.equals("")&&!wdescription.equals("")){
                        citywethertext.setText(wmain+"\r\n"+wdescription);
                    }
                   // Log.i("main",jsonObject1.getString("main"));
                    //Log.i("description",jsonObject1.getString("description"));
                    InputMethodManager mgr= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(citywethertext.getWindowToken(),0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,"could not find weather",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityname= findViewById(R.id.editText);
        citywethertext=findViewById(R.id.textView2);
        enter= findViewById(R.id.button);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    DownloadInfo di= new DownloadInfo();
                    String encodedcity= URLEncoder.encode(cityname.getText().toString(),"UTF-8");
                    di.execute("https://openweathermap.org/data/2.5/weather?q="+encodedcity+"&appid=439d4b804bc8187953eb36d2a8c26a02").get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"could not find weather",Toast.LENGTH_SHORT).show();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"could not find weather",Toast.LENGTH_SHORT).show();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"could not find weather",Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


}
