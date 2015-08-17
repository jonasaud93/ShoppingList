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

public class ShoppingGallery extends AsyncTask<Void, Void, List<Bitmap>> {
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
    protected List<Bitmap> doInBackground(Void... params) {

        List<Bitmap> imgs = new ArrayList<>();
        while(imageCursor.moveToNext()){
            if(imgs.size() < 3) {
            String uriStr = imageCursor.getString(0);
            Uri uri = null;
            if(uriStr == null)
                continue;

                try {
                uri = Uri.parse("file://" + uriStr);
            } catch (Exception e) {
                Log.e(LOG_TAG, "problem with the image loading: " + e);
            }
                if(uri == null)
                    continue;
                Bitmap bm = null;
                try{
                    bm = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                    imgs.add(bm);
                } catch (FileNotFoundException e) {
                    Log.e(LOG_TAG, "Bitmap not found: " + e);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Bitmap IO error: " + e);

                //if(bm == null)
                //    continue;



                }
            }
        }
        return imgs;
    }
}
