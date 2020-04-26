package com.example.higherorlower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void checknumber(View view){
        EditText editText= (EditText)findViewById(R.id.editText);
        int check= Integer.parseInt(editText.getText().toString());
        int rand=(int)(Math.random()*20+1);
        Log.i("rand","random value"+rand);
        if(check>rand){
            Toast.makeText(this, "Higher", Toast.LENGTH_SHORT).show();
        }
        else if(check<rand){
            Toast.makeText(this, "Lower", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "You got it! try again", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
