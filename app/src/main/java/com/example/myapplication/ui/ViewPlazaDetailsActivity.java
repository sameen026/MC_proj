package com.example.myapplication.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Feedback;
import com.example.myapplication.Model.Plaza;
import com.example.myapplication.Model.SavedPlaza;
import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import uz.jamshid.library.ExactRatingBar;

public class ViewPlazaDetailsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    public GoogleMap googleMap;
    public MapFragment mapFragment;
    public Button saveBtn, feedbackBtn, backBtn;
    public TextView name, area, chargesCar, chargesBike, slotCar, slotBike, viewComments, commentsCount;
    private Plaza p;
    private ExactRatingBar ratingStars;
    DatabaseReference myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        p = (Plaza)i.getSerializableExtra("plaza");
        setContentView(R.layout.activity_view_plaza_details);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        ratingStars = findViewById(R.id.rate);
        mapFragment.getMapAsync(this);
        saveBtn=findViewById(R.id.save_btn);
        feedbackBtn=findViewById(R.id.give_feedback_btn);
        myDB = FirebaseDatabase.getInstance().getReference().child("savedPlaza");


        viewComments = findViewById(R.id.view_comment_tv);
        viewComments.setOnClickListener(this);
        commentsCount = findViewById(R.id.view_comments_count_tv);

        name=findViewById(R.id.plaza_name_tv);
        name.setText(p.getPlazaName());
        //Toast.makeText(getApplicationContext(),p.getPlazaName(),Toast.LENGTH_SHORT).show();
        area=findViewById(R.id.plaza_area_tv);
        area.setText(p.getArea());
        chargesCar=findViewById(R.id.plaza_charges_car_tv);
        chargesCar.setText(p.getCarFee()+" Rs Daily for car");
        chargesBike=findViewById(R.id.plaza_charges_bike_tv);
        chargesBike.setText(p.getBikeFee()+" Rs Daily for bike");
        slotCar=findViewById(R.id.slot_car_tv);
        slotCar.setText(p.getCarAvailableSlots()+" slots available for cars");
        slotBike=findViewById(R.id.slot_bike_tv);
        slotBike.setText(p.getBikeAvailableSlots()+" slots available for bikes");
        backBtn=findViewById(R.id.back_btn);
        saveBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        feedbackBtn.setOnClickListener(this);
        loadRatingStars();

    }

    private void loadRatingStars() {
        Query query = FirebaseDatabase.getInstance().getReference().child("Feedback")
                .orderByChild("plazaId")
                .equalTo(p.getPlazaID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float ratingCount = dataSnapshot.getChildrenCount();
                float avg = 0;
                if(dataSnapshot.exists()){

                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        Feedback fb = data.getValue(Feedback.class);
                        avg += Float.parseFloat(fb.getRatings());
                    }
                    avg = avg/ratingCount;
                    if(avg > 4.9){ // Because if I do 5.0, it sets this to 0.0, It's a library error
                        ratingStars.setStar(4.9999f);
                    }else{
                        ratingStars.setStar(avg);
                    }
                    commentsCount.setText("("+String.valueOf((int)ratingCount)+")");
                }else{
                    ratingStars.setStar(avg);
                    commentsCount.setText("("+String.valueOf((int)ratingCount)+")");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        try {
            googleMap.setMyLocationEnabled(true);
        } catch (SecurityException se) {

        }

        //Edit the following as per you needs
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        //

        LatLng placeLocation = new LatLng(p.getPlazaLatitude(), p.getPlazaLongitude()); //Make them global
        Marker placeMarker = googleMap.addMarker(new MarkerOptions().position(placeLocation)
                .title(p.getPlazaName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(placeLocation));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1000, null);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.save_btn) {
            AlertDialog diaBox = AskOption();
            diaBox.show();
        }
        else if(view.getId()==R.id.back_btn) {
            this.onBackPressed();
        }
        else if(view.getId()==R.id.give_feedback_btn) {
            Query query = FirebaseDatabase.getInstance().getReference().child("Feedback")
                    .orderByChild("plazaId")
                    .equalTo(p.getPlazaID());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Boolean isFeedbackFound = false;
                        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            Feedback fB = data.getValue(Feedback.class);
                            if(email.equals(fB.getUserId())){
                                isFeedbackFound = true;
                            }
                        }
                        if(!isFeedbackFound){
                            Intent i = new Intent(getApplicationContext(), FeedbackActivity.class);
                            i.putExtra("plaza",p);
                            Log.i("PlazaID123",p.getPlazaID());
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(),"You have already left feedback to this plaza.",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        //Nobody has left feedback to this plaza
                        Intent i = new Intent(getApplicationContext(), FeedbackActivity.class);
//                        Log.i("PlazaID123",p.getPlazaID());
                        i.putExtra("plaza",p);
                        startActivity(i);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }else if(view.getId() == R.id.view_comment_tv){
            Intent i = new Intent(this, ViewCommentsActivity.class);
            i.putExtra("plaza",p);
            startActivity(i);
        }

    }
    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Save")
                .setMessage("Do you want to save")
                .setIcon(R.drawable.ic_bookmark_black)

                .setPositiveButton("Save", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Query q = FirebaseDatabase.getInstance().getReference().child("savedPlaza")
                                .orderByChild("userId")
                                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    boolean isSaved = false;
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        SavedPlaza sP = data.getValue(SavedPlaza.class);
                                        String id = sP.getPlazaId();
                                        if (id.equals(p.getPlazaID())) {
                                            isSaved = true;
                                        }
                                    }
                                    if (!isSaved) {
                                        String id = myDB.push().getKey();
                                        SavedPlaza sP = new SavedPlaza(id, FirebaseAuth.getInstance().getCurrentUser().getEmail(), p.getPlazaID());
                                        myDB.child(id).setValue(sP);
                                        Toast.makeText(getApplicationContext(), "Plaza saved successfully ", Toast.LENGTH_SHORT).show();
                                        //your deleting code
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Plaza already in saved.", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    String id = myDB.push().getKey();
                                    SavedPlaza sP = new SavedPlaza(id, FirebaseAuth.getInstance().getCurrentUser().getEmail(), p.getPlazaID());
                                    myDB.child(id).setValue(sP);
                                    Toast.makeText(getApplicationContext(), "Plaza saved successfully ", Toast.LENGTH_SHORT).show();
                                    //your deleting code
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        dialog.dismiss();

                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }




}
