package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.myapplication.R;

public class PrivacyPolicyActivity extends AppCompatActivity implements View.OnClickListener {
    WebView web;
    public Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        web =(WebView)findViewById(R.id.webView);
        web.loadUrl("file:///android_asset/privacy_policy.html");
        backBtn=findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.back_btn) {
            this.onBackPressed();
        }
    }
}
