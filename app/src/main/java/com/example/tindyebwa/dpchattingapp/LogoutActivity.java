package com.example.tindyebwa.dpchattingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_logout );

        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {




                        if (task.isComplete()) {
                            Toast.makeText( LogoutActivity.this, "You have logged out!", Toast.LENGTH_SHORT ).show();
                            Intent homeIntent = new Intent( Intent.ACTION_MAIN );
                            homeIntent.addCategory( Intent.CATEGORY_HOME );
                            homeIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                            startActivity( homeIntent );
                        }

//                        Thread t1 = new Thread(){
//                            public void run(){
//
//                                try {
//                                    Thread.sleep(2000);
//                                    finish();
//                                } catch (InterruptedException e) {
//                                    // Auto-generated catch block
//                                    e.printStackTrace();
//                                }finally{
//                                    Intent intent = new Intent(LogoutActivity.this,LogoutActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent);
//                                    LogoutActivity.this.finish();
//
//                                }
//                            }
//                        };
//                        t1.start();
//                        Intent intent = new Intent(Intent.ACTION_MAIN);
//                        intent.addCategory(Intent.CATEGORY_HOME);
//                        startActivity(intent);
                    }
                });
    }
}
