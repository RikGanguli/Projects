package com.example.newsaggregator;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.newsaggregator.model.News;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>
{
    private final MainActivity m_act;

    public NewsAdapter(MainActivity m_act)
    {
        this.m_act = m_act;
    }

    private ArrayList<News> list_of_news;

    public void setList_of_news(ArrayList<News> list_of_news)
    {
        this.list_of_news = list_of_news;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v;

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news, parent, false);
        v.setOnClickListener(m_act);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int index)
    {
        News var_news;

        String date_var;

        var_news = list_of_news.get(index);

        holder.newsDesc.setText(var_news.getNews_description());
        holder.writer.setText(var_news.getNews_writer());
        holder.newsTitle.setText(var_news.getNews_title());

        date_var = getFormattedDateFromString(var_news.getNews_timeStamp());

        holder.timeStamp.setText(date_var);
        holder.currPg.setText((index + 1) + " of " + list_of_news.size());

        if (!var_news.getImage_link_news().equals("null"))
        {
            holder.im.setImageResource(R.drawable.loading);
            new ImageService(holder.im, m_act).execute(var_news.getImage_link_news());
        }
    }

    @Override
    public int getItemCount()
    {
        return list_of_news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView writer, timeStamp;
        ImageView im;
        TextView newsTitle, newsDesc;
        TextView currPg;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            writer = itemView.findViewById(R.id.author_of_news);
            timeStamp = itemView.findViewById(R.id.date_of_news);
            im = itemView.findViewById(R.id.image_of_news);
            newsTitle = itemView.findViewById(R.id.title_of_news);
            newsDesc = itemView.findViewById(R.id.description_of_news);
            currPg = itemView.findViewById(R.id.number_of_page);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ImageService extends AsyncTask<String, Void, Bitmap>
    {
        MainActivity main_act;
        ImageView imv;

        public ImageService(ImageView imv, MainActivity main_act)
        {
            this.imv = imv;
            this.main_act = main_act;
        }

        @Override
        protected Bitmap doInBackground(String... strings)
        {
            Bitmap mIcon11;

            try
            {
                InputStream inp;
                inp = new java.net.URL(strings[0]).openStream();
                mIcon11 = BitmapFactory.decodeStream(inp);

            }
            catch (Exception e)
            {

                Log.e("Fatal Mistake", e.getMessage());
                mIcon11 = null;
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result)
        {
            if (result == null)
                imv.setImageResource(main_act.getResources().getIdentifier("brokenimage", "drawable", main_act.getPackageName()));
            else
                imv.setImageBitmap(result);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getFormattedDateFromString(String publishedAt)
    {
        String get_date = "";
        try
        {
            LocalDateTime localDateTime;
            DateTimeFormatter tf;
            TemporalAccessor acc;
            DateTimeFormatter dtf;

            tf = DateTimeFormatter.ISO_INSTANT;

            dtf  = DateTimeFormatter.ofPattern("LLL dd, yyyy kk:mm");

            acc = tf.parse(publishedAt);

            localDateTime = LocalDateTime.ofInstant(Instant.from(acc), ZoneId.systemDefault());
            get_date = localDateTime.format(dtf);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(get_date.isEmpty())
        {
            try
            {
                LocalDateTime ldt;
                DateTimeFormatter tf;
                TemporalAccessor acc;
                DateTimeFormatter dtf;

                tf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
                acc = tf.parse(publishedAt);

                dtf  = DateTimeFormatter.ofPattern("LLL dd, yyyy kk:mm");
                ldt= LocalDateTime.ofInstant(Instant.from(acc), ZoneId.systemDefault());

                get_date = ldt.format(dtf);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return get_date;
    }
}
