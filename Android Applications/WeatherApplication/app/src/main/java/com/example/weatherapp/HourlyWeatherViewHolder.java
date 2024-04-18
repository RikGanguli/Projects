package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class HourlyWeatherViewHolder extends RecyclerView.ViewHolder {


    TextView day;
    TextView time;
    TextView temp;
    TextView descript;
    ImageView img;

    HourlyWeatherViewHolder(View view){
        super(view);
        day = view.findViewById(R.id.daily_view_day);
        time = view.findViewById(R.id.daily_view_time);
        temp = view.findViewById(R.id.daily_view_temp);
        descript = view.findViewById(R.id.daily_weather_info);
        img = view.findViewById(R.id.daily_weather_icon);
    }
}
