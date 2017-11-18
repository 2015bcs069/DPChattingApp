package com.example.tindyebwa.dpchattingapp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private static final int SIGN_IN_REQUEST_CODE = 111;
    private FirebaseListAdapter<ChatMessage> adapter;
    private String loggedInUserName = "";
    ListView listView;
    private SensorManager sensorManager;
    private Sensor sensor;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sensors
        sensorManager = (SensorManager) getSystemService( SENSOR_SERVICE );
        sensor=sensorManager.getDefaultSensor( Sensor.TYPE_PROXIMITY );
        input = (EditText) findViewById(R.id.input);

        sensorManager.registerListener( this,sensor,SensorManager.SENSOR_DELAY_NORMAL );
        if (sensor==null){
            Toast.makeText( this, "This Phone does not have the Proximity Sensor sensor. Don't worry, You will buy it very soon!",Toast.LENGTH_LONG).show() ;
        }

        //find views by Ids
        ImageButton fab = (ImageButton) findViewById(R.id.fab);
        final EditText input = (EditText) findViewById(R.id.input);
        listView = (ListView) findViewById(R.id.list);

         if (FirebaseAuth.getInstance().getCurrentUser() == null) {

            // Start sign in/sign up activity
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .build(), SIGN_IN_REQUEST_CODE);
        }  else {
            // User is already signed in, show list of messages

            showAllOldMessages();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter some texts!", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase.getInstance()
                            .getReference().child("messages")
                            .push()
                            .setValue(new ChatMessage(input.getText().toString(),
                                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                                    FirebaseAuth.getInstance().getCurrentUser().getUid())
                            );
                    input.setText("");
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sign_out) {
            startActivity( new Intent( this,LogoutActivity.class ) );
        }
        else if(item.getItemId() == R.id.cam) {
            startActivity( new Intent( this,PhonebkActivity.class ) );
        }
        else if (item.getItemId() == R.id.play) {
            startActivity( new Intent( this,PhonebkActivity.class ) );
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, " You have successfully Signed in!", Toast.LENGTH_LONG).show();
                showAllOldMessages();
            } else {
                Toast.makeText(this, "Sign in failed, keep trying!", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void showAllOldMessages() {
        loggedInUserName = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("Main", "user id: " + loggedInUserName);

        adapter = new MessageAdapter(this, ChatMessage.class, R.layout.chatout,
                FirebaseDatabase.getInstance().getReference().child("messages"));
        listView.setAdapter(adapter);
    }

    public String getLoggedInUserName() {
        return loggedInUserName;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        input.setText( String.valueOf( sensorEvent.values[0] ) );
       //Intent intent= new Intent( MainActivity.this, MainActivity.class );
        //startActivity( intent );
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
