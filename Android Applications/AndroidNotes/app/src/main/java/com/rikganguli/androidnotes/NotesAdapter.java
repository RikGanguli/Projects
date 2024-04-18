package com.rikganguli.androidnotes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    private final List<Notes> notesList;
    private final MainActivity mAct;

    public NotesAdapter(List<Notes> lst, MainActivity act) {
        this.notesList = lst;
        this.mAct = act;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_layout, parent, false);

        itemView.setOnClickListener(mAct);
        itemView.setOnLongClickListener(mAct);

        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Notes note = notesList.get(position);

        holder.title.setText(note.getTitle());

        String descr = note.getDescription().replaceAll("\n", " ");

        if (descr.length() > 80){
            char[] charArr = descr.toCharArray();
            String temp = "";
            for(int i = 0; i < 80; i++)
                temp += charArr[i];
            holder.description.setText(temp + "...");
        }else
            holder.description.setText(descr);
        holder.dateTime.setText(notesList.get(position).getDate_time());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
