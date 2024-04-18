package com.example.weatherapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherViewHolder> {

       private final MainActivity main_act;
       private final List<HourlyWeatherData> daily_weather;


       public HourlyWeatherAdapter(MainActivity mact, List<HourlyWeatherData> hwdata ){
              this.main_act = mact;
              this.daily_weather = hwdata;

       }

       @NonNull
       @Override
       public HourlyWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

              View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_view,parent, false);

              return new HourlyWeatherViewHolder(view);
       }

       @Override
       public void onBindViewHolder(@NonNull HourlyWeatherViewHolder holder, int position) {

              HourlyWeatherData sd = daily_weather.get(position);

              holder.time.setText(sd.getTime());
              holder.temp.setText(sd.getTemp());
              holder.day.setText(sd.getDay());
              holder.descript.setText(sd.getDescription());
              holder.img.setImageResource(main_act.getResources().getIdentifier(sd.getIcon(),"drawable", main_act.getPackageName()));
       }

       @Override
       public int getItemCount() {
              return daily_weather.size();
       }
}
