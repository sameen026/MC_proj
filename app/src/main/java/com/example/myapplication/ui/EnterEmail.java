package com.example.myapplication.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

public class EnterEmail extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout emailEt;
    Button sendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_email);
        setTitle("Reset Password");
        init();
        setClickListeners();
    }

    private void init(){
        emailEt = findViewById(R.id.email_et);
        sendBtn = findViewById(R.id.submit_btn);
    }
    private void setClickListeners(){
        sendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.submit_btn:
                if(validateEmail()){
                    //Add loading bar
                    sendEmail();
                }
                break;
        }
    }

    private void sendEmail(){
        FirebaseAuth.getInstance().sendPasswordResetEmail(emailEt.getEditText().getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Email sent.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EnterEmail.this, SigninActivity.class));
                            //Remove Loading bar
                            finish();
                        }else if(task.getException().getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")){
                            Toast.makeText(getApplicationContext(), "Email not registered.", Toast.LENGTH_SHORT).show();
                            //Remove Loading bar
                        }else if(task.getException().getMessage().equals("The email address is badly formatted.")){
                            Toast.makeText(getApplicationContext(), "Email is badly formatted.", Toast.LENGTH_SHORT).show();
                            //Remove Loading bar
                        }else if(task.getException().getMessage().equals("A network error (such as timeout, interrupted connection or unreachable host) has occurred.")){
                            Toast.makeText(getApplicationContext(), "No Internet Connection.", Toast.LENGTH_SHORT).show();
                            //Remove Loading bar
                        }else{
                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                            Log.d("Umar4",task.getException().getMessage());
                            //Remove Loading bar
                        }
                    }
                });
    }
    private boolean validateEmail(){
        String text = emailEt.getEditText().getText().toString().trim();
        if(text.isEmpty()){
            emailEt.setError("Email can't be empty");
            return false;
        }else if(!isValidEmail(text)) {
            emailEt.setError("Invalid email address");
            return false;
        }else{
            //Need to validate email here
            emailEt.setError(null);
            return true;
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
