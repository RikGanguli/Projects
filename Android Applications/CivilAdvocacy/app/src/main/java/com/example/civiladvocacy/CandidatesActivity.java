package com.example.civiladvocacy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Candidates;

public class CandidatesActivity extends AppCompatActivity {

    Candidates candidate;
    TextView candidate_email_info, candidate_website_info, candidate_phone_no, candidate_position, candidate_mail, candidate_address_info;
    TextView current_address_line, address_text, candidate_party_info, phone_text, candidate_name, website_text;
    ImageView candidate_dp_pic, socials_logo, yt_link, fb_link, twt_link;
    ConstraintLayout constraintLayout;
    String FaceBook ="", Twitter = "", YouTube = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        Bundle bundle = getIntent().getBundleExtra("List");

        candidate = (Candidates) bundle.getSerializable("List");
        address_text = findViewById(R.id.address_details);
        candidate_position = findViewById(R.id.candidate_position);
        candidate_party_info = findViewById(R.id.party_details);
        website_text = findViewById(R.id.website_title);
        current_address_line = findViewById(R.id.address_title);
        phone_text = findViewById(R.id.phone_title);
        candidate_name = findViewById(R.id.candidate_name);
        candidate_mail = findViewById(R.id.email_title);
        candidate_address_info = findViewById(R.id.candidate_address);
        candidate_address_info = findViewById(R.id.candidate_address);
        candidate_email_info = findViewById(R.id.candidate_email);
        candidate_website_info = findViewById(R.id.candidate_website);
        candidate_phone_no = findViewById(R.id.candidate_phone_number);
        candidate_phone_no = findViewById(R.id.candidate_phone_number);
        candidate_email_info = findViewById(R.id.candidate_email);
        candidate_website_info = findViewById(R.id.candidate_website);
        yt_link = findViewById(R.id.yt_icon);
        fb_link = findViewById(R.id.fb_icon);
        twt_link = findViewById(R.id.twitter_icon);
        socials_logo = findViewById(R.id.link_icon);
        candidate_dp_pic = findViewById(R.id.display_pic);
        constraintLayout = findViewById(R.id.clConstraintLayout);
        assign_cand_data();
    }

    private void assign_cand_data(){
        address_text.setText(candidate.getCurrent_address());
        candidate_name.setText(candidate.getCandidate_name());
        candidate_position.setText(candidate.getPosition());

        if(!candidate.getPol_party().equals(""))
        {
            candidate_party_info.setText("("+ candidate.getPol_party()+")");
            if(candidate.getPol_party().contains("Democratic"))
            {
                socials_logo.setImageResource(R.drawable.dem_logo);
                constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
            }
            else if(candidate.getPol_party().contains("Republican"))
            {
                socials_logo.setImageResource(R.drawable.rep_logo);
                constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
            }
            else
            {
                constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
                socials_logo.setVisibility(ImageView.GONE);
            }
        }


        if(!candidate.getCandidate_address().equals(""))
        {
            SpannableString spanStr = new SpannableString(candidate.getCandidate_address());
            spanStr.setSpan(new UnderlineSpan(), 0, spanStr.length(), 0);
            candidate_address_info.setText(spanStr);
        }
        else
        {
            current_address_line.setVisibility(TextView.GONE);
            candidate_address_info.setVisibility(TextView.GONE);
        }

        if(!candidate.getPh_num().equals(""))
        {
            SpannableString spanStr = new SpannableString(candidate.getPh_num());
            spanStr.setSpan(new UnderlineSpan(), 0, spanStr.length(), 0);
            candidate_phone_no.setText(spanStr);
        }
        else
        {
            phone_text.setVisibility(TextView.GONE);
            candidate_phone_no.setVisibility(TextView.GONE);
        }

        if(!candidate.getEmail().equals(""))
        {
            SpannableString spanStr = new SpannableString(candidate.getEmail());
            spanStr.setSpan(new UnderlineSpan(), 0, spanStr.length(), 0);
            candidate_email_info.setText(spanStr);
        }
        else
        {
            candidate_mail.setVisibility(TextView.GONE);
            candidate_email_info.setVisibility(TextView.GONE);
        }

        if(!candidate.getHyperlinks().equals("")) {
            SpannableString spanStr = new SpannableString(candidate.getHyperlinks());
            spanStr.setSpan(new UnderlineSpan(), 0, spanStr.length(), 0);
            candidate_website_info.setText(spanStr);
        }
        else
        {
            website_text.setVisibility(TextView.GONE);
            candidate_website_info.setVisibility(TextView.GONE);
        }

        if(!candidate.getDp_link().equals(""))
        {
            Picasso.get()
                    .load(candidate.getDp_link())
                    .error(R.drawable.brokenimage)
                    .into(candidate_dp_pic, new Callback() {
                        @Override
                        public void onSuccess() {
                        }
                        @Override
                        public void onError(Exception e) {
                            candidate_dp_pic.setTag("fail");
                        }
                    });
        }
        else
            candidate_dp_pic.setImageResource(R.drawable.missing);

        List<String[]> list = candidate.getCandidate_lst();
        for (String[] s : list) {
            if(s[0].equals("Twitter"))
            {
                twt_link.setImageResource(R.drawable.twitter);
                Twitter = s[1];
            }
            else if(s[0].equals("Facebook"))
            {
                fb_link.setImageResource(R.drawable.facebook);
                FaceBook = s[1];
            }
            else if(s[0].equals("YouTube"))
            {
                yt_link.setImageResource(R.drawable.youtube);
                YouTube = s[1];
            }
        }
    }

    public void on_youtube_icon_clicked(View v) {
        Intent intent;
        String n = YouTube;
        try
        {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + n));
            startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + n)));
        }
    }

    public void on_location_clicked(View v) {
        String add = candidate.getCandidate_address();
        Uri u_map = Uri.parse("geo:0,0?q=" + Uri.encode(add));
        Intent intent = new Intent(Intent.ACTION_VIEW, u_map);

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    public void twitter_icon_clicked(View v) {
        String n = Twitter;
        Intent intent;
        try
        {
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + n));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        catch (Exception e)
        {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + n));
        }
        startActivity(intent);
    }

    public void on_icon_clicked(View v) {
        String str = "";
        Intent intent;
        if(candidate.getPol_party().contains("Democratic"))
            str = "https://democrats.org/";
        else
            str = "https://www.gop.com/";

        try
        {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(str));
            startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(str)));
        }
    }

    @SuppressLint("ResourceType")
    public void details_of_dp(View v) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, GetPhotosOfCandidatesActivity.class);

        if (!candidate.getDp_link().equals(""))
        {
            intent.putExtra("List", bundle);
            bundle.putSerializable("List", candidate);
            startActivity(intent);
        }
    }

    public void call_number_clicked(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        String number = candidate_phone_no.getText().toString();
        intent.setData(Uri.parse("tel:" + number));
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    public void fb_button_clicked(View v)
    {
        Intent intent;

        String fb_link = "https://www.facebook.com/" + FaceBook;

        if (check_install_pack("com.facebook.katana"))
        {
            String lnk = "fb://facewebmodal/f?href=" + fb_link;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(lnk));
        }
        else
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(fb_link));

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
        else
            show_error_message("No such app found");
    }

    public void email_button_clicked(View v) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        String[] address_list;
        address_list = new String[]{candidate_email_info.getText().toString()};
        intent.putExtra(Intent.EXTRA_EMAIL, address_list);

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    public boolean check_install_pack(String packageName) {
        try
        {
            return getPackageManager().getApplicationInfo(packageName, 0).enabled;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }

    public void website_link_clicked(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(candidate_website_info.getText().toString()));
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    private void show_error_message(String msg) {

        AlertDialog.Builder bld = new AlertDialog.Builder(this);

        bld.setMessage(msg);
        bld.setTitle("There is no such app");

        AlertDialog dialog = bld.create();
        dialog.show();
    }
}