package com.example.jonas.shoppinglist.processes;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

public class ShoppingGallery extends AsyncTask<Void, Void, Void> {
    private Activity activity;
    private Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
    private String[] projection = {MediaStore.Images.Thumbnails.DATA};
    private Cursor imageCursor;

    public ShoppingGallery(Activity activity){
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {

        imageCursor = activity.getContentResolver().query(uri, projection, null, null, null);

        int imageIdx = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA);

        while(imageCursor.moveToNext()){
            String bytestream = imageCursor.getString(imageIdx);
        }

        return null;
    }
}
