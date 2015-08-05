package com.example.jonas.shoppinglist.processes;

import android.app.Activity;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class ShoppingContacts extends AsyncTask<Void, Void, Void>  {
    private Activity activity;
    private Cursor contactCursor;
    private Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private String[] projection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};

    public ShoppingContacts(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {

        contactCursor = activity.getContentResolver().query(uri, projection,null,null, null);

        int nameIdx = contactCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
        int phoneIdx = contactCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int idIdx = contactCursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID);

        List<String> resultSet = new ArrayList<>();
        resultSet.add("Contact list:");
        while(contactCursor.moveToNext()){
            String name = contactCursor.getString(nameIdx);
            String phone = contactCursor.getString(phoneIdx);
            String id = contactCursor.getString(idIdx);

            resultSet.add("id:" + id + ", name = " + name + ", phone number = " + phone );
        }

        for(String contact : resultSet)
        Log.d("MainActivity", contact);

        return null;
    }
}
