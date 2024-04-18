package com.example.newsaggregator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsaggregator.model.News;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static RequestQueue q1;
    private static RequestQueue q2;
    private ListView news_list;
    private DrawerLayout dLayout;
    public News n;
    private String[] src;
    private ArrayAdapter<String> nw_adpt;
    private News[] nwSrc;
    private static List<News> news_art = new ArrayList<>();
    private static List<News> varNews = new ArrayList<>();
    private ActionBarDrawerToggle togDraw;
    Map<Integer, String> color_filter_map = new HashMap<>();
    ArrayList<News> newsArticles;
    private NewsAdapter news_Adapter;
    private String api_key ="af3cab2b7de04ff2a34682c0aaf9ef76";
    RecyclerView rv;
    private Menu genres;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void news_source_setter(List<News> newsSources)
    {
        news_art = new ArrayList<>(newsSources);
        src = new String[newsSources.size()];
        News[] news_data;
        news_data = new News[newsSources.size()];

        int index = 0;
        while(index < src.length)
        {
            src[index] = newsSources.get(index).getNews_name();
            index++;
        }

        this.setTitle("News Gateway " + "(" + src.length+")");

        nw_adpt = new ArrayAdapter<String>(this, R.layout.drawer_list, src)
        {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                View v1;
                v1 = super.getView(position, convertView, parent);
                ((TextView)v1.findViewById(android.R.id.text1)).setTextColor(n.getColor(newsSources.get(position).getNews_category()));
                return v1;
            }
        };

        news_list.setAdapter(nw_adpt);

        index = 0;

        while(index < newsSources.size())
        {
            news_data[index] = newsSources.get(index);
            index++;
        }

        this.nwSrc = news_data;
        updateMenu(news_data);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void start_assign()
    {

        color_filter_map.put(1, "all");
        news_list = findViewById(R.id.left_drawer);
        dLayout = findViewById(R.id.drawer_layout);
        rv = findViewById(R.id.newslist);

        news_data_getter();

        n = new News();
        n.setColor(this);
    }

    public void new_article_setter(ArrayList<News> newsArticles)
    {
        this.newsArticles = newsArticles;

        news_Adapter = new NewsAdapter(this);
        news_Adapter.setList_of_news(newsArticles);

        rv.setAdapter(news_Adapter);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        start_assign();

        news_list.setOnItemClickListener((parent, view, position, id) -> item_selecter(position));
        togDraw = new ActionBarDrawerToggle(this, dLayout, R.string.drawer_open, R.string.drawer_close);

        ActionBar success = getSupportActionBar();
        if (success != null)
        {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void item_selecter(int position)
    {
        this.setTitle(news_art.get(position).getNews_name());
        data_art_fetcher(news_art.get(position).getSl_no());

        findViewById(R.id.content_frame).setBackgroundColor(Color.parseColor("#ffffff"));
        dLayout.closeDrawer(news_list);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void data_updater(String item, int idNum)
    {
        if (idNum == 0)
        {
            return;
        }

        String previous;
        previous = color_filter_map.get(idNum);

        color_filter_map.put(idNum, item);

        News[] source_with_genre;

        source_with_genre = Arrays.stream(nwSrc)
                .filter(source -> (source.getNews_category().
                        equals(color_filter_map.get(1)) || color_filter_map.get(1).equals("all")))
                .toArray(News[]::new);

        news_art = new ArrayList<>();

        int index = 0;

        while(index < source_with_genre.length)
        {
            news_art.add(source_with_genre[index]);
            index++;
        }

        String []news_sources;
        news_sources= Arrays.stream(source_with_genre).map(News::getNews_name).toArray(String[]::new);

        int result = news_sources.length;

        if (result == 0)
        {
            new AlertDialog.Builder(this)
                    .setTitle("News source does not exist.")
                    .setMessage("News source with specified category does not exist.")
                    .setPositiveButton("OK", (dialog, which) -> color_filter_map.put(idNum, previous)).show();
        }
        else
        {
            src = news_sources;

            nw_adpt = new ArrayAdapter<String>(this, R.layout.drawer_list, src)
            {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
                {
                    View vw;
                    vw = super.getView(position, convertView, parent);

                    ((TextView)vw.findViewById(android.R.id.text1)).
                            setTextColor(n.getColor(source_with_genre[position].getNews_category()));
                    return vw;
                }
            };

            news_list.setAdapter(nw_adpt);

            nw_adpt.notifyDataSetChanged();

            setTitle("News Gateway (" + src.length + ")");
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        togDraw.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        togDraw.onConfigurationChanged(newConfig);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateMenu(News[] newsSources)
    {

        Arrays.stream(newsSources).map(News::getNews_category).distinct().forEach((topic) -> {
            SpannableString spanStr;
            spanStr = new SpannableString(topic);
            spanStr.setSpan(new ForegroundColorSpan(n.getColor(topic)), 0, spanStr.length(), 0);

            genres.add(1, 0, 0, spanStr);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        genres = menu;
        genres.add(1, 0, 0, "all");
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        if (togDraw.onOptionsItemSelected(item))
        {
            Log.d(TAG, "onOptionsItemSelected: DrawerToggle " + item);
            return true;
        }

        data_updater(item.getTitle().toString(), item.getGroupId());
        return super.onOptionsItemSelected(item);
    }

    private void showToast(String message)
    {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v)
    {
        int loc;
        loc = rv.getChildAdapterPosition(v);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(newsArticles.get(loc).getNews_link())));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void news_data_getter()
    {
        q1 = Volley.newRequestQueue(this);

        Uri.Builder bld;
        bld = Uri.parse("https://newsapi.org/v2/top-headlines/sources").buildUpon();
        bld.appendQueryParameter("apiKey", api_key);

        String link;
        link = bld.build().toString();

        Response.Listener<JSONObject> jObjL;
        jObjL= response -> JSON_data_parser(response.toString());

        Response.ErrorListener errorListener = error -> {
            news_source_setter(null);
        };
        JsonObjectRequest jObjR;
        jObjR= new JsonObjectRequest(Request.Method.GET, link, null,jObjL,errorListener) {

            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> hdrs = new HashMap<>();
                hdrs.put("User-Agent", "News-App");
                return hdrs;
            }

        };

        q1.add(jObjR);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void JSON_data_parser(String toString) {
        try
        {
            JSONArray json_sources;

            json_sources = new JSONObject(toString).getJSONArray("sources");

            int index = 0;

            while(index < json_sources.length())
            {
                Map<String, String> hMap = new HashMap<>();

                JSONObject News;
                News= (JSONObject) json_sources.get(index);
                varNews.add(new News(News.getString("id"),News.getString("name"),News.getString("category")));
                index++;
            }
            news_source_setter(varNews);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void data_art_fetcher(String source)
    {

        q2 = Volley.newRequestQueue(this);
        System.out.println("Sources:::"+source);

        Uri.Builder bld;
        bld = Uri.parse("https://newsapi.org/v2/top-headlines").buildUpon();

        bld.appendQueryParameter("sources", source);
        bld.appendQueryParameter("apiKey", api_key);

        String link;
        link = bld.build().toString();

        Response.Listener<JSONObject> jObjL;
        jObjL = response -> json_article_parser(response.toString());

        Response.ErrorListener errorListener = error -> {
            new_article_setter(null);
        };

        JsonObjectRequest jObjR;
        jObjR = new JsonObjectRequest(Request.Method.GET, link, null,jObjL,errorListener)
        {
            @Override
            public Map<String, String> getHeaders()
            {

                Map<String, String> hDrs = new HashMap<>();

                hDrs.put("User-Agent", "News-App");
                return hDrs;
            }
        };
        q2.add(jObjR);
    }

    private void json_article_parser(String toString)
    {
        newsArticles = new ArrayList<>();
        try
        {
            JSONArray nw_src;
            nw_src = new JSONObject(toString).getJSONArray("articles");

            int index = 0;

            while(index < nw_src.length())
            {

                JSONObject art;
                art = (JSONObject) nw_src.get(index);
                newsArticles.add(new News(art.getString("title"),art.getString("author"),art.getString("description"),
                        art.getString("publishedAt"),art.getString("urlToImage"),art.getString("url")));
                index++;

            }
            new_article_setter(newsArticles);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}