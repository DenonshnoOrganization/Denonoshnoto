package com.example.chav.mapsproject.controller;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.chav.mapsproject.AddSpotItem;
import com.example.chav.mapsproject.R;

import java.util.ArrayList;

/**
 * Created by Chav on 3/10/2016.
 */
public class AddSpotFragment extends DialogFragment implements AddSpotAdapter.AdapterCallback{

    private AddSpotAdapter addSpotAdapter;
    private ArrayList<AddSpotItem> addSpotItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private OnSpotSelectedListener onSpotSelectedListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_spot_fragment, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.add_spot_recycler_view);
        addSpotItems.add(new AddSpotItem(R.drawable.gas_color, "Gas Station"));
        addSpotItems.add(new AddSpotItem(R.drawable.shop_color, "Shop"));
        addSpotItems.add(new AddSpotItem(R.drawable.atm_color, "ATM"));
        addSpotItems.add(new AddSpotItem(R.drawable.pharmacy_color, "Pharmacy"));
        addSpotItems.add(new AddSpotItem(R.drawable.animal_clinic, "Animals"));
        addSpotItems.add(new AddSpotItem(R.drawable.pizza_color, "Fast Food"));
        addSpotItems.add(new AddSpotItem(R.drawable.restaurant_color, "Restaurant"));
        addSpotItems.add(new AddSpotItem(R.drawable.casino_color, "Casino"));
        addSpotItems.add(new AddSpotItem(R.drawable.wi_fi, "Free Wi-FI"));


        recyclerView.setHasFixedSize(true);
        addSpotAdapter = new AddSpotAdapter(addSpotItems, this);
        recyclerView.setAdapter(addSpotAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(v.getContext(), 3));

        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onSpotSelectedListener = (OnSpotSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnSpotSelectedListener");
        }
    }

    @Override
    public void onMethodCallback(String iconText, int image) {
        Log.d("asd", "respond to " + iconText);
        onSpotSelectedListener.onSpotSelected(iconText, image);
        getDialog().hide();
    }

    public interface OnSpotSelectedListener {
        void onSpotSelected(String spotType, int image);
    }
}
