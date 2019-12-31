package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private Button btnSubmit;
    private RelativeLayout rl;
    public Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle("Feedback");
        addListenerOnRatingBar();
        addListenerOnButton();
        init();
    }
    @Override
    public void onClick(View v) {
        if (R.id.btnSubmit == v.getId()) {
           // Intent i = new Intent(this, PlazaProfile.class);
            //startActivity(i);
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

    public void addListenerOnButton() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        //if click on me, then display the current rating value.
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//
////            @Override
////            public void onClick(View v) {
////
////                Toast.makeText(MyAndroidAppActivity.this,
////                        String.valueOf(ratingBar.getRating()),
////                        Toast.LENGTH_SHORT).show();
////
////            }
//
//        });


    }

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
