package com.rikganguli.androidnotes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements View.OnClickListener, View.OnLongClickListener{

    private final List<Notes> notes_list = new ArrayList<>();
    private RecyclerView myRecyclerView;

    private NotesAdapter nAdapter;

    //private ActivityResultLauncher<Intent> actresLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRecyclerView = findViewById(R.id.recycler);

        nAdapter = new NotesAdapter(notes_list, this);

        myRecyclerView.setAdapter(nAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        llm.setReverseLayout(true);
        myRecyclerView.setLayoutManager(llm);
    }

    //
    // From OnClickListener
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks
        int pos = myRecyclerView.getChildLayoutPosition(v);
        Notes n = notes_list.get(pos);
        Intent intent = new Intent(this,EditActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("Title", n.getTitle());
        intent.putExtra("Description", n.getDescription());
        bundle.putSerializable("List_notes", (Serializable) notes_list);
        intent.putExtra("List_notes", bundle);
        intent.putExtra("Position", pos);
        startActivity(intent);
    }

    private void save_notes() {
        try {
            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fileOutputStream);
            pw.print(notes_list);
            pw.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    //
    // From OnLongClickListener
    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
        int pos = myRecyclerView.getChildLayoutPosition(v);
        //Notes n = notesList.get(pos);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Note '" + notes_list.get(pos).getTitle() + "' ?");
        builder.setPositiveButton("Yes", (diagIn, i) -> {
            notes_list.remove(pos);
            save_notes();
            setTitle("Android Notes (" + notes_list.size() + ")");
            nAdapter.notifyDataSetChanged();
        });
        builder.setNegativeButton("No",null);
        builder.show();
        return true;
    }

    public ArrayList<Notes> load()
    {
        ArrayList<Notes> nlist = new ArrayList<>();
        try {
            InputStream inputStream = getApplicationContext().openFileInput(getString(R.string.file_name));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuffer sb = new StringBuffer();
            String line;
            while((line = bufferedReader.readLine())!= null) {
                sb.append(line);
            }
            JSONArray jsonArray = new JSONArray(sb.toString());
            for(int  i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("Title");
                String description = jsonObject.getString("Description");
                String dateTime = jsonObject.getString("Date_Time");
                Notes notes = new Notes(title, description, dateTime);
                nlist.add(notes);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return nlist;
    }

    protected void onStart(){
        notes_list.clear();
        notes_list.addAll(load());
        setTitle("Android Notes (" + notes_list.size()+ ")");
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        finish();
        finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case(R.id.about_menu):
                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
                break;

            case(R.id.add_menu):
                Intent intent2 = new Intent(this, EditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("List_notes", (Serializable) notes_list);
                intent2.putExtra("List_notes", bundle);
                startActivity(intent2);
                break;

            default:
                Toast.makeText(this, "Unknown item selected", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}