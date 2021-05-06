package com.example.atry;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class weatherData {
    private String mTemperature,micon,mcity,mWeatherType,mMax_temp,mMin_temp, mPressure, mHumidity, mVisibility, mWindSpeed, mSunrise,mSunset;
    private int mCondition;

    public static weatherData fromJson(JSONObject jsonObject)
    {

        try
        {
            weatherData weatherD=new weatherData();
            weatherD.mcity=jsonObject.getString("name");
            weatherD.mCondition=jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherD.mWeatherType=jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.micon=updateWeatherIcon(weatherD.mCondition);
            double tempResult=jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int roundedValue=(int)Math.rint(tempResult);
            weatherD.mTemperature=Integer.toString(roundedValue);

            double max_temp=jsonObject.getJSONObject("main").getDouble("temp_max")-273.15;
            int rundedValue=(int)Math.rint(max_temp);
            weatherD.mMax_temp=Integer.toString(rundedValue);


            double min_temp=jsonObject.getJSONObject("main").getDouble("temp_max")-273.15;
            int rundedValue1=(int)Math.rint(min_temp);
            weatherD.mMin_temp=Integer.toString(rundedValue1);

            weatherD.mPressure = String.valueOf(jsonObject.getJSONObject("main").getDouble("pressure"));

            weatherD.mHumidity = String.valueOf(jsonObject.getJSONObject("main").getDouble("humidity"));


            double vi = Double.parseDouble(jsonObject.getString("visibility")) / 1000;
            weatherD.mVisibility = String.valueOf(vi);

            weatherD.mWindSpeed = String.valueOf(jsonObject.getJSONObject("wind").getDouble("speed"));

            String sunup = jsonObject.getJSONObject("sys").getString("sunrise");
            long unix_secondss = Long.parseLong(sunup);
            SimpleDateFormat sdfdww = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
            weatherD.mSunrise = sdfdww.format(new java.util.Date (unix_secondss*1000));


            String sundn = jsonObject.getJSONObject("sys").getString("sunset");
            long unix_sec = Long.parseLong(sundn);
            SimpleDateFormat sfdww = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
            weatherD.mSunset = sfdww.format(new java.util.Date (unix_sec*1000));


            return weatherD;


        }


        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }


    private static String updateWeatherIcon(int condition)
    {


        if (condition >= 200 && condition <= 232)
        {
            return "thunderstrom";

        } else if (condition >= 300 && condition <= 321)
        {
            return "drizzle";

        } else if (condition >= 500 && condition <= 531)
        {
            return "rainy";

        } else if (condition >= 600 && condition <= 622)
        {
            return "snow";

        } else if (condition >= 701 && condition <= 781)
        {
            return "atmosphere";

        } else if (condition == 800)
        {
            return "clearsky";

        } else if (condition >= 801 && condition <= 804)
        {
            return "clouds";
        }


        return "dunno";


    }

    public String getmTemperature() {
        return mTemperature+"°C";
    }

    public String getMicon() {
        return micon;
    }

    public String getMcity() {
        return mcity;
    }

    public String getmWeatherType() {
        return mWeatherType;
    }

    public String getmMax_temp() {
        return mMax_temp+"°C";
    }

    public String getmMin_temp() { return mMin_temp+"°C"; }

    public String getmPressure() { return mPressure+"hPa"; }

    public String getmHumidity() { return mHumidity+"%"; }

    public String getmVisibility() { return mVisibility; }

    public String getmWindSpeed() { return mWindSpeed; }

    public String getmSunrise() { return mSunrise; }

    public String getmSunset() { return mSunset; }



}

