package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Feedback;
import com.example.myapplication.Model.Plaza;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {
    private RatingBar ratingBar;
    private EditText review_et;
    private TextView txtRatingValue;
    private Button btnSubmit, backBtn;
    private Plaza plaza;
    public String userId, userName, imageURL;
    DatabaseReference feedbackTableRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        init();
        ratingBar.setRating(0.5f);
        setTitle("Feedback");
        addListenerOnRatingBar();
        addListenerOnButton();
    }
    @Override
    public void onClick(View v) {
        if (R.id.btnSubmit == v.getId()) {
           String ratings=String.valueOf(ratingBar.getRating());
           String review=review_et.getText().toString();
           if(ratings!=null && review!=null)
           {
               Feedback feedback=new Feedback(userId, plaza.getPlazaID(),ratings,review, userName, imageURL);
               String id=feedbackTableRef.child("Feedback").push().getKey();
               //Start Loading bar
               feedbackTableRef.child(id).setValue(feedback).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       Toast.makeText(getApplicationContext(),"Feedback Left Successfully",Toast.LENGTH_LONG).show();
                       Intent i = new Intent(FeedbackActivity.this, ViewPlazaDetailsActivity.class);
                       i.putExtra("plaza",plaza);
                       //End Loading bar
                       startActivity(i);
                       finish();
                   }
               });

           }
           if(TextUtils.isEmpty(review))
           {
                review_et.setError("Fill all fields");

           }
           if(ratingBar.getRating()==0.0)
           {
               Toast.makeText(getApplicationContext(), "Select Ratings", Toast.LENGTH_SHORT).show();
           }

        }
        if(v.getId()==R.id.back_btn) {
            this.onBackPressed();
        }
    }

    public void addListenerOnRatingBar() {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                txtRatingValue.setText(String.valueOf(rating));
            }
        });
    }

    public void addListenerOnButton() {
        btnSubmit.setOnClickListener(this);
        backBtn.setOnClickListener(this);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void init() {
        feedbackTableRef = FirebaseDatabase.getInstance().getReference("Feedback");

        btnSubmit=findViewById(R.id.btnSubmit);
        backBtn=findViewById(R.id.back_btn);

        ratingBar=findViewById(R.id.ratingBar);
        review_et=findViewById(R.id.review_et);
        plaza = (Plaza)getIntent().getSerializableExtra("plaza");
        userId = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);
        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        imageURL = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
    }
}
