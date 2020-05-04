package com.example.myapplication.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.util.InternetBroadcastReceiver;
import com.google.android.material.textfield.TextInputLayout;

public class ChangePasswordActivity extends Activity implements View.OnClickListener {
   public Button btn;
    public Button backBtn;
    TextInputLayout oldPass, newPass, confNewPass;
    public TextView intenetError;
    NetworkInfo netInfo;
    InternetBroadcastReceiver internetBroadcastReceiver;
    ConnectivityManager conMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pswd);
        init();
        intenetError=findViewById(R.id.internet_tv);
        conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = conMgr.getActiveNetworkInfo();
        if(netInfo!=null)
            intenetError.setVisibility(View.INVISIBLE);
        internetBroadcastReceiver=new InternetBroadcastReceiver();
        registerNetworkBroadcast();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.back_btn) {
            this.onBackPressed();
        }
        if(view.getId()==R.id.pswd_change_btn) {
            if(validatePassword(oldPass)){
                ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                if (netInfo != null) {
                    //Validate user

                }else{
                    Toast.makeText(getApplicationContext(), "No internet Connection", Toast.LENGTH_SHORT).show();

                }
            }

        }

//            Intent i=new Intent(this,SigninActivity.class);
//            startActivity(i);
    }
    public void init()
    {
        backBtn=findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        btn=findViewById(R.id.btn_Pswd);
        btn.setOnClickListener(this);

        oldPass = findViewById(R.id.old_password);
        newPass = findViewById(R.id.new_password);
        confNewPass = findViewById(R.id.confirm_password);
    }

    private boolean validatePassword(TextInputLayout password) {
        String pass = password.getEditText().getText().toString().trim();
        if (pass.isEmpty()) {
            password.setError("Password can't be empty");
            return false;
        } else if (pass.length() > 25) {
            password.setError("Password can't exceed 25 characters.");
            return false;
        } else {
            //Need to validate the password here
            password.setError(null);
            return true;
        }
    }
    private void registerNetworkBroadcast() {
        registerReceiver(internetBroadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
}
