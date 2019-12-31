package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class firstStartActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);

//        Signup
        Button Signin = (Button) findViewById(R.id.signin);
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Signin Button Clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(i);
            }
        });
//        Login
        Button Signup = (Button) findViewById(R.id.signup);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Signin Button Clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
            }
        });
    }
}
