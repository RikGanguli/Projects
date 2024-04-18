package com.example.weatherapp;

public class HourlyWeatherData {

    private final String time, day, icon, temp, description;

    HourlyWeatherData(String time, String day, String icon, String temp, String description){
        this.time = time;
        this.day = day;
        this.icon = icon;
        this.temp = temp;
        this.description = description;
    }

    public String getTemp(){
        return this.temp;
    }

    public String getIcon(){
        return this.icon;
    }

    public String getDay(){
        return this.day;
    }
    public String getTime(){
        return this.time;
    }
    public String getDescription(){
        return this.description;
    }
}
