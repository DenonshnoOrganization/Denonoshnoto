package com.example.chav.mapsproject.model_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.chav.mapsproject.Message;
import com.example.chav.mapsproject.Spot;
import com.example.chav.mapsproject.User;
import com.example.chav.mapsproject.model_db.DatabaseHelper;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Chav on 3/6/2016.
 */
public class SpotDAO implements ISpotDAO {

    public static SpotDAO instance;
    private Context mContext;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private SpotDAO(Context context) {
        this.mContext = context;
    }

    @Override
    public Spot addSpot(String spotType, LatLng latlng, User user, int image) {//TODO Last point we added SPOTS to the database
        long lastId = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String date = sdf.format(calendar.getTime());

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_SPOT_USER_ID, user.getId());
        values.put(DatabaseHelper.KEY_SPOT_NAME, spotType);
        values.put(DatabaseHelper.KEY_SPOT_LAT, latlng.latitude);
        values.put(DatabaseHelper.KEY_SPOT_LONG, latlng.longitude);
        values.put(DatabaseHelper.KEY_CREATED_AT, date);
        values.put(DatabaseHelper.KEY_SPOT_RATE, 5);//TODO
        values.put(DatabaseHelper.KEY_SPOT_IMAGE, image);

        dbHelper = DatabaseHelper.getInstance(mContext);
        database = dbHelper.getWritableDatabase();

        database.insert(DatabaseHelper.TABLE_SPOTS, null, values);

        String selectQuery = " SELECT " + DatabaseHelper.KEY_ID + " FROM " + DatabaseHelper.TABLE_SPOTS + " ORDER BY " + DatabaseHelper.KEY_ID + " DESC LIMIT 1";
        Cursor c = database.rawQuery(selectQuery, null);
        if (c != null && c.moveToFirst()) {
            lastId = c.getLong(0);
        }

        Spot newSpot = new Spot(lastId, spotType, 5, latlng.latitude, latlng.longitude, calendar, image);

        database.close();
        Log.d("asd", "SpotDAO addSpot here " + spotType);
        return newSpot;
    }

    @Override
    public void removeSpot() {

    }

    @Override
    public ArrayList<Spot> getAllSpots() {
        ArrayList<Spot> allSpots = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + DatabaseHelper.TABLE_SPOTS;
        database = dbHelper.getInstance(mContext).getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                int rate = cursor.getInt(2);
                double latitude = cursor.getDouble(3);
                double longitude = cursor.getDouble(4);
                String createdAt = cursor.getString(5);
                Calendar calendar = new GregorianCalendar();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    calendar.setTime(sdf.parse(createdAt));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int image = cursor.getInt(7);
                Spot spot = new Spot(id, name, rate, latitude, longitude, calendar, image);
                allSpots.add(spot);
            } while (cursor.moveToNext());
        }
        database.close();
        Message.message(mContext, "returned allSpots information");
        return allSpots;
    }

    public static SpotDAO getInstance(Context context) {
        if (instance == null) {
            instance = new SpotDAO(context);
        }
        return instance;
    }
}

