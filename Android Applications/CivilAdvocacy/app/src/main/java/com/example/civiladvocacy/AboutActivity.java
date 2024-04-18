package com.example.civiladvocacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    String text = "Google Civic \n Information API";
    TextView google_civic_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        google_civic_link = findViewById(R.id.link);
        SpannableString s = new SpannableString(text);
        s.setSpan(new UnderlineSpan(), 0, s.length(), 0);
        google_civic_link.setText(s);
    }

    public void openInBrowser(View v){
        Intent intent;
        String link ="https://developers.google.com/civic-information/";
        try
        {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(link));
            startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
        }
    }
}