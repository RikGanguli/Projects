package com.example.civiladvocacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import Model.Candidates;

public class GetPhotosOfCandidatesActivity extends AppCompatActivity {

    Candidates candidate;
    TextView cand_address, cand_name, cand_position;
    ImageView social_logo;
    ImageView dp_image;
    ConstraintLayout cstl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);

        cand_address = findViewById(R.id.address_details);
        cand_name = findViewById(R.id.candidate_name);
        cand_position = findViewById(R.id.candidate_position);
        social_logo = findViewById(R.id.link_icon);
        dp_image = findViewById(R.id.display_pic);
        cstl = findViewById(R.id.clConstraintLayout);
        Bundle bundle = getIntent().getBundleExtra("List");
        candidate = (Candidates) bundle.getSerializable("List");
        assign_pol_data();
    }

    private void assign_pol_data() {
        cand_address.setText(candidate.getCurrent_address());
        cand_name.setText(candidate.getCandidate_name());
        cand_position.setText(candidate.getPosition());

        if (candidate.getPol_party().contains("Democratic"))
        {
            social_logo.setImageResource(R.drawable.dem_logo);
            cstl.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
        }
        else if (candidate.getPol_party().contains("Republican"))
        {
            social_logo.setImageResource(R.drawable.rep_logo);
            cstl.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        }
        else
        {
            cstl.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
            social_logo.setVisibility(ImageView.GONE);
        }

        if (!candidate.getDp_link().equals(""))
        {
            Picasso.get().load(candidate.getDp_link()).error(R.drawable.brokenimage)
                    .into(dp_image, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e)
                        {
                            dp_image.setTag("failed");
                        }
                    });
        }
        else
            dp_image.setImageResource(R.drawable.missing);
    }
}