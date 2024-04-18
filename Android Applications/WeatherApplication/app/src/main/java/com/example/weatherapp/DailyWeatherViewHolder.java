package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class DailyWeatherViewHolder extends RecyclerView.ViewHolder {


    TextView date_time;
    TextView temp;
    TextView descrip;
    TextView precip_per;
    TextView uvin;
    TextView morning;
    TextView afternoon;
    TextView evening;
    TextView night;
    ImageView img;

    DailyWeatherViewHolder(View view){
        super(view);

        date_time = view.findViewById(R.id.recycler_date_time);
        temp = view.findViewById(R.id.recycler_temp);
        descrip = view.findViewById(R.id.recycler_weather_info);
        img = view.findViewById(R.id.recycler_weather_icon);
        precip_per = view.findViewById(R.id.recycler_precip_info);
        uvin = view.findViewById(R.id.recycler_uv);
        morning = view.findViewById(R.id.recycler_morning_temp);

        afternoon = view.findViewById(R.id.recycler_afternoon_temp);
        evening = view.findViewById(R.id.recycler_evening_temp);
        night = view.findViewById(R.id.recycler_night_temp);

    }

}
