package com.example.chav.mapsproject;

import android.content.Context;
import android.util.Log;

import com.example.chav.mapsproject.model_dao.SpotDAO;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Chav on 3/6/2016.
 */
public class SpotManager {

    public static SpotManager instance;

    private ArrayList<Spot> mAllSpots = new ArrayList<>();
    private SpotDAO mSpotDAO;
    private Context mContext;

    private SpotManager(Context context) {
        this.mContext = context;
        mSpotDAO = SpotDAO.getInstance(context);
        mAllSpots = mSpotDAO.getAllSpots();
    }

    public static SpotManager getInstance(Context context) {
        if (instance == null) {
            instance = new SpotManager(context);
        }

        return instance;
    }

    public Spot addSpot(String name, LatLng latLng, User user, int image){
        Spot newSpot = mSpotDAO.addSpot(name, latLng, user, image);
        mAllSpots.add(newSpot);
        return newSpot;
    }

    public ArrayList<Spot> getAllSpots() {
        return mAllSpots;
    }
}
