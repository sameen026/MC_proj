package com.example.myapplication.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class ViewSavedPlazaActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    public GoogleMap googleMap;
    public MapFragment mapFragment;
    public Button shareBtn, backBtn, feedbackBtn, deleteBtn;
    public TextView name, area, chargesCar, chargesBike, slotCar, slotBike;
    Intent i;
    Plaza p;
    DatabaseReference myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_plaza);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        shareBtn=findViewById(R.id.share_btn);
        deleteBtn=findViewById(R.id.delete_btn);
        feedbackBtn=findViewById(R.id.give_feedback_btn);
        i = getIntent();
        p =(Plaza) i.getSerializableExtra("plaza");
        myDB = FirebaseDatabase.getInstance().getReference();

        name=findViewById(R.id.plaza_name_tv);
        name.setText(p.getPlazaName());

        area=findViewById(R.id.plaza_area_tv);
        area.setText(p.getArea());

        chargesCar=findViewById(R.id.plaza_charges_car_tv);
        chargesCar.setText(Double.toString(p.getCarFee())+" Rs Daily for car");

        chargesBike=findViewById(R.id.plaza_charges_bike_tv);
        chargesBike.setText(Double.toString(p.getBikeFee())+" Rs Daily for car");

        slotCar=findViewById(R.id.slot_car_tv);
        slotCar.setText(p.getCarAvailableSlots()+" slots available for cars");

        slotBike=findViewById(R.id.slot_bike_tv);
        slotBike.setText(p.getBikeAvailableSlots()+" slots available for bikes");

        backBtn=findViewById(R.id.back_btn);
        shareBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
       backBtn.setOnClickListener(this);
        feedbackBtn.setOnClickListener(this);

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
        if(view.getId()==R.id.share_btn) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "abc");
            sendIntent.setType("text/plain");
            Intent.createChooser(sendIntent, "Share via");
            startActivity(sendIntent);
        }
       else if(view.getId()==R.id.delete_btn) {
            AlertDialog diaBox = AskOption();
            diaBox.show();
        }
        else if(view.getId()==R.id.back_btn) {
           this.onBackPressed();
        }
        else if(view.getId()==R.id.give_feedback_btn) {
            Intent i=new Intent(this,FeedbackActivity.class);
            startActivity(i);
        }

    }
    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.ic_delete_black)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        Query query = myDB.child("savedPlaza")
                                .orderByChild("userId")
                                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    for(DataSnapshot data: dataSnapshot.getChildren()){
                                        SavedPlaza sP = data.getValue(SavedPlaza.class);
                                        if(p.getPlazaID().equals(sP.getPlazaId())){
                                            data.getRef().removeValue();
                                            Toast.makeText(getApplicationContext(),"Plaza deleted successfully",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    //After deletion
                                    startActivity(new Intent(getApplicationContext(),MainScreenActivity.class));
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
