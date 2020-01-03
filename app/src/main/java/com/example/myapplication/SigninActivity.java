package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.Model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity {
    TextInputLayout email;
    TextInputLayout password;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
    DatabaseReference myDB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        email=findViewById(R.id.user_name);
        password=findViewById(R.id.pwd);

        myDB = FirebaseDatabase.getInstance().getReference("user");
        //This is my code/////////////////////////////////////////////////////////////////////////
        com.google.android.gms.common.SignInButton googleSignIn = findViewById(R.id.sign_in_button);
        googleSignIn.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////


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
                if(validateEmail() & validatePassword())
                {
                    Query query = FirebaseDatabase.getInstance().getReference("user").orderByChild("email").equalTo(email.getEditText().getText().toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                //Do something to verify password
                                boolean isFound = false;
                                for(DataSnapshot data: dataSnapshot.getChildren()){
                                    User user = data.getValue(User.class);
                                    if(user.getEmail().equals("aleemahmada107@gmail.com")){
                                        isFound = true;
                                    }
                                }
                                if(isFound) {
                                    Toast.makeText(getApplicationContext(), "Email found", Toast.LENGTH_LONG).show();
                                }else
                                {
                                    Toast.makeText(getApplicationContext(), "Email not found", Toast.LENGTH_LONG).show();
                                }

                            }else{
                                Toast.makeText(getApplicationContext(),"Email not found in DB",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//                    Intent i = new Intent(getApplicationContext(), MainScreenActivity.class);
//                    startActivity(i);
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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in the GoogleSignInAccount will be non-null.
        if(GoogleSignIn.getLastSignedInAccount(this)!= null){
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            //The user is already signed in. Update UI accordingly
            Intent i = new Intent(getApplicationContext(), MainScreenActivity.class);
            startActivity(i);

//            Toast.makeText(getApplicationContext(),"User has already signed in to the app.",Toast.LENGTH_SHORT);
        }
    }


    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent i = new Intent(getApplicationContext(), MainScreenActivity.class);
            startActivity(i);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());

            //updateUI(null);
        }
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
    private boolean validateEmail(){
        String text = email.getEditText().getText().toString().trim();
        if(text.isEmpty()){
            email.setError("Email can't be empty");
            return false;
        }else if(!isValidEmail(text)) {
            email.setError("Invalid email address");
            return false;
        }else{
            //Need to validate email here
            email.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
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
    private boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}