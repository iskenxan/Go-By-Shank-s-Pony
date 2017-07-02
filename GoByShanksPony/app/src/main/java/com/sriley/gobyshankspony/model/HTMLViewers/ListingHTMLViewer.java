package com.sriley.gobyshankspony.model.HTMLViewers;

import android.webkit.JavascriptInterface;

import com.sriley.gobyshankspony.model.JSOUPManager;
import com.sriley.gobyshankspony.model.ListingProperty;
import com.sriley.gobyshankspony.model.interfaces.ListingScrapeRequestListener;

import java.util.ArrayList;


public  class ListingHTMLViewer extends HTMLViewer {

    private ListingScrapeRequestListener mListingScrapeRequestListener;

    public ListingHTMLViewer(ListingScrapeRequestListener listener) {
        mListingScrapeRequestListener =listener;
    }


    @JavascriptInterface
    public void showHTML(String _html) {
       ArrayList<ListingProperty> properties= JSOUPManager.extractPropertiesFromHTMLString(_html);
        mListingScrapeRequestListener.onScrapeRequestComplete(properties);
    }
}
