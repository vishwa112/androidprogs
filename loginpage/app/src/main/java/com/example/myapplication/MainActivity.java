package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public void userLogin(View view){
        EditText ueditText;
        ueditText = (EditText)findViewById(R.id.ueditText);
        EditText peditText2=(EditText)findViewById(R.id.peditText2);
        Log.i("button","button clicked");
        Log.i("log in", ueditText.getText().toString());
        Log.i("password", peditText2.getText().toString());
        Toast.makeText(this, "hi "+ueditText.getText().toString(), Toast.LENGTH_SHORT).show();
        Activity aa=new Activity();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
