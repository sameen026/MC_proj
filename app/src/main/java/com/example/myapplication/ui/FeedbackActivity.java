package com.example.myapplication.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Feedback;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {
    private RatingBar ratingBar;
    private EditText review_et;
    private TextView txtRatingValue;
    private Button btnSubmit;
    public Button backBtn;
    public String userId="-LxbPhmF0HoX4dkyNHBJ";
    public String plazaId="-LxrRJTk0IMuX3bNqWZn";
    DatabaseReference firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ratingBar=findViewById(R.id.ratingBar);
        review_et=findViewById(R.id.review_et);
        firebaseDatabase= FirebaseDatabase.getInstance().getReference("Feedback");
        setTitle("Feedback");
        addListenerOnRatingBar();
        addListenerOnButton();
        init();
    }
    @Override
    public void onClick(View v) {
        if (R.id.btnSubmit == v.getId()) {
           String ratings=String.valueOf(ratingBar.getRating());
           String review=review_et.getText().toString();
           if(ratings!=null && review!=null)
           {
               Feedback feedback=new Feedback(userId,plazaId,ratings,review);
               String id=firebaseDatabase.child("Feedback").push().getKey();
               firebaseDatabase.child(id).setValue(feedback);
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

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                txtRatingValue.setText(String.valueOf(rating));
            }
        });
    }

    public void addListenerOnButton() { }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void init()
    {
        btnSubmit=findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        backBtn=findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
    }
}
