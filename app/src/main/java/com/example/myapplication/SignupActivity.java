package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;


public class SignupActivity extends AppCompatActivity {
    TextInputLayout userName;
    TextInputLayout email;
    TextInputLayout password;
    TextInputLayout cPassword;
    Button signUpBtn;
    Button googleSignUpBtn;
    TextView textSignUp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        userName = (TextInputLayout) findViewById(R.id.name);
        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);
        cPassword = (TextInputLayout) findViewById(R.id.c_password);
        signUpBtn = (Button) findViewById(R.id.signup_btn);
        googleSignUpBtn = (Button) findViewById(R.id.google_signup_btn);
        textSignUp = (TextView) findViewById(R.id.or_text);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameString = userName.getEditText().getText().toString();
                String emailString = email.getEditText().getText().toString();
                String passwordString = password.getEditText().getText().toString();
                String cPasswordString = cPassword.getEditText().getText().toString();
                if(nameString.isEmpty()){

                }
                Intent i = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(i);
            }
        });



        //        Signup

        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Signin Button Clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(i);
            }
        });

//Login Button Pressed


    }
}
