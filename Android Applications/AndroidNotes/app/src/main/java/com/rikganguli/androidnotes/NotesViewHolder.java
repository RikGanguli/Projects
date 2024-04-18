package com.rikganguli.androidnotes;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class NotesViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView description;
    TextView dateTime;

    NotesViewHolder(View view) {
        super(view);
        title = view.findViewById(R.id.titleView);
        description = view.findViewById(R.id.descriptionView);
        dateTime = view.findViewById(R.id.dateTimeView);
    }

}
