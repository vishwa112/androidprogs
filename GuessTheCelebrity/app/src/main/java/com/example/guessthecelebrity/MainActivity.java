package com.example.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    String result=null;
    ArrayList<String>  celebnames= new ArrayList<String>();
    ArrayList<String>imglinks= new ArrayList<String>();
    int chosenceleb,LocationofChosenceleb,incorrectlocation;
    ImageView imageView;
    String[] answers=new String[4];
    Button b0,b1,b2,b3;


    public class Downloadstuff extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {

            String content ="";
            URL url ;
            HttpURLConnection connection = null;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data != -1){
                    char current = (char) data;
                    stringBuilder.append(current);
                    data = reader.read();
                }
                content = stringBuilder.toString();
                return content;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public class ImageDownload extends AsyncTask<String,Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
           try {
               URL url=new URL(urls[0]);
               HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
               urlConnection.connect();
               InputStream in= urlConnection.getInputStream();
               Bitmap bitmap= BitmapFactory.decodeStream(in);
               return bitmap;
           }catch (Exception e){
               e.printStackTrace();
               return null;
           }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b0=findViewById(R.id.button);
        b1=findViewById(R.id.button2);
        b2=findViewById(R.id.button3);
        b3=findViewById(R.id.button4);
        imageView= findViewById(R.id.imageView);

        Downloadstuff ds=new Downloadstuff();
        try {
            result=ds.execute("https://www.imdb.com/list/ls052283250/").get();
            Pattern p= Pattern.compile("<img alt=\\\"(.*?)\\\"");
            Matcher m=p.matcher(result);
            String name;
            while(m.find()){
                name=m.group(1);
                celebnames.add(name);
              Log.i("celebnames", m.group(1)) ;
            }

            p=Pattern.compile("src=\\\"(.*?).jpg\\\"");
            m=p.matcher(result);

            String links;
            while(m.find()){
                links=m.group(1);
                imglinks.add(links);
                Log.i("imglinks", m.group(1)) ;
            }
            imagechange();


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i("namelist",celebnames.toString() );
        Log.i("downloaded",result);

    }


    public void imagechange(){
        try{
            ImageDownload id= new ImageDownload();
            Random rand= new Random();
            chosenceleb=rand.nextInt(imglinks.size());
            Bitmap bitmap=id.execute(imglinks.get(chosenceleb)).get();
            imageView.setImageBitmap(bitmap);

            LocationofChosenceleb=rand.nextInt(4);
            incorrectlocation=0;
            for(int i=0;i<4;i++){
                if(i==LocationofChosenceleb){
                    answers[i]=celebnames.get(chosenceleb);
                }else{
                    incorrectlocation=rand.nextInt(imglinks.size());
                    while(incorrectlocation==chosenceleb){
                        incorrectlocation=rand.nextInt(imglinks.size());
                    }
                    answers[i]=celebnames.get(incorrectlocation);
                }
            }
Log.i("answers array",answers.toString());
            b0.setText(answers[0]);
            b1.setText(answers[1]);
            b2.setText(answers[2]);
            b3.setText(answers[3]);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void nextImage(View view){
        String message="";
        if(Integer.parseInt(view.getTag().toString())==LocationofChosenceleb){
            message="correct";
        }else{
            message="incorrect it's "+celebnames.get(chosenceleb);
        }

        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
        imagechange();
    }
}
