package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.Model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {
    TextInputLayout userName, email, password, cPassword;
    Button signUpBtn;
    TextView googleSignUpText, textSignUp;
    DatabaseReference myDB;
    GoogleSignInClient mGoogleSignInClient;
//    FirebaseAuth fAuth;
    int RC_SIGN_IN = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        userName = (TextInputLayout) findViewById(R.id.name);
        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);
        cPassword = (TextInputLayout) findViewById(R.id.c_password);
        signUpBtn = (Button) findViewById(R.id.signup_btn);
        googleSignUpText = (TextView) findViewById(R.id.google_signup_text);
        textSignUp = (TextView) findViewById(R.id.or_text);
        myDB= FirebaseDatabase.getInstance().getReference("user");
//        fAuth = FirebaseAuth.getInstance();

        //For google Signin//////////////////

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ////////////////////////////////////////////////////////


        googleSignUpText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        //
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateEmail() & validatePassword() & validateUserName() & validatePasswordMisMatch()){
                    String id=myDB.push().getKey();
                    User usr=new User(id,email.getEditText().getText().toString(),userName.getEditText().getText().toString(),password.getEditText().getText().toString(),"membership");
                    myDB.child(id).setValue(usr);
                    Intent i = new Intent(getApplicationContext(), SigninActivity.class);
                    startActivity(i);
                }
            }
        });



        //        Signup
        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(i);
            }
        });

//Login Button Pressed



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
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            acct = GoogleSignIn.getLastSignedInAccount(this);
            // Signed in successfully, show authenticated UI.

            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                String id=myDB.push().getKey();
                User usr=new User(id,personEmail,personGivenName,"Dummy","membership");
                myDB.child(id).setValue(usr);
//                Toast.makeText(getApplicationContext(),personEmail,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainScreenActivity.class);
                startActivity(i);
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());

            //updateUI(null);
        }
    }
    private boolean validatePasswordMisMatch(){
        String pass = password.getEditText().getText().toString().trim();
        String cPass = cPassword.getEditText().getText().toString().trim();

        if(!pass.equals(cPass)){
            cPassword.setError("Password didn't match");
            Toast.makeText(getApplicationContext(),cPass,Toast.LENGTH_SHORT).show();
            return false;
        }else{
            //Need to validate the password here
            password.setError(null);
            return true;
        }
    }
    private boolean validateUserName(){
        String text = userName.getEditText().getText().toString().trim();
        if(text.isEmpty()){
            userName.setError("Password can't be empty");
            return false;
        }else if(text.length() > 25){
            userName.setError("Username can't exceed 15 characters.");
            return false;
        }else{
            //Need to validate the password here
            userName.setError(null);
            return true;
        }
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
    private boolean validatePassword(){
        String pass = password.getEditText().getText().toString().trim();
        if(pass.isEmpty()){
            password.setError("Password can't be empty");
            return false;
        }else if(pass.length() > 25){
            password.setError("Password can't exceed 25 characters.");
            return false;
        }else if(pass.length() < 8){
            password.setError("Password must be at least 8 character long.");
            return false;
        }else{
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
