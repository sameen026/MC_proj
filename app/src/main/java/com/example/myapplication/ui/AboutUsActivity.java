package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Model.Plaza;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {
    public Button backBtn;
    //DatabaseReference myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        init();
//        myDB = FirebaseDatabase.getInstance().getReference().child("plaza");
//
//        String id = myDB.push().getKey();
//        Plaza p = new Plaza(id,"The plaza",200,150,350,400,40,20,"Daily","Daily","unRegistered",31.541979,74.300196,"Umar", "Gulberg");
//        myDB.child(id).setValue(p);
//
//
//        id = myDB.push().getKey();
//        p = new Plaza(id,"Plaza",150,50,350,500,40,20,"Daily","Daily","Registered",31.542445,74.301355,"Sameen","DHA");
//        myDB.child(id).setValue(p);
//
//        id = myDB.push().getKey();
//        p = new Plaza(id,"New Plaza",150,50,350,500,40,20,"Daily","Daily","Registered",31.537209,74.300283,"Sameen","PUCIT");
//        myDB.child(id).setValue(p);
//
//        id = myDB.push().getKey();
//        p = new Plaza(id,"Parking Plaza",150,50,350,500,40,20,"Daily","Daily","Registered",31.541335,74.302519,"Sameen","PU");
//        myDB.child(id).setValue(p);
    }



    @Override
    public void onClick(View view) {
         if(view.getId()==R.id.back_btn) {
            this.onBackPressed();

        }
    }
    public void init()
    {
        backBtn=findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
    }
}
