package com.example.myapplication.ui;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
public class SigninActivity extends AppCompatActivity {
    private static final String TAG = "Errir";
    TextInputLayout email, password;
    String emailString, passwordString;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
    DatabaseReference myDB;

    FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        email = findViewById(R.id.user_name);
        password=findViewById(R.id.pwd);
        //fAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();


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
                    emailString = email.getEditText().getText().toString();
                    passwordString = password.getEditText().getText().toString();
                    mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //Directing to Dashboard Screen page to Login to app
                                startActivity(new Intent(getApplicationContext(), MainScreenActivity.class));
                                finish();
                            }else if(task.getException().toString().equals("com.google.firebase.auth.FirebaseAuthInvalidUserException: There is no user record corresponding to this identifier. The user may have been deleted.")){
                                email.setError("Email not registered.");
                                Log.i("Umar",task.getException().toString());
                            }else{
                                email.setError(" ");
                                password.setError(" ");
                                Log.i("Umar",task.getException().toString());
                                Toast.makeText(getApplicationContext(),"Email or Password is Incorrect.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(getApplicationContext(), MainScreenActivity.class);
            startActivity(i);
            finish();
        }
    }


    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Query query = FirebaseDatabase.getInstance().getReference().child("user")
                                    .orderByChild("email")
                                    .equalTo(user.getEmail());
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        //User have already been registered to our app.
                                        // There isn't anything to do now
                                        startActivity(new Intent(SigninActivity.this, MainScreenActivity.class));
                                        finish();
                                    }else{
                                        String id = myDB.push().getKey();
                                        User usr = new User(id, user.getEmail(), user.getDisplayName(), "membership", user.getPhotoUrl().toString());
                                        myDB.child(id).setValue(usr);
                                        startActivity(new Intent(SigninActivity.this, MainScreenActivity.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(),"Sign in failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Error")
                .setMessage("Incorrect email or passwrod")
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
    private AlertDialog AskOptionToSignup() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Alert")
                .setMessage("User not Registered. Would you like to Signup?")
                .setIcon(R.drawable.ic_error_black_24dp)

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
                        String id = myDB.push().getKey();
                        User usr = new User(id, currUser.getEmail(), currUser.getDisplayName(), "membership", currUser.getPhotoUrl().toString());
                        myDB.child(id).setValue(usr);
                        startActivity(new Intent(getApplicationContext(), MainScreenActivity.class));
                        finish();
                        //your deleting code
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        mGoogleSignInClient.signOut();
                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    private AlertDialog AskOptionToSignin() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Alert")
                .setMessage("You signed up with Gmail. Signin with gmail?")
                .setIcon(R.drawable.ic_error_black_24dp)

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        signIn();
                        //your deleting code
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
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