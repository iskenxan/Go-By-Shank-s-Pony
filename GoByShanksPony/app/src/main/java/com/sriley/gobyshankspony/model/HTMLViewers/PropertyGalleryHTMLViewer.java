package com.sriley.gobyshankspony.model.HTMLViewers;


import android.webkit.JavascriptInterface;

import com.sriley.gobyshankspony.model.JSOUPManager;
import com.sriley.gobyshankspony.model.interfaces.PropertyImageListRequestListener;

import java.util.ArrayList;

public class PropertyGalleryHTMLViewer extends HTMLViewer {

    PropertyImageListRequestListener mListener;

    public PropertyGalleryHTMLViewer(PropertyImageListRequestListener listener){
        mListener=listener;
    }


    @JavascriptInterface
    public void showHTML(String _html) {
        ArrayList<String> imageUrl= JSOUPManager.extractGalleryImageUrls(_html);
        mListener.onImageUrlsExtracted(imageUrl);
    }
}
