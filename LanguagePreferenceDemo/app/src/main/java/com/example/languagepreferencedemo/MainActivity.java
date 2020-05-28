package com.example.languagepreferencedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView textView;
    public void setLanguage(String s){
      sharedPreferences.edit().putString("Language",s).apply();
        textView.setText(s);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView= findViewById(R.id.hellotext);
        sharedPreferences= this.getSharedPreferences("com.example.languagepreferencedemo", Context.MODE_PRIVATE);

        String lang= sharedPreferences.getString("Language","error");

        if(lang.equals("error")){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_btn_speak_now)
                    .setTitle("Select Language")
                    .setMessage("Which language do you prefere?")
                    .setPositiveButton("English", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setLanguage("English");
                        }
                    })
                    .setNegativeButton("Gujarati", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setLanguage("Gujarati");
                        }
                    })
                    .show();
        }else{
            textView.setText(lang);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);

         switch (item.getItemId()){
             case R.id.english:
                 setLanguage("English");
                 Toast.makeText(getApplicationContext(),"prefered language is ENGLISH", Toast.LENGTH_SHORT).show();
                 return true;
             case R.id.gujarati:
                 Toast.makeText(getApplicationContext(),"prefered language is GUJARATI", Toast.LENGTH_SHORT).show();
                 setLanguage("Gujarati");
                 return true;
             default:
                 return false;
         }
    }
}
