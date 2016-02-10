package com.example.dhammond1.testapp;

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

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

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

    public void CreateDBSample(){
        //DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext(),null, null, 1);
        DatabaseHandler dbHandler = DatabaseHandler.getInstance(getApplicationContext());
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

    public class DataService extends Service {

        private final IBinder mBinder = new MyBinder();
        public TemperatureEntry sampleEntry = new TemperatureEntry(null, null, null, null);

        public int onStartCommand(Intent intent, int flags, int startId){
            //to do ---- something useful
            //for now, write a method that will return a random value of pit and meat temp
            //in a TemperatureEntry class
            ReadTemperatures(sampleEntry);
            return Service.START_NOT_STICKY;
        }

        public IBinder onBind(Intent intent){
            //to do for communication return IBinder implementation
            return null;
        }

        public class MyBinder extends Binder {
            DataService getService(){
                return DataService.this;
            }
        }

        public TemperatureEntry GetTemperatureEntry()
        {
            return sampleEntry;
        }

        private void ReadTemperatures(TemperatureEntry sample)
        {
            Random rand = new Random();
            int pit = rand.nextInt(240) + 20;
            rand = new Random();
            int meat = rand.nextInt(220) + 60;
            sample.setPitTemp(Integer.toString(pit));
            sample.setMeatTemp(Integer.toString(meat));
        }
    }

   /* public final static class GetDateTime
    {
        public static String GetDate(Calendar calendar)
        {
            int Month = calendar.get(Calendar.MONTH) + 1;
            int Day = calendar.get(Calendar.DAY_OF_MONTH);
            int Year = calendar.get(Calendar.YEAR);

            String date = String.format("%02d/%02d/%04d", Month,Day,Year);
            return  date;
        }

        public static String GetTime(Calendar calendar)
        {
            calendar.setTime(calendar.getTime());

            String time = String.format("%1$tl %1$tM %1$tS %1$tp", calendar);
            return time;
        }
    }*/

    //TempEntry class
   /* public final class TemperatureEntry
    {
        //private vars
        int _id;
        //Calendar.getTime()
        String _date;
        String _time;
        String _pitTemp;
        String _meatTemp;

        public TemperatureEntry()
        { }

        public TemperatureEntry(String date, String time, String pitTemp, String meatTemp)
        {
            this._date = date;
            this._time = time;
            this._pitTemp = pitTemp;
            this._meatTemp = meatTemp;
        }

        public TemperatureEntry(int id, String date, String time, String pitTemp, String meatTemp)
        {
            this._id = id;
            this._date = date;
            this._time = time;
            this._pitTemp = pitTemp;
            this._meatTemp = meatTemp;
        }


        public int getID(){ return this._id; }

        public void setID(int id){ this._id = id; }

        public String getDate()  { return this._date; }

        public void setDate(String date) { this._date = date; }

        public String getTime()
        {
            return this._time;
        }

        public void setTime(String time)
        {
            this._time = time;
        }

        public String getPitTemp()
        {
            return this._pitTemp;
        }

        public void setPitTemp(String pitTemp)
        {
            this._pitTemp = pitTemp;
        }

        public String getMeatTemp()
        {
            return this._meatTemp;
        }

        public void setMeatTemp(String meatTemp)
        {
            this._meatTemp = meatTemp;
        }

    }

    //Database class
    public class DatabaseHandler extends SQLiteOpenHelper
    {
        Context _context;


        // Database Version
        private static final int DATABASE_VERSION = 3;

        // Database Name
        private static final String DATABASE_NAME = "temperatureEntry.db";

        // Contacts table name
        public static final String TABLE_ENTRIES = "entries";

        // Contacts Table Columns names
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_PIT_TEMP = "pitTemp";
        public static final String COLUMN_MEAT_TEMP = "meatTemp";


        public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            _context = context;
        }

        public String DeleteTable()
        {
            return "DROP TABLE IF EXISTS " + TABLE_ENTRIES;
        }

        public String CreateTableString()
        {
            final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_ENTRIES + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_DATE + " TEXT,"
                    + COLUMN_TIME + " TEXT,"
                    + COLUMN_PIT_TEMP + " TEXT,"
                    + COLUMN_MEAT_TEMP + " TEXT" + ")";
            return SQL_CREATE_ENTRIES;
        }

        public boolean DoesDatabaseExist(Context context, String dbName)
        {
            File dbFile = context.getDatabasePath(dbName);
            return dbFile.exists();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            //start with a clean db for now
            if(DoesDatabaseExist(_context, TABLE_ENTRIES))
            {
                db.execSQL(DeleteTable());
            }

            db.execSQL(CreateTableString());

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);

            // Create tables again
            onCreate(db);
        }

        public void addEntry(TemperatureEntry entry)
        {
            SQLiteDatabase db = null;
            try {
                db = this.getWritableDatabase();
            }
            catch(Exception e)
            {
                Log.d("database error", e.getMessage());
            }
            ContentValues values = new ContentValues();
            values.put(COLUMN_DATE, entry.getDate());
            values.put(COLUMN_TIME, entry.getTime());
            values.put(COLUMN_PIT_TEMP, entry.getPitTemp());
            values.put(COLUMN_MEAT_TEMP, entry.getMeatTemp());

            db.insert(TABLE_ENTRIES, null, values);
            db.close();
        }

       *//* public TemperatureEntry getEntry(int id)
        {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_TEMPSTER, new String[]{
                            KEY_ID, KEY_DATE, KEY_PIT_TEMP, KEY_MEAT_TEMP}, KEY_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if(cursor != null)
                cursor.moveToFirst();

            TemperatureEntry entry = new TemperatureEntry(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3));

            cursor.close();
            return entry;

        }*//*

        public List<TemperatureEntry> getEntriesByDate(String date)
        {
            List<TemperatureEntry> listEntry = new ArrayList<TemperatureEntry>();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ENTRIES  + " WHERE " + COLUMN_DATE + " = \"" + date + "\"";
            //String selectQuery = "SELECT  * FROM " + TABLE_ENTRIES + " WHERE " + COLUMN_DATE + " = \"" + date + "\"";

            SQLiteDatabase db = this.getWritableDatabase();
            //Cursor cursor = db.rawQuery(selectQuery, new String[]{date});
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(selectQuery, null);
            }
            catch(Exception ex)
            {
                Log.d("GetEntriesError", ex.getMessage());
            }
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    TemperatureEntry entry = new TemperatureEntry();
                    entry.setID(Integer.parseInt(cursor.getString(0)));
                    entry.setDate(cursor.getString(1));
                    entry.setTime(cursor.getString(2));
                    entry.setPitTemp(cursor.getString(3));
                    entry.setMeatTemp(cursor.getString(4));
                    // Adding contact to list
                    listEntry.add(entry);
                } while (cursor.moveToNext());
            }
            cursor.close();
            // return contact list
            return listEntry;
        }


    }*/
}
