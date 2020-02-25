package com.example.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.broooapps.otpedittext2.OnCompleteListener;
import com.example.myapplication.Model.SavedPlaza;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterCodeActivity extends Activity implements OnCompleteListener,View.OnClickListener {
    com.broooapps.otpedittext2.OtpEditText et;
    public Button backBtn;
    DatabaseReference myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);
        init();
        myDB = FirebaseDatabase.getInstance().getReference("savedPlazas");
        String id = myDB.push().getKey();
        SavedPlaza savedPlaza = new SavedPlaza(id,"myUserID","myPlazaID");
        myDB.child(id).setValue(savedPlaza);
    }


    @Override
    public void onComplete(String value) {
        Intent i = new Intent(this, ChangePasswordActivity.class);
        startActivity(i);
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.back_btn) {
            this.onBackPressed();
        }
    }
    public void init()
    {
        et = findViewById(R.id.et1_main_activity);
        et.setOnCompleteListener(this);
        backBtn=findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
    }

}

