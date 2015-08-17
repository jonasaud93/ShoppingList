package com.example.jonas.shoppinglist.processes;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingGallery extends AsyncTask<Void, Void, List<String>> {
    private Activity activity;
    private static final String LOG_TAG = ShoppingGallery.class.getSimpleName();

    private Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private String[] projection = {MediaStore.Images.Thumbnails.DATA};
    private Cursor imageCursor;

    public ShoppingGallery(Activity activity){
        this.activity = activity;
        imageCursor = activity.getContentResolver().query(uri, projection, null, null, null);
    }


    @Override
    protected List<String> doInBackground(Void... params) {

        List<String> imgs = new ArrayList<>();
        while(imageCursor.moveToNext()){
            if(imgs.size() < 3) {
            String uriStr = imageCursor.getString(0);
            if(uriStr == null)
                continue;

                imgs.add(uriStr);
            }
        }
        return imgs;
    }
}
