package com.example.myapplication;

import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlazaAdapter extends RecyclerView.Adapter<PlazaAdapter.MyViewHolder> {

    private List<Plaza> plazaList;
    private MyViewHolder.OnPlazaClickListner onPlazaClickListner;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView area;
        public  OnPlazaClickListner onPlazaClickListner;
    public MyViewHolder(View view,OnPlazaClickListner onPlazaClickListner) {
        super(view);
        name = (TextView) view.findViewById(R.id.name);
        area = (TextView) view.findViewById(R.id.area);
        this.onPlazaClickListner=onPlazaClickListner;
        view.setOnClickListener(this);
    }

        @Override
        public void onClick(View view) {
            onPlazaClickListner.onPlazaClick(getAdapterPosition());
        }

        public interface OnPlazaClickListner{
        void onPlazaClick(int position);
        }
}

    public PlazaAdapter(List<Plaza> plazaList, MyViewHolder.OnPlazaClickListner onPlazaClickListner) {
        this.plazaList = plazaList;
        this.onPlazaClickListner=onPlazaClickListner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_plaza_list_row
                        , parent, false);

        return new MyViewHolder(itemView,onPlazaClickListner);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Plaza plaza =plazaList.get(position);

        holder.name.setText(plaza.getName());
        holder.area.setText(plaza.getArea());
    }

    @Override
    public int getItemCount() {
        return plazaList.size();
    }
}