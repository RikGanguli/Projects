package com.example.weatherapp;

public class WeatherRecyclerData {

    private  final String temp, date_time, desc, perc_precip;
    private final String uvi, weather_icon, temp_morning;
    private final String temp_afternoon, temp_evening, temp_night;


    WeatherRecyclerData(String temp, String date_time, String desc, String perc_precip,
                        String uvi, String weather_icon, String temp_morning,
                        String temp_afternoon, String temp_evening, String temp_night){

        this.date_time = date_time;
        this.uvi = uvi;
        this.temp_night = temp_night;
        this.temp_afternoon = temp_afternoon;
        this.temp_morning = temp_morning;
        this.weather_icon = weather_icon;
        this.temp = temp;
        this.temp_evening = temp_evening;
        this.perc_precip = perc_precip;
        this.desc = desc;
    }


    String getTemp(){
        return temp;
    }

    String getDate_time(){
        return date_time;
    }
    String getTemp_morning(){
        return temp_morning;
    }
    String getPerc_precip(){
        return perc_precip;
    }
    String getUvi(){
        return uvi;
    }
    String getWeather_icon(){
        return weather_icon;
    }
    String getTemp_afternoon(){
        return temp_afternoon;
    }

    String getTemp_evening(){
        return temp_evening;
    }
    String getTemp_night(){
        return temp_night;
    }
    String getDesc(){
        return desc;
    }
}
