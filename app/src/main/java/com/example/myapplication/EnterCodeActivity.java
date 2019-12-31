package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.broooapps.otpedittext2.OnCompleteListener;

public class EnterCodeActivity extends Activity implements OnCompleteListener,View.OnClickListener {
    com.broooapps.otpedittext2.OtpEditText et;
    public Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);
        init();
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

