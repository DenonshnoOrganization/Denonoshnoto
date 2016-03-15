package com.example.chav.mapsproject.controller;

import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.chav.mapsproject.FilterSpotItem;
import com.example.chav.mapsproject.R;

import java.util.ArrayList;

/**
 * Created by Chav on 3/13/2016.
 */
public class FilterSpotAdapter extends RecyclerView.Adapter<FilterSpotAdapter.FilterSpotViewHolder> {

    private ArrayList<FilterSpotItem> filterSpotItems;
    private FilterCallback filterCallback;

    public FilterSpotAdapter(ArrayList<FilterSpotItem> filterSpotItems, FilterCallback filterCallback) {
        this.filterSpotItems = filterSpotItems;
        this.filterCallback = filterCallback;
    }

    @Override

    public FilterSpotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_spot_item_layout, parent, false);
        FilterSpotViewHolder filterSpotViewHolder = new FilterSpotViewHolder(v);
        return filterSpotViewHolder;
    }

    @Override
    public void onBindViewHolder(final FilterSpotViewHolder holder, int position) {
        final FilterSpotItem current = filterSpotItems.get(position);
        holder.filterImageButton.setImageResource(current.getImg());
        holder.filterImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.isSelected()){
                    v.setSelected(false);
                    v.getBackground().clearColorFilter();
                    Log.d("fil", "removed " + current.getImg());
                } else {
                    v.setSelected(true);
                    v.getBackground().setColorFilter(0xe0f47521,PorterDuff.Mode.SRC_ATOP);
                    Log.d("fil", "added " + current.getImg());
                }
                filterCallback.onFilterCallback(current.getImg());
            }
        });

        holder.filterImageButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO GETS YOU TO THE NEAREST SPOT OF THAT TYPE OR GIVES YOU AN ALERT DIALOG STATING THAT THERE IS NOTHING AROUND.
                //TODO GIVE ALSO REASONABLE BOUNDS WITHIN WHICH YOU WILL BE SEARCHING
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterSpotItems.size();
    }

    public static class FilterSpotViewHolder extends RecyclerView.ViewHolder {

        private ImageButton filterImageButton;

        public FilterSpotViewHolder(View itemView) {
            super(itemView);
            filterImageButton = (ImageButton) itemView.findViewById(R.id.filter_button);
        }
    }

    public interface FilterCallback{
        void onFilterCallback(Integer imageId);
    }
}
