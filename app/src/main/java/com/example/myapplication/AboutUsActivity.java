package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {
    public Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        init();


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
