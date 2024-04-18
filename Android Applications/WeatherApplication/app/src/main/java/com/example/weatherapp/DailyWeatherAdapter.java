package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherViewHolder>{


    private final DailyWeather main_activity;
    private final List<WeatherRecyclerData> weather_data;

    public DailyWeatherAdapter(DailyWeather mact, List<WeatherRecyclerData> data){
        this.main_activity = mact;
        this.weather_data = data;
    }


    @NonNull
    @Override
    public DailyWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_forecast_recycler,parent,false);

        return new DailyWeatherViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DailyWeatherViewHolder holder, int position) {

        WeatherRecyclerData data = weather_data.get(position);

        holder.descrip.setText(data.getDesc());
        holder.date_time.setText(data.getDate_time());
        holder.temp.setText(data.getTemp());
        holder.uvin.setText(data.getUvi());
        holder.precip_per.setText(data.getPerc_precip());
        holder.morning.setText(data.getTemp_morning());
        holder.afternoon.setText(data.getTemp_afternoon());
        holder.evening.setText(data.getTemp_evening());
        holder.night.setText(data.getTemp_night());
        holder.img.setImageResource(main_activity.getResources().getIdentifier(data.getWeather_icon(),"drawable", main_activity.getPackageName()));





    }

    @Override
    public int getItemCount() {
        return weather_data.size();
    }
}
