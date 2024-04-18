package com.example.newsaggregator.model;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.example.newsaggregator.MainActivity;
import com.example.newsaggregator.R;

import java.util.HashMap;
import java.util.Map;

public class News {
    String news_title, news_timeStamp, image_link_news;

    private Map<String, Integer> shades;

    String news_category, news_description, news_name;

    String sl_no, news_writer, news_link;


    public News(String news_title, String news_writer, String news_description, String news_timeStamp, String image_link_news, String news_link) {
        this.news_title = news_title;
        this.news_writer = news_writer;
        this.news_description = news_description;
        this.news_timeStamp = news_timeStamp;
        this.image_link_news = image_link_news;
        this.news_link = news_link;
    }

    public News(String sl_no, String news_name, String news_category) {
        this.sl_no = sl_no;
        this.news_name = news_name;
        this.news_category = news_category;
    }

    public News() {

    }

    public String getSl_no() {
        return sl_no;
    }

    public String getNews_title() {
        return news_title;
    }

    public String getNews_link() {
        return news_link;
    }

    public String getNews_timeStamp() {
        return news_timeStamp;
    }

    public String getNews_writer() {
        return news_writer;
    }

    public String getNews_name() {
        return news_name;
    }

    public String getNews_description() {
        return news_description;
    }
    
    public String getNews_category() {
        return news_category;
    }

    public String getImage_link_news() {
        return image_link_news;
    }

    public void setColor(MainActivity mainActivity) {
        shades = new HashMap<>();
        Resources r = mainActivity.getResources();

        shades.put("technology", r.getColor(R.color.technology));
        shades.put("health", r.getColor(R.color.health));
        shades.put("sports", r.getColor(R.color.sports));
        shades.put("general", r.getColor(R.color.general));
        shades.put("entertainment", r.getColor(R.color.entertainment));
        shades.put("science", r.getColor(R.color.science));
        shades.put("business", r.getColor(R.color.business));
    }

    public int getColor(String type) {
        return shades.get(type);
    }

    @NonNull
    @Override
    public String toString() {
        return "News{" +
                "news_title='" + news_title + '\'' +
                ", writer='" + news_writer + '\'' +
                ", news_description='" + news_description + '\'' +
                ", news_timestamp='" + news_timeStamp + '\'' +
                ", image_link='" + image_link_news + '\'' +
                '}';
    }
}
