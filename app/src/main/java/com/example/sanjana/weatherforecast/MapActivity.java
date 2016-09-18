package com.example.sanjana.weatherforecast;

/**
 * Created by sanjana on 12/8/2015.
 */


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.hamweather.aeris.communication.AerisEngine;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        AerisEngine.initWithKeys("ahKHSTILSIBzHHoYFRq8l", "Bo8CDmWmoJLkpaJgMdppLKTiqpdbS6JmiAxWzG0Y", this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MapFragment myFragment = new MapFragment();
        Bundle bundle = getIntent().getExtras();
        myFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.Map_Frame, myFragment);
        fragmentTransaction.commit();
    }

}