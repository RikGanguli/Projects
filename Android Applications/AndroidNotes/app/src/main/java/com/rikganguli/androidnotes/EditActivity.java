package com.rikganguli.androidnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    private ArrayList<Notes> notesArrayList;

    private EditText title_field_content;

    private EditText description_field_content;

    private String title;

    private String description;

    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        title_field_content = findViewById(R.id.title_field);

        description_field_content = findViewById(R.id.description_field);

        pos = -1;

        Bundle bund1 = getIntent().getBundleExtra("List_notes");

        notesArrayList = (ArrayList<Notes>) bund1.getSerializable("List_notes");
        Bundle bund2 = getIntent().getExtras();
        title = bund2.getString("Title");
        if(title != null) {
            description = bund2.getString("Description");
            pos = bund2.getInt("Position");
            title_field_content.setText(title);
            description_field_content.setText(description);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void save_notes() {
        try {
            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fileOutputStream);
            pw.print(notesArrayList);
            pw.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private boolean add_notes() {
        String title_temp = title_field_content.getText().toString();
        String desc = description_field_content.getText().toString();
        if(!title_temp.isEmpty()) {
            String date_time = new SimpleDateFormat("E MMM dd, hh:mm aa ").format(new Date());
            Notes note = new Notes(title_temp, desc, date_time);
            if(pos >= 0) {
                if (!title.equals(title_temp) || !description.equals(desc))
                    notesArrayList.remove(pos);
                else
                    return true;
            }
            notesArrayList.add(note);
            save_notes();
            return true;
        }
        return false;
    }

    public void EmptyWarning() {
        Intent intent = new Intent(this, MainActivity.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Please enter a title");
        builder.setMessage("Note cannot be saved without a title.\n"+"Continue?\n");
        builder.setPositiveButton("Yes", (dialog, which) -> startActivity(intent));
        builder.setNegativeButton("No", null);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_button) {
            Intent intent = new Intent(this, MainActivity.class);
            if(!add_notes()) {
                Toast.makeText(this,"Please give an appropriate title to the note",Toast.LENGTH_SHORT).show();
                EmptyWarning();
                return true;
            }
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        String title_temp = title_field_content.getText().toString();
        String desc = description_field_content.getText().toString();
        if(pos > -1 && title.equals(title_temp) && description.equals(desc)) {
            add_notes();
            startActivity(intent);
        }else if(title_temp.isEmpty() && desc.isEmpty())
            startActivity(intent);
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setTitle("Save Note '" + title_temp + "'?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                if(title_temp.isEmpty())
                    Toast.makeText(this,"Please enter a title",Toast.LENGTH_SHORT).show();
                else {
                    add_notes();
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> startActivity(intent)
            );
            builder.show();
        }
    }
}