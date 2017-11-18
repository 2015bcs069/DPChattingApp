package com.example.tindyebwa.dpchattingapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PhonebkActivity extends AppCompatActivity {
//    ListView lvp;
//    Cursor cursor;
//    String name;
//    ArrayList arrayList;
    private static final int RESULT_PICK_CONTACT = 85500;

    private TextView textView1;
    private TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_phonebk );

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
    }

    public void pickContact(View v)
    {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //check whether the result is ok
        if (resultCode == RESULT_OK) {
            //check for request code,
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }

        } else {
            Log.e("PhonebkActivity", "Failed to pick contact");
        }
    }

    //query URI and read contact details
    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();

            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);

            textView1.setText(name);
            textView2.setText(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        lvp = (ListView) findViewById( R.id.lvpb );
//        //fetching contacts from Phonebook using Cursor
//
//        cursor = getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                null,null,null,
//                null,null);
//
//        while(cursor.moveToNext()){
//            name = cursor.getString( cursor.getColumnIndex( ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME ) );
//            arrayList = new ArrayList(  );
//            arrayList.add( name );
//        }
//        lvp.setAdapter( new ArrayAdapter( PhonebkActivity.this,R.layout.simple_list_item_1,arrayList ) );
    }
}
