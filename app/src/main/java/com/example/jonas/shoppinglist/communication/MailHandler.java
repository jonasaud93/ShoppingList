package com.example.jonas.shoppinglist.communication;

import android.os.AsyncTask;
import android.util.Log;

public class MailHandler extends AsyncTask<String, Void, Void> {
    private static final String LOG_TAG = MailHandler.class.getSimpleName();
    @Override
    protected Void doInBackground(String... params) {
        Mail m = new Mail("shoppingsender@gmail.com", "50centwindowshopper");
        m.setBody(params[0]);
        String[] toArr = {"devylder3@outlook.com", "shoppingreceiver@gmail.com"};
        m.setTo(toArr);
        m.setFrom("shoppingsender@gmail.com");
        m.setSubject("New shopping data!");

        try {
            // m.addAttachment("/sdcard/filelocation");
            m.send();

        } catch (Exception e) {
            Log.e(LOG_TAG, "Could not send email", e);
        }
        return null;
    }
}
