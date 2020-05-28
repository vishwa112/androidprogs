package com.example.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase mydb= this.openOrCreateDatabase("Users", MODE_PRIVATE,null);

        //new table with primary key

        mydb.execSQL("CREATE TABLE IF NOT EXISTS newuser (name VARCHAR, age INT(3),id INTEGER Primary key)");

    /*    mydb.execSQL("INSERT INTO newuser(name,age) VALUES ('Smit', 22)");
        mydb.execSQL("INSERT INTO newuser(name,age) VALUES ('Vish', 26)");
        mydb.execSQL("INSERT INTO newuser(name,age) VALUES ('Smit', 34)");
        mydb.execSQL("INSERT INTO newuser(name,age) VALUES ('purvi', 21)");
        mydb.execSQL("INSERT INTO newuser(name,age) VALUES ('jalpit', 23)");
        mydb.execSQL("INSERT INTO newuser(name,age) VALUES ('purvi', 1)");
        mydb.execSQL("INSERT INTO newuser(name,age) VALUES ('jalpit', 6)");
        mydb.execSQL("INSERT INTO newuser(name,age) VALUES ('mahi', 3)");
        mydb.execSQL("INSERT INTO newuser(name,age) VALUES ('joy', 9)");

     */

    mydb.execSQL("DELETE FROM newuser WHERE id=2");

        Cursor c= mydb.rawQuery("SELECT * FROM newuser",null);

        int namei=c.getColumnIndex("name");
        int agei=c.getColumnIndex("age");
        int id=c.getColumnIndex("id");

        c.moveToFirst();

        while (!c.isAfterLast()){
            Log.i("name",c.getString(namei));
            Log.i("age",c.getString(agei));
            Log.i("id",c.getString(id));
            c.moveToNext();
        }

        //user table

        /*
        mydb.execSQL("CREATE TABLE IF NOT EXISTS users(name VARCHAR, age INT(3))");
          mydb.execSQL("INSERT INTO users(name,age) VALUES ('Vish', 26)");
       mydb.execSQL("INSERT INTO users(name,age) VALUES ('Smit', 34)");
        mydb.execSQL("INSERT INTO users(name,age) VALUES ('purvi', 21)");
      mydb.execSQL("INSERT INTO users(name,age) VALUES ('jalpit', 23)");
        mydb.execSQL("INSERT INTO users(name,age) VALUES ('purvi', 1)");
        mydb.execSQL("INSERT INTO users(name,age) VALUES ('jalpit', 6)");
        mydb.execSQL("INSERT INTO users(name,age) VALUES ('mahi', 3)");
        mydb.execSQL("INSERT INTO users(name,age) VALUES ('joy', 9)");

    //mydb.execSQL("DELETE FROM users WHERE name='Smit'");
      //  Cursor c= mydb.rawQuery("SELECT * FROM users WHERE name LIKE 'j%' AND age<10 ",null);
        Cursor c= mydb.rawQuery("SELECT * FROM users  ",null);

        int nameIndex= c.getColumnIndex("name");
        int ageIndex= c.getColumnIndex("age");

        c.moveToFirst();
        while(!c.isAfterLast() ){
            Log.i("name",c.getString(nameIndex));
            Log.i("age", c.getString(ageIndex));
            c.moveToNext();
        }
        */
    }
}
