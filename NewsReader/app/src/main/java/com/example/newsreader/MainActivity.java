package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> titles= new ArrayList<>();
    ArrayList<String> contents= new ArrayList<>();
    ArrayAdapter arrayAdapter;
    SQLiteDatabase mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb=this.openOrCreateDatabase("Article",MODE_PRIVATE,null);
        mydb.execSQL("CREATE TABLE IF NOT EXISTS article ( id INTEGER PRIMARY KEY,articleid INTEGER,title VARCHAR,content VARCHAR) ");


        DownloadTask downloadTask= new DownloadTask();
        try{
            downloadTask.execute(" https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
        }catch (Exception e){
            e.printStackTrace();
        }

        ListView listView= findViewById(R.id.listview);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,titles);
        listView.setAdapter(arrayAdapter);
        updateListview();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getApplicationContext(),ArticleActivity.class);
                intent.putExtra("content",contents.get(position));
                startActivity(intent);
            }
        });
    }

    public void updateListview(){
        Cursor c= mydb.rawQuery("SELECT * FROM article",null);
        int titleindex= c.getColumnIndex("title");
        int contentindex= c.getColumnIndex("content");

       if( c.moveToFirst()){
           titles.clear();
           contents.clear();
        do{
            titles.add(c.getString(titleindex));
            contents.add(c.getString(contentindex));
        }while(c.moveToNext());
           arrayAdapter.notifyDataSetChanged();
       }

    }


    public class DownloadTask extends AsyncTask<String,Void, String>{

        @Override
        protected String doInBackground(String... strings) {

            String result="";
            URL url;
            HttpURLConnection URLConnection;
            try {
                url=new URL(strings[0]);
                URLConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream= URLConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(inputStream);
                int data=reader.read();

                while(data!=-1){
                    char c=(char) data;
                    result+=c;
                    data=reader.read();
                }

                JSONArray jsonArray= new JSONArray(result);
                int numberofItems=20;

                if(jsonArray.length()<20){
                  numberofItems=  jsonArray.length();
                }
                Log.i("result",result);

                mydb.execSQL("DELETE FROM article");
                for(int i=0; i<numberofItems;i++) {
                    String articleid = jsonArray.getString(i);
                    try {
                        url = new URL("https://hacker-news.firebaseio.com/v0/item/"+articleid+".json?print=pretty");
                        URLConnection = (HttpURLConnection) url.openConnection();
                        inputStream = URLConnection.getInputStream();
                        reader = new InputStreamReader(inputStream);
                        data = reader.read();
                        String articleInfo = "";
                        while (data != -1) {
                            char c = (char) data;
                            articleInfo += c;
                            data = reader.read();
                        }
                     //   Log.i("articleInfo",articleInfo);

                        JSONObject jsonObject= new JSONObject(articleInfo);
                        if(!jsonObject.isNull("title") && !jsonObject.isNull("url")) {
                            String articletitle =jsonObject.getString("title");
                            String url1=jsonObject.getString("url");
                      //      Log.i("title+url",articletitle+url1);
                         /*   url= new URL(url1);
                            URLConnection = (HttpURLConnection) url.openConnection();
                            inputStream = URLConnection.getInputStream();
                            reader = new InputStreamReader(inputStream);
                            data = reader.read();
                            String htmldata= "";
                            while (data != -1) {
                                char c = (char) data;
                                htmldata += c;
                                data = reader.read();
                            }
                            Log.i("html",htmldata);

                          */
                            String sql="INSERT INTO article(articleid,title,content) VALUES(?,?,?)" ;
                            SQLiteStatement statement=mydb.compileStatement(sql) ;
                            statement.bindString(1,result);
                            statement.bindString(2,articletitle);
                            statement.bindString(3,url1);
                            statement.execute();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
             //   return result;

            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateListview();
        }
    }
}
