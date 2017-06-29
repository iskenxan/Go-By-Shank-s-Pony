package com.sriley.gobyshankspony.model;


import android.webkit.WebView;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.interfaces.ListingScrapeRequestListener;
import com.sriley.gobyshankspony.model.interfaces.PhoneScrapeRequestListener;

import java.io.IOException;

public class ScrapeManager extends HttpRequestManager {



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
        webView.getSettings().setJavaScriptEnabled(true);
        ListingHTMLViewer jInterface = new ListingHTMLViewer(listener);
        webView.addJavascriptInterface(jInterface, "HtmlViewer");
        webView.setWebViewClient(new MyWebViewClient(webView));
        webView.loadUrl(urlStr);
    }


    private static void scrapePhoneNumberData(final WebView webView, String urlStr, PhoneScrapeRequestListener listener) throws IOException {
        webView.getSettings().setJavaScriptEnabled(true);
        PhoneHTMLViewer jInterface = new PhoneHTMLViewer(listener);
        webView.addJavascriptInterface(jInterface, "HtmlViewer");
        webView.setWebViewClient(new MyWebViewClient(webView));
        webView.loadUrl(urlStr);
    }


}


