package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    boolean unit_in_fahrenheit = true;
    String sym = "°F";
    Forecast weather_data;
    private SharedPreferences data_stored;
    List<HourlyWeatherData> daily_weather_data = new ArrayList<>();
    private RecyclerView hourly_rc;
    String current_hour;
    String days_data;

    private static final String w_url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private static final String ic_url = "https://openweathermap.org/img/w/";

    private static String starting_city = "Chicago, Illinois";

    private static final String api_key ="PMSXZ3FNVRHMCSVK29NSTPKFP" ;

    private RequestQueue request;

    SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refresh = findViewById(R.id.refreshNew);
        refresh.setOnRefreshListener(()->{
            if(connectedToInternet())
            {
                setTitle(starting_city);
                retrieveWeatherData();
                refresh.setRefreshing(false);
            }
            else
            {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                refresh.setRefreshing(false);
            }
        });

        if(!connectedToInternet())
        {
            setTitle("Weather App");
            ((TextView)findViewById(R.id.home_date_time)).setText("No Internet Connection");
            for(int item = 0; item < ((ConstraintLayout)findViewById(R.id.main_activity)).getChildCount(); item++)
            {
                View child = ((ConstraintLayout)findViewById(R.id.main_activity)).getChildAt(item);
                if(child.getId()!=R.id.home_date_time)
                {
                    child.setVisibility(View.GONE);
                }
            }
        }
        else
        {
            request = Volley.newRequestQueue(this);
            hourly_rc = findViewById(R.id.recycler_hourly_weather);
            data_stored = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            unit_in_fahrenheit = data_stored.getBoolean("unitF", unit_in_fahrenheit);
            starting_city = data_stored.getString("city", starting_city);
            retrieveWeatherData();
            setTitle(starting_city);
        }
    }


    public void create_hourly_rv()
    {
        HourlyWeatherAdapter adapter = new HourlyWeatherAdapter(this, daily_weather_data);
        hourly_rc.setAdapter(adapter);
        hourly_rc.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));

    }
    private boolean connectedToInternet()
    {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
    public void unit_conversion(MenuItem item)
    {
            if(!unit_in_fahrenheit)
            {
                unit_in_fahrenheit = true;
                sym = "°F";
                item.setIcon(R.drawable.units_f);
            }
            else
            {
                unit_in_fahrenheit = false;
                item.setIcon(R.drawable.units_c);
            }

            data_stored.edit().putBoolean("unitF", unit_in_fahrenheit).apply();
            return;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem units_test = menu.findItem(R.id.units);

        if(!unit_in_fahrenheit)
        {
            units_test.setIcon(R.drawable.units_c);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.units)
        {
            unit_conversion(item);
            daily_weather_data.clear();
            retrieveWeatherData();
            return true;
        }

        if(item.getItemId() == R.id.calendar)
        {
            Intent intent = new Intent(this, DailyWeather.class);
            intent.putExtra("jsonData", days_data);
            intent.putExtra("unitF", unit_in_fahrenheit);
            startActivity(intent);
            return true;
        }
        if(item.getItemId() == R.id.location)
        {
            AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
            alertBox.setTitle("Enter a Location" );
            alertBox.setMessage("\nFor US locations, enter as 'City' or 'City,State' " + "\nFor International locations, enter as 'City,Country'");
            final EditText location = new EditText(MainActivity.this);
            alertBox.setView(location);
            alertBox.setPositiveButton("ok",(dialog,which)->{
                dialog.dismiss();
                this.starting_city = location.getText().toString();
                retrieveWeatherData();
                Log.w("Location", this.starting_city);
            }
            );

            alertBox.setNegativeButton("Cancel",(dialog,which)->{
                dialog.dismiss();
            }
            );

            AlertDialog alert = alertBox.create();
            alert.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void retrieveWeatherData()
    {
        Uri.Builder buildURL = Uri.parse(w_url).buildUpon();
        buildURL.appendPath(starting_city);
        if(unit_in_fahrenheit)
        {
            buildURL.appendQueryParameter("unitGroup", "us");
        }
        else
        {
            buildURL.appendQueryParameter("unitGroup", "metric");
        }

        buildURL.appendQueryParameter("key", api_key);

        String used_url = buildURL.build().toString();
        Log.w("Volley Request",used_url);

        Response.Listener<JSONObject> listener =
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            daily_weather_data.clear();
                            setTitle(starting_city);
                            data_stored.edit().putString("city", starting_city).apply();
                            Log.w("Volley Response", response.toString());
                            days_data = response.toString();
                            dataParser(response.toString());
                            parseDailyData(response.toString());

                        }
                        catch (Exception e)
                        {
                            Log.w("Volley Response", "catch of response");
                        }
                    }
                };

        Response.ErrorListener error = error1 -> {
            try
            {
                setTitle(data_stored.getString("city","UnKnown"));
                Log.w("Volley Response", error1.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, used_url, null, listener, error);

        request.add(jor);
    }

    private String determineWindDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "Y";
    }


    public void weather_data_display(){

        if(!unit_in_fahrenheit)
            sym = "°C";

        TextView dateTime = findViewById(R.id.home_date_time);
        dateTime.setText(weather_data.getDate());

        TextView temp = findViewById(R.id.home_temp);
        temp.setText(weather_data.getTemperature()+ sym);

        ((ImageView)findViewById(R.id.home_weather_icon)).setImageResource(getResources().getIdentifier(weather_data.getWeather_icon(),"drawable",this.getPackageName()));

        TextView desc = findViewById(R.id.home_weather_info);
        desc.setText(weather_data.getWeather_desc());

        TextView fltemp = findViewById(R.id.home_feels_like);
        fltemp.setText("Feels like "+ weather_data.getFeels_like_temp());


        TextView wdesc = findViewById(R.id.home_wind_info);
        wdesc.setText(weather_data.getWind_info());

        TextView hum = findViewById(R.id.home_humidity);
        hum.setText("Humidity: "+ weather_data.getHumidity());

        TextView vsb = findViewById(R.id.home_vis_info);
        vsb.setText("Visibility: "+ weather_data.getVisibility_info());

        TextView uv = findViewById(R.id.home_uv_info);
        uv.setText("UV Index: "+ weather_data.getUVIndex());
        TextView mtp = findViewById(R.id.home_morning_temp);
        mtp.setText(weather_data.getMorning_temp()+ sym);

        TextView atp = findViewById(R.id.home_afternoon_temp);
        atp.setText(weather_data.getAfternoon_temp()+ sym);

        TextView etp = findViewById(R.id.home_evening_temp);
        etp.setText(weather_data.getEvening_temp()+ sym);
        TextView ntp = findViewById(R.id.home_night_temp);
        ntp.setText(weather_data.getNight_temp()+ sym);

        TextView sr = findViewById(R.id.home_sunrise_time); sr.setText(weather_data.getSunrise_time());
        ((TextView)findViewById(R.id.home_sunset_time)).setText(weather_data.getSunset_time());
    }

    public void parseDailyData(String data){
        try
        {
            JSONObject json_obj = new JSONObject(data);

            JSONArray day = json_obj.getJSONArray("days");

            String temp;
            String date;
            String time;
            String desc;
            String icon;

            int hour = Integer.parseInt(current_hour.substring(0,2));

            for(int count = hour + 1 ; count < 24; count++){
                 date = "Today";
                 temp = day.getJSONObject(0).getJSONArray("hours").getJSONObject(count).getString("temp")+ sym;
                 time = String.valueOf(count<=12? count:count-12)+":00" + ( count < 12 ? "AM":"PM");
                 desc = day.getJSONObject(0).getJSONArray("hours").getJSONObject(count).getString("conditions");
                 icon =  day.getJSONObject(0).getJSONArray("hours").getJSONObject(count).getString("icon");
                 icon = icon.replace("-","_");
                 daily_weather_data.add(new HourlyWeatherData(date,time,temp,desc,icon));
                create_hourly_rv();

            }

            for(int count = 0 ; count < 3; count++){
                for(int item = 1; item < 25; item++){
                    long date_time_ep = day.getJSONObject(count).getJSONArray("hours").getJSONObject(item).getLong("datetimeEpoch");
                    SimpleDateFormat dayDate = new SimpleDateFormat("EEEE MM/dd", Locale.getDefault());
                    Date dateTime = new Date(date_time_ep * 1000);
                    date = dayDate.format(dateTime);

                    temp = day.getJSONObject(count).getJSONArray("hours").getJSONObject(item).getString("temp")+ sym;
                    time = (item <= 12 ? item : item - 12) + ":00" + ( item < 12 ? "AM":"PM");
                    desc = day.getJSONObject(count).getJSONArray("hours").getJSONObject(item).getString("conditions");
                    icon =  day.getJSONObject(count).getJSONArray("hours").getJSONObject(item).getString("icon");
                    icon = icon.replace("-","_");
                    daily_weather_data.add(new HourlyWeatherData(date,time,temp,desc,icon));
                    create_hourly_rv();
                }
            }

            int size = daily_weather_data.size();
            Log.w("dwdSize",String.valueOf(size));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }



    public void dataParser(String data){
        try {
            JSONObject json_obj = new JSONObject(data);

            String city = json_obj.getString("address");

            JSONObject currentCondition = json_obj.getJSONObject("currentConditions");

            JSONObject day = json_obj.getJSONArray("days").getJSONObject(0);
            JSONArray hours = day.getJSONArray("hours");

            String afternoon_temp = hours.getJSONObject(13).getString("temp");
            String temp = currentCondition.getString("temp");
            String evening_temp = hours.getJSONObject(17).getString("temp");;
            String night_temp = hours.getJSONObject(23).getString("temp");;
            String desc = currentCondition.getString("conditions") + " ("+currentCondition.getString("cloudcover")+")" ;
            String vis = currentCondition.getString("visibility")+" mi";
            String current_date_time = currentCondition.getString("datetimeEpoch");
            String icon = currentCondition.getString("icon");
            icon = icon.replace("-","_");
            String UVIndex = currentCondition.getString("uvindex");
            String humidity = currentCondition.getString("humidity")+"%";
            String morning_temp =  hours.getJSONObject(8).getString("temp");
            String feels_like_temp = currentCondition.getString("feelslike");

            String sunrise_time;
            String sunset_time;
            long sunriseepoch = currentCondition.getLong("sunriseEpoch");
            long sunsetepoch = currentCondition.getLong("sunsetEpoch");

            String wind_gust = currentCondition.getString("windgust");
            Double wind_direction = currentCondition.getDouble("winddir");
            String wind_speed = currentCondition.getString("windspeed");

            String wind_data = "Winds: "+ determineWindDirection(wind_direction) +" at "+wind_speed+" mpg gusting to "+wind_gust+" mph";

            long datetimeEpoch = currentCondition.getLong("datetimeEpoch");

            Date dateTime = new Date(datetimeEpoch * 1000); // Java time values need milliseconds
            SimpleDateFormat fullDate = new SimpleDateFormat("EEE MMM dd h:mm a, yyyy", Locale.getDefault());
            String fullDateStr = fullDate.format(dateTime); // Thu Sep 29 12:00 AM, 2022
            SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm ", Locale.getDefault());
            current_hour = timeOnly.format(dateTime);

            dateTime = new Date(sunriseepoch*1000);
            sunrise_time = timeOnly.format(dateTime);

            dateTime = new Date(sunsetepoch*1000);
            sunset_time = timeOnly.format(dateTime);

           weather_data = new Forecast(city,temp,fullDateStr,feels_like_temp,desc,wind_data,humidity,UVIndex,vis,morning_temp,afternoon_temp,evening_temp,night_temp,sunrise_time,sunset_time,icon);

           weather_data_display();

            Log.w("CurrentCondition", currentCondition.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

}



