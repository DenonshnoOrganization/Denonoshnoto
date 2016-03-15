package com.example.chav.mapsproject.model_dao;

import com.example.chav.mapsproject.Spot;
import com.example.chav.mapsproject.User;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Chav on 3/6/2016.
 */
public interface ISpotDAO {

    Spot addSpot(String spotType, LatLng latlng, User user, int image);

    void removeSpot();

    List<Spot> getAllSpots();

}
