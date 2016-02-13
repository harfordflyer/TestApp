package com.example.dhammond1.testapp;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        CreateDBSample();
        Intent i = new Intent(this, DataService.class);
        i.putExtra("name", "testapp_service");
        startService(i);
        boolean running = isServiceRunning(DataService.class);
        Toast.makeText(this, "service running = " + running, Toast.LENGTH_LONG);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean isServiceRunning(Class<?> serviceClass)
    {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            Log.d("running service: ", service.service.getClassName());
            if(serviceClass.getName().equals(service.service.getClassName()))
            {
                return true;
            }


        }
        return  false;
    }


    public void CreateDBSample(){
        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
        Log.d("Insert: ", "Inserting ...");
        Calendar c = Calendar.getInstance();


       /* dbHandler.addEntry(new TemperatureEntry(GetDateTime.GetDate(c),GetDateTime.GetTime(c),"200", "150"));

        dbHandler.addEntry(new TemperatureEntry(GetDateTime.GetDate(c),GetDateTime.GetTime(c),"210", "150"));

        dbHandler.addEntry(new TemperatureEntry(GetDateTime.GetDate(c),GetDateTime.GetTime(c),"220", "150"));

        dbHandler.addEntry(new TemperatureEntry(GetDateTime.GetDate(c), GetDateTime.GetTime(c), "230", "150"));

        dbHandler.addEntry(new TemperatureEntry("02/14/2016",GetDateTime.GetTime(c),"200", "150"));

        dbHandler.addEntry(new TemperatureEntry("02/14/2016",GetDateTime.GetTime(c),"210", "150"));

        dbHandler.addEntry(new TemperatureEntry("02/14/2016",GetDateTime.GetTime(c),"220", "150"));

        dbHandler.addEntry(new TemperatureEntry("02/14/2016", GetDateTime.GetTime(c), "230", "150"));*/

        Log.d("Reading", "Reading entries...");

       // List<TemperatureEntry> entries = dbHandler.getEntriesByDate(GetDateTime.GetDate(c));
        List<TemperatureEntry> entries = dbHandler.getEntriesByDate("02/14/2016");
    }



}

