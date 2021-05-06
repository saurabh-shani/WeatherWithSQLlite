package com.example.atry;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    private static final String dbname = "WeatherBySearch.db";

    public DBhelper(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "create table search (Sno integer primary key autoincrement, CityName text, WeatherType text, temperature text, MaxTemp text, MinTemp text, Latitude text, Longitude text, Humidity text, Pressure text, Sunrise text, Sunset text, WindSpeed text, Visibility text)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists search");
        onCreate(db);
    }

    public boolean insert_data(String CityName, String WeatherType, String temperature, String MaxTemp, String MinTemp, String Latitude, String Longitude, String Humidity, String Pressure, String Sunrise, String Sunset, String WindSpeed, String Visibility)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("CityName",CityName);
        c.put("WeatherType",WeatherType);
        c.put("temperature",temperature);
        c.put("MaxTemp",MaxTemp);
        c.put("MinTemp",MinTemp);
        c.put("Latitude",Latitude);
        c.put("Longitude",Longitude);
        c.put("Humidity",Humidity);
        c.put("Pressure",Pressure);
        c.put("Sunrise",Sunrise);
        c.put("Sunset",Sunset);
        c.put("WindSpeed",WindSpeed);
        c.put("Visibility",Visibility);
        long r = db.insert("search",null,c);

        return r != -1;

    }



    public boolean update_data(String cityname, String weathertype)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("WeatherType",weathertype);
        Cursor cursor = db.rawQuery("select * from search where CityName=?", new String[]{cityname});
        if (cursor.getCount() > 0)
        {
            long r = db.update("search",c,"CityName=?", new String[]{cityname});
            return r != -1;
        }
        else {
            return false;
        }
    }




    public  boolean delete_data(String cityname)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from search where CityName=?", new String[]{cityname});

        if (cursor.getCount() > 0)
        {
            long r = db.delete("search","CityName=?", new String[]{cityname});
            return r != -1;

        } else
        {

            return false;
        }

    }



    public Cursor getinfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from search",null);
        return cursor;
    }
}
