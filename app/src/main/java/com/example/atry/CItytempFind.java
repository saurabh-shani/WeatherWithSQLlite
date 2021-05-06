    package com.example.atry;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class CItytempFind extends AppCompatActivity {

    Button GetCityName, insertDB, ViewDB, deleteDB, UpdateDB;
    EditText CityName, UpdateEditText;
    TextView Name, temp, min_temp_value, latitude_value, longitude_value, max_temp_value, pressure_value, humidity_value;
    TextView visibilty_value, windspeed_value, sunrise_value, sunset_value, city_description;
    LottieAnimationView city_lottie;


    String apikey = "e3d3c54579fac7b2013e3669777599a5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_c_itytemp_find);

        GetCityName = findViewById(R.id.city_button);
        insertDB = findViewById(R.id.insert_database_button);
        ViewDB = findViewById(R.id.view_database_button);
        deleteDB = findViewById(R.id.Delete_database_button);
        UpdateDB = findViewById(R.id.Update_database_button);
        CityName = findViewById(R.id.city_edittext);
        UpdateEditText = findViewById(R.id.update_editText);
        Name = findViewById(R.id.City_Name);
        temp = findViewById(R.id.city_temprature);
        min_temp_value = findViewById(R.id.city_min_temp_value);
        max_temp_value = findViewById(R.id.city_max_temp_value);
        latitude_value = findViewById(R.id.city_latitude_value);
        longitude_value = findViewById(R.id.city_longitude_value);
        pressure_value = findViewById(R.id.city_presure_value);
        humidity_value = findViewById(R.id.city_humidity_value);
        visibilty_value = findViewById(R.id.city_visiblity_value);
        windspeed_value = findViewById(R.id.city_wind_speed_value);
        sunrise_value = findViewById(R.id.city_sunrise_value);
        sunset_value = findViewById(R.id.city_sunset_value);
        city_description = findViewById(R.id.city_Description);
        city_lottie = (LottieAnimationView) findViewById(R.id.city_lottie);

        DBhelper DB =  new DBhelper(this);
        SQLiteDatabase sqLiteDatabase = DB.getReadableDatabase();



        GetCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cityName = CityName.getText().toString();
                String url = "https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid=e3d3c54579fac7b2013e3669777599a5";
                Name.setText(cityName);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {

                        try {


                            JSONObject object =response.getJSONObject("main");
                            JSONArray array = response.getJSONArray("weather");
                            JSONObject jsonObject = array.getJSONObject(0);

                            String dis = jsonObject.getString("description");
                            city_description.setText(dis);

                            String id = jsonObject.getString("id");
                            int ids = Integer.parseInt(id);

                            if (ids >= 200 && ids <= 232)
                            {
                                city_lottie.setAnimation(R.raw.thunderstorm);

                            } else if (ids >= 300 && ids <= 321)
                            {
                                city_lottie.setAnimation(R.raw.drizzle);

                            } else if (ids >= 500 && ids <= 531)
                            {
                                city_lottie.setAnimation(R.raw.rainy);

                            } else if (ids >= 600 && ids <= 622)
                            {
                                city_lottie.setAnimation(R.raw.snow);

                            } else if (ids >= 701 && ids <= 781)
                            {
                                city_lottie.setAnimation(R.raw.atmosphere);

                            } else if (ids == 800)
                            {
                                city_lottie.setAnimation(R.raw.clearsky);

                            } else if (ids >= 801 && ids <= 804)
                            {
                                city_lottie.setAnimation(R.raw.clouds);
                            }



                            //To get Current Temperature
                            String tempt = object.getString("temp");
                            double temperature = Double.parseDouble(tempt) - 273.15;
                            temp.setText(Double.toString(temperature).substring(0,4));

                            String tempt_min = object.getString("temp_min");
                            double temp_min = Double.parseDouble(tempt_min) - 273.15;
                            min_temp_value.setText(Double.toString(temp_min).substring(0,4)+"°C");

                            String tempt_max = object.getString("temp_max");
                            double temp_max = Double.parseDouble(tempt_max) - 273.15;
                            max_temp_value.setText(Double.toString(temp_max).substring(0,4)+" °C");

                            String pressure = object.getString("pressure");
                            double pressu = Double.parseDouble(pressure);
                            pressure_value.setText(Double.toString(pressu)+" hPa");

                            String humid = object.getString("humidity");
                            double humidity = Double.parseDouble(humid);
                            humidity_value.setText(Double.toString(humidity)+" %");


                            JSONObject object1 =response.getJSONObject("coord");
                            String latitude = object1.getString("lat");
                            double latitude_v = Double.parseDouble(latitude);
                            latitude_value.setText(Double.toString(latitude_v));


                            String longitude = object1.getString("lon");
                            double longitude_v = Double.parseDouble(longitude);
                            longitude_value.setText(Double.toString(longitude_v));

                            JSONObject object2 =response.getJSONObject("wind");
                            String windsp = object2.getString("speed");
                            double windspeed = Double.parseDouble(windsp);
                            windspeed_value.setText(Double.toString(windspeed)+" m/s");

                            JSONObject jsonrespose = new JSONObject(String.valueOf(response));
                            String visibility = jsonrespose.getString("visibility") ;
                            double visi = Double.parseDouble(visibility) / 1000;
                            visibilty_value.setText(visi+" kms");

                            JSONObject object3 =response.getJSONObject("sys");
                            String sunup = object3.getString("sunrise");
                            long unix_secondss = Long.parseLong(sunup);
                            //Date date = new Date(unix_seconds*1000L);
                            SimpleDateFormat sdfdww = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                            String dateees = sdfdww.format(new java.util.Date (unix_secondss*1000));
                            //int sunup_v = (sunup);
                            sunrise_value.setText(dateees);


                            String sundw = object3.getString("sunset");
                            long unix_seconds = Long.parseLong(sundw);
                            //Date date = new Date(unix_seconds*1000L);
                            SimpleDateFormat sdfdw = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                            String dateee = sdfdw.format(new java.util.Date (unix_seconds*1000));
                            sunset_value.setText(dateee);



                        } catch (JSONException e){

                            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(CItytempFind.this, "Please try again"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(request);


            }

        });

        insertDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String CityName = Name.getText().toString();
                String WeatherType = city_description.getText().toString();
                String temprature =  temp.getText().toString();
                String MaxTemp = max_temp_value.getText().toString();
                String MinTemp = min_temp_value.getText().toString();
                String Latitude = latitude_value.getText().toString();
                String Longitude = longitude_value.getText().toString();
                String Humidity = humidity_value.getText().toString();
                String Pressure = pressure_value.getText().toString();
                String Sunrise = sunrise_value.getText().toString();
                String Sunset = sunset_value.getText().toString();
                String WindSpeed  = windspeed_value.getText().toString();
                String Visibility = visibilty_value.getText().toString();



                boolean i = DB.insert_data(CityName,WeatherType, temprature , MaxTemp, MinTemp, Latitude, Longitude, Humidity, Pressure, Sunrise, Sunset, WindSpeed, Visibility);

                if (i)
                {
                    Toast.makeText(CItytempFind.this, "Successfull", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CItytempFind.this, "Not Successfull", Toast.LENGTH_SHORT).show();
                }
            }
        });


            ViewDB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Cursor cursor = DB.getinfo();
                    if (cursor.getCount() == 0){

                        Toast.makeText(CItytempFind.this, "No data Found", Toast.LENGTH_SHORT).show();

                    }

                        StringBuilder buffer = new StringBuilder();
                        while (cursor.moveToNext())
                        {
                            buffer.append("City Name : ").append(cursor.getString(1)).append("\n");
                            buffer.append("Weather Type : ").append(cursor.getString(2)).append("\n");
                            buffer.append("Temprature : ").append(cursor.getString(3)).append("\n");
                            buffer.append("Max Temp : ").append(cursor.getString(4)).append("\n");
                            buffer.append("Min Temp : ").append(cursor.getString(5)).append("\n");
                            buffer.append("Latitude : ").append(cursor.getString(6)).append("\n");
                            buffer.append("Longitude : ").append(cursor.getString(7)).append("\n");
                            buffer.append("Humidity : ").append(cursor.getString(8)).append("\n");
                            buffer.append("Pressure : ").append(cursor.getString(9)).append("\n");
                            buffer.append("Sunrise : ").append(cursor.getString(10)).append("\n");
                            buffer.append("Sunset : ").append(cursor.getString(11)).append("\n");
                            buffer.append("Wind Speed : ").append(cursor.getString(12)).append("\n");
                            buffer.append("Visibility : ").append(cursor.getString(13)).append("\n\n\n");


                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(CItytempFind.this);
                        builder.setCancelable(true);
                        builder.setTitle("Weather Data Searched");
                        builder.setMessage(buffer.toString());
                        builder.show();

                    }

            });



            deleteDB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String cityname = CityName.getText().toString();
                    Toast.makeText(CItytempFind.this, ""+ cityname, Toast.LENGTH_SHORT).show();
                    Boolean i = DB.delete_data(cityname);
                    if (true)
                    {
                        Toast.makeText(CItytempFind.this, "Successful", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        Toast.makeText(CItytempFind.this, " Not Successful", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            UpdateDB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String cityname = CityName.getText().toString();
                    String update_weather = UpdateEditText.getText().toString();
                    boolean i = DB.update_data(cityname,update_weather);

                    if (i)
                    {
                        Toast.makeText(CItytempFind.this, "Updated", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        Toast.makeText(CItytempFind.this, "Not Updated", Toast.LENGTH_SHORT).show();
                    }

                }
            });
    }
}