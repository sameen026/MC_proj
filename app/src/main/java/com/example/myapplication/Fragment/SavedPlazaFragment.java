package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Plaza;
import com.example.myapplication.PlazaAdapter;
import com.example.myapplication.R;
import com.example.myapplication.ui.ViewSavedPlazaActivity;

import java.util.ArrayList;
import java.util.List;

public class SavedPlazaFragment extends Fragment implements PlazaAdapter.MyViewHolder.OnPlazaClickListner, View.OnClickListener{
    private List<Plaza> plazaList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PlazaAdapter pAdapter;
    public Button backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saved_plaza_list, container, false);
        prepareSamplePlazaData();

        recyclerView = v.findViewById(R.id.recycler_view);
        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);

        pAdapter = new PlazaAdapter(plazaList, this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(pAdapter);
        return v;
    }

    private void prepareSamplePlazaData() {
        Plaza plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);

        plaza = new Plaza("Mad Max: Fury Road", 1.0, 2.0, "pucit");
        plazaList.add(plaza);


        //pAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPlazaClick(int position) {
        Intent i = new Intent(getActivity(), ViewSavedPlazaActivity.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_btn) {
           getActivity().onBackPressed();
        }
    }

}
