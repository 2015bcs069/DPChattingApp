package com.example.tindyebwa.dpchattingapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText ema;
    private EditText password;
    private Button emails;
    Button esignin;

    //Button createAcct;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabasePlace_users, UsersDb;
    private Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
            //mytoolbar=findViewById(R.id.signin);
//            setSupportActionBar(mytoolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            //getSupportActionBar().setTitle(R.string.signin);
//
            if (mDatabasePlace_users != null) {
                mDatabasePlace_users.keepSynced(true);
            }
//
           mAuth = FirebaseAuth.getInstance();
            mDatabasePlace_users = FirebaseDatabase.getInstance().getReference().child("Place_Users");
           // Set up the login form.
           progressDialog = new ProgressDialog(this);
            ema = (EditText) findViewById(R.id.emaile);
            password = (EditText) findViewById(R.id.pass);
            esignin = (Button) findViewById(R.id.esb);
//
       esignin.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    startSigningIn();
                }
           });
       }

       private void startSigningIn() {
            String email = ema.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if(!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(email)//&&!Utils.isValidEmail(email)
            ){
                Toast.makeText(this, "Invalid email, Try again", Toast.LENGTH_SHORT).show();
                return;
          }
//
           if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
                if (pass.length() < 6) {
                    Toast.makeText(this, "Your password should be atleast 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setTitle("Checking sign in");
                progressDialog.setMessage("Please wait while we validate your credentials");
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){
                            final FirebaseUser user = mAuth.getCurrentUser();

                            String token = FirebaseInstanceId.getInstance().getToken();
                            Log.d(TAG, "onComplete: Token" + token);
                            FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid())
                                    .child("device_token").setValue(FirebaseInstanceId.getInstance().getToken()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                    if (user.isEmailVerified()) {
//                                        checkUserExists();


                                    } else {
                                        Toast.makeText(LoginActivity.this, "Your Email is not verified \n Please check your mail box to Verify it", Toast.LENGTH_SHORT).show();
                                        mAuth.signOut();

                                        //SIGN USER IN
                                        progressDialog.dismiss();
                                    }
                                }
                            });


                        }else {
                            Toast.makeText(LoginActivity.this, "Sign In Error", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }}
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@android.support.annotation.NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "All fields are required,please check your email and a password", Toast.LENGTH_SHORT).show();
            }
        }

    public void getCreateAcct(View createAcct) {
        Intent createacc = new Intent( LoginActivity.this, RegisterActivity.class );
        startActivity(createacc);
    }
}


