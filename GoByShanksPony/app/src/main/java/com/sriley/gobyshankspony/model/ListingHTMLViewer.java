package com.sriley.gobyshankspony.model;

import android.webkit.JavascriptInterface;

import com.sriley.gobyshankspony.model.interfaces.ListingScrapeRequestListener;

import java.util.ArrayList;


public  class ListingHTMLViewer {

    private ListingScrapeRequestListener mListingScrapeRequestListener;

    ListingHTMLViewer(ListingScrapeRequestListener listener) {
        mListingScrapeRequestListener =listener;
    }


    @JavascriptInterface
    public void showHTML(String _html) {
       ArrayList<ListingProperty> properties= JSOUPManager.extractPropertiesFromHTMLString(_html);
        mListingScrapeRequestListener.onScrapeRequestComplete(properties);
    }
}
