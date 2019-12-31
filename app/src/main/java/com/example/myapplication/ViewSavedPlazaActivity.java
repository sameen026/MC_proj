package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewSavedPlazaActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    public GoogleMap googleMap;
    public MapFragment mapFragment;
    public Button shareBtn;
    public  Button deleteBtn;
    public TextView name;
    public TextView area;
    public TextView chargesCar;
    public TextView chargesBike;
    public  Button backBtn;
    public  Button feedbackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_plaza);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        shareBtn=findViewById(R.id.share_btn);
        deleteBtn=findViewById(R.id.delete_btn);
        feedbackBtn=findViewById(R.id.give_feedback_btn);

        name=findViewById(R.id.plaza_name_tv);
        name.setText("The Plaza");
        area=findViewById(R.id.plaza_area_tv);
        area.setText("Gulberg");
        chargesCar=findViewById(R.id.plaza_charges_car_tv);
        chargesCar.setText("20 Rs per hour for car");
        chargesBike=findViewById(R.id.plaza_charges_bike_tv);
        chargesBike.setText("10 Rs per hour for bike");
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

            LatLng placeLocation = new LatLng(31.570611, 74.310223); //Make them global
            Marker placeMarker = googleMap.addMarker(new MarkerOptions().position(placeLocation)
                    .title("PUCIT"));
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
