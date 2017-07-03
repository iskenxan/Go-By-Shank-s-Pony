package com.sriley.gobyshankspony.model.utils;


import android.app.Activity;
import android.content.Intent;

public class GalleryBrowser {

    public static final int SELECT_PICTURE_INTENT=4;

    public static void startGalleryBrowsingIntent(Activity activity){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(galleryIntent, SELECT_PICTURE_INTENT);
    }
}
