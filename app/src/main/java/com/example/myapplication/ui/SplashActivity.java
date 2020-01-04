package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(2500);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(SplashActivity.this,firstStartActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();


        /** Duration of wait **/

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
//            new Handler().postDelayed(new Runnable(){
//                @Override
//                public void run() {
//                    /* Create an Intent that will start the Menu-Activity. */
//                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
//                    SplashActivity.this.startActivity(mainIntent);
//                    SplashActivity.this.finish();
//                }
//            }, SPLASH_DISPLAY_LENGTH);
    }
}
