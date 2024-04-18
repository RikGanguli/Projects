package com.example.weatherapp;

public class Forecast {
    private final String city_name, temperature, date, feels_like_temp, weather_icon;
    private final String weather_desc, wind_info, humidity, UVIndex;
    private final String visibility_info, morning_temp, afternoon_temp, evening_temp;
    private final String night_temp, sunrise_time, sunset_time;

    Forecast(String afternoon_temp, String feels_like_temp, String city_name, String weather_icon,
             String date, String humidity, String wind_info, String weather_desc, String evening_temp, String temperature,
             String sunset_time, String visibility_info, String night_temp, String sunrise_time,
             String morning_temp, String UVIndex){


        this.afternoon_temp = feels_like_temp;
        this.feels_like_temp = feels_like_temp;
        this.city_name = city_name;
        this.weather_icon = weather_icon;
        this.date = date;
        this.humidity = humidity;
        this.wind_info = wind_info;
        this.weather_desc = weather_desc;
        this.evening_temp = evening_temp;
        this.temperature = temperature;
        this.sunset_time = sunset_time;
        this.visibility_info = visibility_info;
        this.night_temp = night_temp;
        this.sunrise_time = sunrise_time;
        this.morning_temp = morning_temp;
        this.UVIndex = UVIndex;
    }

    public String getTemperature(){
        return temperature;
    }

    public String getWeather_icon() {
        return weather_icon;
    }

    public String getDate(){
        return date;
    }
    public String getWeather_desc(){
        return weather_desc;
    }

    public String getCity_name(){
        return city_name;
    }
    public String getFeels_like_temp(){
        return feels_like_temp;
    }
    public String getSunrise_time(){
        return sunrise_time;
    }
    public String getUVIndex(){
        return UVIndex;
    }
    public String getWind_info(){
        return wind_info;
    }
    public String getHumidity(){
        return humidity;
    }

    public String getVisibility_info(){
        return visibility_info;
    }

    public String getMorning_temp(){
        return morning_temp;
    }

    public String getAfternoon_temp(){
        return afternoon_temp;
    }

    public String getEvening_temp(){
        return evening_temp;
    }
    public String getNight_temp(){
        return night_temp;
    }

    public String getSunset_time(){
        return sunset_time;
    }
}
