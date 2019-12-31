package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangePasswordActivity extends Activity implements View.OnClickListener {
   public Button btn;
    public Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pswd);
        init();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.back_btn) {
            this.onBackPressed();
        }
        if(view.getId()==R.id.btn_Pswd) {
            Intent i=new Intent(this,SigninActivity.class);
            startActivity(i);
        }

    }
    public void init()
    {
        backBtn=findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        btn=findViewById(R.id.btn_Pswd);
        btn.setOnClickListener(this);
    }
}
