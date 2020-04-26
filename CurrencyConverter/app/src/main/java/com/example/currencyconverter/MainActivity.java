package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void convertIt(View view){
        Log.i("convert", "button clicked to convert");
        ImageView i=(ImageView)findViewById(R.id.imageView);
        i.setImageResource(R.drawable.indianrupee);
        EditText useditText=(EditText)findViewById(R.id.useditText);
        String usd=useditText.getText().toString();
        Double us=Double.parseDouble(usd); //Double.valueOf(usd);
        Double value= us*76.32;
        Log.i("inr", value.toString());
        String inr= String.format("%.2f",value);//value.toString();
        EditText inreditText=(EditText)findViewById(R.id.inreditText);
        inreditText.setText(inr);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
