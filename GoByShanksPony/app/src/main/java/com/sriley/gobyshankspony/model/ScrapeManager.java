package com.sriley.gobyshankspony.model;


import android.webkit.WebView;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.HTMLViewers.HTMLViewer;
import com.sriley.gobyshankspony.model.HTMLViewers.ListingHTMLViewer;
import com.sriley.gobyshankspony.model.HTMLViewers.PhoneHTMLViewer;
import com.sriley.gobyshankspony.model.interfaces.ListingScrapeRequestListener;
import com.sriley.gobyshankspony.model.interfaces.PhoneScrapeRequestListener;

import java.io.IOException;

public class ScrapeManager  {



    public static void getRentalsList(WebView  webView, Place place, ListingScrapeRequestListener listener) {
        try {
            scrapePropertyList(webView, place,listener);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getPropertyPhone(final  WebView webView, String detailsUrl, PhoneScrapeRequestListener listener){
        try {
            scrapePhoneNumberData(webView,detailsUrl,listener);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void scrapePropertyList(final WebView webView, Place place, ListingScrapeRequestListener listener) throws IOException {
        String urlStr = webView.getContext().getResources().getString(R.string.listing_scrape_url);
        urlStr = urlStr.replaceAll("ZIP", place.getZip());
        scrapeListingData(webView,urlStr,listener);
    }





    private static void scrapeListingData(final WebView webView, String urlStr, ListingScrapeRequestListener listener) throws IOException {
        ListingHTMLViewer jInterface = new ListingHTMLViewer(listener);
        runWebview(jInterface,webView,urlStr);
    }



    private static void scrapePhoneNumberData(final WebView webView, String urlStr, PhoneScrapeRequestListener listener) throws IOException {
        PhoneHTMLViewer jInterface = new PhoneHTMLViewer(listener);
       runWebview(jInterface,webView,urlStr);
    }


    private static void runWebview(HTMLViewer htmlViewer, final WebView webView, String urlStr){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(htmlViewer, "HtmlViewer");
        webView.setWebViewClient(new MyWebViewClient(webView));
        webView.loadUrl(urlStr);
    }

}


