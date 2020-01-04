package com.example.myapplication.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.R;

public class SigninActivity extends AppCompatActivity {
    EditText userName;
    EditText password;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        userName=findViewById(R.id.user_name);
        password=findViewById(R.id.pwd);


//        Signup
        TextView textSignup = (TextView) findViewById(R.id.or_text);
        textSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Signin Button Clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
            }
        });

//Login Button Pressed
        Button Signin_btn = (Button) findViewById(R.id.signin_btn);
        Signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Signin Button Clicked",Toast.LENGTH_SHORT).show();
                if(userName.getText().toString().equals("abc@gmail.com")&& password.getText().toString().equals("abc"))
                {
                    Intent i = new Intent(getApplicationContext(), MainScreenActivity.class);
                    startActivity(i);
                }
                else
                {
                    AlertDialog diaBox = AskOption();
                    diaBox.show();
                }

            }
        });

        TextView forgotPassword=findViewById(R.id.forgotpwd);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Signin Button Clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), EnterCodeActivity.class);
                startActivity(i);
            }
        });
    }
    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Error")
                .setMessage("Wrong email or passwrod")
                .setIcon(R.drawable.ic_error_black_24dp)

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }
}
