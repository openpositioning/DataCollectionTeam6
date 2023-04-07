package com.example.a2_integration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements task1sensors.SensorsListener {

    FragmentManager fragmentManager; // Declare manager

    // Declare all fragments
    Fragment sensorsfragment = new task1sensors();
    Fragment pdrfragment = new task2pdr();
    Fragment apifragment = new task3api();
    Fragment currentfragment;

    // For the Nav Bar
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    String title; // Used to change title at top of app

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // This executes on creation of the app
        setTitle("Assignment2"); // Title of this app is Compass+

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set view to activity_main.xml file
        handlingNavBar(); // initialises navbar
        initialiseFragments(); // initialises all the fragments and shows default (compass)
    }

    private void handlingNavBar() {

        getSupportActionBar().setTitle("Assignment2");
        // The drawer layout toggles menu icon to open drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // Passes the Open and Close toggle for the drawer layout listener to toggle the button state
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // makes the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigation = findViewById(R.id.navigation);

        // Listens for clicks of nav bar buttons
        navigation.setNavigationItemSelectedListener(item -> {
            // hide current fragment
            fragmentManager.beginTransaction().hide(currentfragment).commit();
            // switch statement to select between fragments
            switch(item.getItemId()) {
                case R.id.nav_compass:
                    currentfragment =  sensorsfragment; // switch to compass
                    title = "Compass App";
                    break;
                case R.id.nav_pedometer:
                    currentfragment = pdrfragment; // switch to pedometer
                    title = "Pedometer App";
                    break;
                case R.id.nav_settings:
                    currentfragment = apifragment; // switch to settings
                    title = "Settings";
                default:
                    break;
            }
            // Show the fragment that was just selected
            fragmentManager.beginTransaction().show(currentfragment).commit();
            getSupportActionBar().setTitle(title); // sets title bar to fragment name
            drawerLayout.closeDrawer(GravityCompat.START); // close Navigation drawer

            return true;
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // When menu/back button is pressed
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialiseFragments() {

        fragmentManager = getSupportFragmentManager();

        // Initialise fragments and add them to flcontent frame layout declared in activity_main.xml
        fragmentManager.beginTransaction().add(R.id.flContent, sensorsfragment).commit();
        fragmentManager.beginTransaction().add(R.id.flContent, pdrfragment).commit();
        fragmentManager.beginTransaction().add(R.id.flContent, apifragment).commit();

        // Hide secondary fragments (pedometer, settings)
        fragmentManager.beginTransaction().hide(pdrfragment).commit();
        fragmentManager.beginTransaction().hide(apifragment).commit();

        // Set current fragment to the default (compass)
        currentfragment = sensorsfragment;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void SensorData(float sensor) {

    }
}