package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//Set the weather for 15 days
public class DailyWeather extends AppCompatActivity {

    boolean unit_in_fahrenheit = true;
    String resp;
    List<WeatherRecyclerData> weather_data = new ArrayList<>();
    String unit = "°F";

    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_forecast);

        Intent intent = getIntent();

        unit_in_fahrenheit = intent.getBooleanExtra("unitF",true);

        if(!unit_in_fahrenheit)
            unit = "°C";
        rv = findViewById(R.id.recycler_daily_weather);

        parseResponseData();
    }

    public void setRCView(){
        DailyWeatherAdapter adapter = new DailyWeatherAdapter(this, weather_data);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    void parseResponseData(){

        Intent i = getIntent();

        if(i.hasExtra("jsonData")){
            resp = i.getStringExtra("jsonData");
            Log.w("DaysWeather", resp);
        }
        else
            return;

        try {
            JSONObject jObjMain = new JSONObject(resp);

            JSONArray day = jObjMain.getJSONArray("days");

            String date_time;
            String temp;
            String descp;
            String precip;
            String uvindex;
            String icon;
            String morning;
            String afternoon;
            String evening;
            String night;

            for(int item = 0; item < 15; item++){

                long epoch = day.getJSONObject(item).getLong("datetimeEpoch");

                Date date = new Date(epoch * 1000);
                SimpleDateFormat dayDate = new SimpleDateFormat("EEEE MM/dd", Locale.getDefault());


                date_time = dayDate.format(date);

                temp = (int) Math.ceil((Double.parseDouble(day.getJSONObject(item).getString("tempmax")))) + unit +"/"+ (int) Math.floor(Double.parseDouble(day.getJSONObject(item).getString("tempmin")))+ unit;
                descp = day.getJSONObject(item).getString("description");
                precip = "("+day.getJSONObject(item).getString("precipprob")+"% precip.)";
                uvindex = "UV Index: "+ day.getJSONObject(item).getString("uvindex");
                icon = day.getJSONObject(item).getString("icon");
                icon = icon.replace("-","_");
                morning = day.getJSONObject(item).getJSONArray("hours").getJSONObject(8).getString("temp") + unit;
                afternoon = day.getJSONObject(item).getJSONArray("hours").getJSONObject(13).getString("temp")+ unit;
                evening = day.getJSONObject(item).getJSONArray("hours").getJSONObject(17).getString("temp")+ unit;
                night = day.getJSONObject(item).getJSONArray("hours").getJSONObject(day.getJSONObject(item).getJSONArray("hours").length()-1).getString("temp")+ unit;
                weather_data.add(new WeatherRecyclerData(temp,date_time,descp,precip,uvindex,icon,morning,afternoon,evening,night));
                setRCView();
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}