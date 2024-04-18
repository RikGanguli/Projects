package com.rikganguli.androidnotes;

import android.util.JsonWriter;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

public class Notes implements Serializable {
    private String title;
    private String description;
    private String date_time;

    public Notes(String title, String desc, String time_stamp){
        this.title = title;
        this.description = desc;
        this.date_time = time_stamp;
    }

    public String getTitle() {return title;}
    public String getDescription() {return description;}

    public String getDate_time() {return date_time;}

    @Override
    public String toString(){
        try {
            StringWriter sw = new StringWriter();
            JsonWriter jw = new JsonWriter(sw);
            jw.setIndent("  ");
            jw.beginObject();
            jw.name("Title").value(getTitle());
            jw.name("Description").value(getDescription());
            jw.name("Date_Time").value(getDate_time());
            jw.endObject();
            jw.close();
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}


