package com.example.tindyebwa.dpchattingapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_MS = 6000;
    private Handler mHandler;
    private Runnable mRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_screen );

        mHandler = new Handler();


        mRunnable = new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent( SplashScreenActivity.this, MainActivity.class );
                startActivity( intent );

                // check if user is already logged in or not
//                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//                    // if logged in redirect the user to user Chatting activity
//                    startActivity( new Intent( SplashScreenActivity.this, MainActivity.class ) );
//                }
//                else{
//                // otherwise redirect the user to login activity
//                    startActivity( new Intent( SplashScreenActivity.this, LoginActivity.class ) );
//            }
                finish();
            }

            };

        mHandler.postDelayed( mRunnable, SPLASH_TIME_MS );
    }
}
