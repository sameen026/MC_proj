package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.FeedbackAdapter;
import com.example.myapplication.Model.Feedback;
import com.example.myapplication.Model.Plaza;
import com.example.myapplication.PlazaAdapter;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewCommentsActivity extends AppCompatActivity implements FeedbackAdapter.MyViewHolder.OnFeedbackClickListner, View.OnClickListener{
    private List<Feedback> feedbackList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FeedbackAdapter fAdapter;
    DatabaseReference myDB;
    Intent i;
    Plaza p;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);
        setTitle("Comments");
        i = getIntent();
        p = (Plaza)i.getSerializableExtra("plaza");
        myDB = FirebaseDatabase.getInstance().getReference();

        fAdapter = new FeedbackAdapter(feedbackList, this);
        prepareSampleData();
        recyclerView = findViewById(R.id.recycler_view_new);

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fAdapter);


    }

    private void prepareSampleData(){
        Query query = FirebaseDatabase.getInstance().getReference().child("Feedback")
                .orderByChild("plazaId")
                .equalTo(p.getPlazaID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        Feedback f = data.getValue(Feedback.class);
                        feedbackList.add(f);
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"No Feedback for this Plaza.",Toast.LENGTH_SHORT).show();

                }
                fAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onFeedbackClick(int position) {

    }

    @Override
    public void onClick(View v) {

    }
}