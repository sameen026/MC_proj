package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlacesAutoCompleteAdapte extends RecyclerView.Adapter<PlacesAutoCompleteAdapte.MyViewHolder>{
    private List<String> locationList;

    public PlacesAutoCompleteAdapte(List<String> locationList) { this.locationList=locationList;}
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView desription;
        public MyViewHolder(View view) {
            super(view);
            desription = (TextView) view.findViewById(R.id.description);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_location_list_row,parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String location = locationList.get(position);
        holder.desription.setText(location);
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

}