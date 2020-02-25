package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.SavedPlaza;

import java.util.List;

public class SavedPlazaAdapter extends RecyclerView.Adapter<SavedPlazaAdapter.MyViewHolder> {


    private List<SavedPlaza>  savedPlazaList;
    //public MyViewHolder.OnSavedPlazaClickListner onSavedPlazaClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, area;

        public MyViewHolder(View view){
            super(view);
            name =view.findViewById(R.id.name);
            area = view.findViewById(R.id.area);

        }
    }

    public SavedPlazaAdapter(List<SavedPlaza> savedPlazaList){

        this.savedPlazaList = savedPlazaList;
    }
    @NonNull
    @Override
    public SavedPlazaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_plaza_list_row
                        , parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedPlazaAdapter.MyViewHolder holder, int position) {

        SavedPlaza sP = savedPlazaList.get(position);

        holder.name.setText(sP.getPlazaId());
        holder.area.setText(sP.getUserId());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
