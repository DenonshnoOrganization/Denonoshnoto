package com.example.chav.mapsproject.controller;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chav.mapsproject.AddSpotItem;
import com.example.chav.mapsproject.R;

import java.util.ArrayList;

/**
 * Created by Chav on 3/8/2016.
 */
public class AddSpotAdapter extends RecyclerView.Adapter<AddSpotAdapter.SpotViewHolder> {

    private ArrayList<AddSpotItem> addSpotItems;
    private AdapterCallback adapterCallback;

    public AddSpotAdapter(ArrayList<AddSpotItem>addSpotItems, AdapterCallback adapterCallback) {
        this.addSpotItems = addSpotItems;
        try {
            this.adapterCallback = adapterCallback;
        } catch (ClassCastException e) {
            throw new ClassCastException("Fragment must implement AdapterCallback interface");
        }

    }

    @Override
    public SpotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_spot_item_layout, parent, false);
        SpotViewHolder spotViewHolder = new SpotViewHolder(v);
        return spotViewHolder;
    }

    @Override
    public void onBindViewHolder(SpotViewHolder holder, int position) {
        final AddSpotItem current = addSpotItems.get(position);
        holder.text.setText(current.getText());
        holder.imageView.setImageResource(current.getImg());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setIcon(current.getImg())
                        .setTitle("Add Spot")
                        .setMessage("Adding " + current.getText() + " spot.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("asd", "onClick " + current.getText());
                                adapterCallback.onMethodCallback(current.getText(), current.getImg());
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return addSpotItems.size();
    }

    public static class SpotViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageButton imageView;

        public SpotViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.icon_text_view);
            imageView = (ImageButton) itemView.findViewById(R.id.icon_image_button);
        }
    }
    public interface AdapterCallback{
        void onMethodCallback(String iconText, int image);
    }
}
