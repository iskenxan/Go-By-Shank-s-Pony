package com.sriley.gobyshankspony.model;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.sriley.gobyshankspony.model.interfaces.ScrapeRequestListener;

import java.util.ArrayList;


public  class HTMLViewer {

    private ScrapeRequestListener mScrapeRequestListener;

    HTMLViewer(ScrapeRequestListener listener) {
        mScrapeRequestListener=listener;
    }


    @JavascriptInterface
    public void showHTML(String _html) {
       ArrayList<ListingProperty> properties= JSOUPManager.extractPropertiesFromHTMLString(_html);
        mScrapeRequestListener.onScrapeRequestComplete(properties);
    }
}
