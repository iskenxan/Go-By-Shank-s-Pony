package com.sriley.gobyshankspony.model.utils;


import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;


public class GlideManager {





    public static void loadRoundedProfilePicture(final ImageView imageView, int resourceId) {
        Glide.with(imageView.getContext()).load(resourceId).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                GlideManager.makePictureRounded(resource,imageView);
            }
        });
    }


    public static void loadRoundedProfilePicture(final ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                GlideManager.makePictureRounded(resource,imageView);
            }
        });
    }


    private static void makePictureRounded(Bitmap resource, ImageView imageView){
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), resource);
        circularBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(circularBitmapDrawable);
    }
}
