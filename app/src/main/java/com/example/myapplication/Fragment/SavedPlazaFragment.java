package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Model.SavedPlaza;
import com.example.myapplication.Model.Plaza;
import com.example.myapplication.PlazaAdapter;
import com.example.myapplication.R;
import com.example.myapplication.ui.ViewSavedPlazaActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SavedPlazaFragment extends Fragment implements PlazaAdapter.MyViewHolder.OnPlazaClickListner, View.OnClickListener{
    private List<Plaza> plazaList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PlazaAdapter pAdapter;
    public Button backBtn;
    DatabaseReference myDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saved_plaza_list, container, false);

        myDB = FirebaseDatabase.getInstance().getReference().child("savedPlaza");


        prepareSamplePlazaData();
        pAdapter = new PlazaAdapter(plazaList, this);

        recyclerView = v.findViewById(R.id.recycler_view);
        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setAdapter(pAdapter);
        return v;
    }

    private void prepareSamplePlazaData() {
        myDB = FirebaseDatabase.getInstance().getReference().child("savedPlaza");
        Query query = FirebaseDatabase.getInstance().getReference().child("savedPlaza")
                .orderByChild("userId")
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        SavedPlaza sP = data.getValue(SavedPlaza.class);
                        String id = sP.getPlazaId();
                        Query q2 = FirebaseDatabase.getInstance().getReference().child("plaza")
                                .orderByChild("plazaID")
                                .equalTo(id);
                        //Log.e("SavedPlaza","Found saved Plaza "+id);
                        q2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    if(dataSnapshot.getChildrenCount() == 1){
                                        for (DataSnapshot data: dataSnapshot.getChildren()) {
                                            Plaza p = data.getValue(Plaza.class);
                                            plazaList.add(p);
                                            //Toast.makeText(getActivity(), "Plaza added to list " + p.getPlazaName(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }else{
                                    if(plazaList.size() == 0){
                                        Toast.makeText(getActivity(),"There is no saved Plaza",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                pAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }


                }else{
                    if(plazaList.size() == 0){
                        Toast.makeText(getActivity(),"No saved Plaza",Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onPlazaClick(int position) {
        Plaza plaza = plazaList.get(position);

        Intent i = new Intent(getActivity(), ViewSavedPlazaActivity.class);
        i.putExtra("plaza",plaza);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_btn) {
            getActivity().onBackPressed();
        }
    }



}