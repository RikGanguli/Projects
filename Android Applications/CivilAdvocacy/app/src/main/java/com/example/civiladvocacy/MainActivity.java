package com.example.civiladvocacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Model.Candidates;
import adapter.candidate_data_adapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    List<Candidates> officialsList;
    private SharedPreferences prefs;
    TextView address;
    RecyclerView data;
    FusedLocationProviderClient fusedLocationProviderClient;
    ConstraintLayout no_internet, child;
    candidate_data_adapter GovernmentListAdapter;
    private final static int REQUEST_CODE = 100;
    String loc = "Chicago";
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = findViewById(R.id.candidate_recycler_data);
        address = findViewById(R.id.address_details);
        no_internet = findViewById(R.id.no_internet);
        child = findViewById(R.id.clChild);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (internet_connected())
        {
            child.setVisibility(View.VISIBLE);
            no_internet.setVisibility(View.GONE);
            loc_data_fetcher();
        }
        else
        {
            child.setVisibility(View.GONE);
            no_internet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        int posi = data.getChildAdapterPosition(v);

        Candidates cands = officialsList.get(posi);

        Bundle bundle = new Bundle();

        Intent intent = new Intent(this, CandidatesActivity.class);

        bundle.putSerializable("List", (Serializable) cands);

        intent.putExtra("List", bundle);

        startActivity(intent);
    }




    private void display_dialog() {
        if (internet_connected())
        {
            android.app.AlertDialog.Builder bld = new android.app.AlertDialog.Builder(this);

            final EditText txt;

            txt = new EditText(this);

            txt.setGravity(Gravity.CENTER_HORIZONTAL);

            bld.setView(txt);

            bld.setTitle("Enter Address");

            bld.setPositiveButton("OK", (dialog, id) -> {
                if (internet_connected())
                {
                    child.setVisibility(View.VISIBLE);
                    no_internet.setVisibility(View.GONE);
                    location(txt.getText().toString());
                } else
                {
                    child.setVisibility(View.GONE);
                    no_internet.setVisibility(View.VISIBLE);
                }
            });

            bld.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());

            AlertDialog dialog = bld.create();
            dialog.show();

        }
        else
            Toast.makeText(getApplicationContext(), "This requires devices to be connected to the internet", Toast.LENGTH_LONG).show();
    }

    private boolean internet_connected() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loc_data_fetcher();
                } else {
                    Toast.makeText(MainActivity.this, "Please provide the required permission", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void data_update(List<Candidates> officials) {
        if (officials != null)
        {
            officialsList = new ArrayList<>(officials);
            address.setText(officialsList.get(0).getCurrent_address());
            GovernmentListAdapter = new candidate_data_adapter(this, officials);
            data.setAdapter(GovernmentListAdapter);
            data.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }
        else
            Toast.makeText(MainActivity.this, "Please Enter valid location", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case(R.id.about):
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case(R.id.search):
                display_dialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loc_data_fetcher() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, myLocation -> {

                    if (myLocation != null)
                    {
                        try
                        {
                            String thisLocation = address_info_fetcher(myLocation);
                            prefs.edit().putString("location", thisLocation).apply();
                            location(prefs.getString("location","Chicago, Illinois"));
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                })
                .addOnFailureListener(this, e ->
                        Toast.makeText(MainActivity.this,
                                e.getMessage(), Toast.LENGTH_LONG).show());
    }



    private String address_info_fetcher(Location location) {
        List<Address> addr_lst = new ArrayList<>();
        try
        {
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            addr_lst = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            latitude = addr_lst.get(0).getLatitude();
            longitude = addr_lst.get(0).getLongitude();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return addr_lst.get(0).getAddressLine(0);
    }


    public void location(String myLocation) {
        GetPoliticianData.extract_data(this, myLocation);
    }

}