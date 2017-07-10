package com.sriley.gobyshankspony.model;


import android.webkit.WebView;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.HTMLViewers.HTMLViewer;
import com.sriley.gobyshankspony.model.HTMLViewers.PropertyGalleryHTMLViewer;
import com.sriley.gobyshankspony.model.HTMLViewers.RentalListingHTMLViewer;
import com.sriley.gobyshankspony.model.HTMLViewers.PhoneHTMLViewer;
import com.sriley.gobyshankspony.model.HTMLViewers.SaleListingHTMLViewer;
import com.sriley.gobyshankspony.model.interfaces.ListingScrapeRequestListener;
import com.sriley.gobyshankspony.model.interfaces.PhoneScrapeRequestListener;
import com.sriley.gobyshankspony.model.interfaces.PropertyImageListRequestListener;

import java.io.IOException;

public class ScrapeManager  {



    public static void getRentalsList(WebView  webView, Place place, ListingScrapeRequestListener listener) {
            String rentalScrapeUrl=webView.getContext().getResources().getString(R.string.rent_listing_scrape_url);
            RentalListingHTMLViewer htmlViewer=new RentalListingHTMLViewer(listener);
            scrapePropertyList(webView, place,rentalScrapeUrl,htmlViewer);
    }



    public static void getSalesList(WebView webView,Place place,ListingScrapeRequestListener listener){
            String rentalScrapeUrl=webView.getContext().getResources().getString(R.string.sale_listing_scrape_url);
            SaleListingHTMLViewer htmlViewer=new SaleListingHTMLViewer(listener);
            scrapePropertyList(webView, place,rentalScrapeUrl,htmlViewer);
    }


    public static void getPropertyPhone(final  WebView webView, String detailsUrl, PhoneScrapeRequestListener listener){
        PhoneHTMLViewer jInterface = new PhoneHTMLViewer(listener);
        runWebview(jInterface,webView,detailsUrl);
    }


    public static void getPropertyImageList(WebView webView,String detailsUrl,PropertyImageListRequestListener listener){
        PropertyGalleryHTMLViewer htmlViewer=new PropertyGalleryHTMLViewer(listener);
        runWebview(htmlViewer,webView,detailsUrl);
    }


    private static void scrapePropertyList(final WebView webView, Place place,String requestUrl, HTMLViewer htmlViewer){
        String urlStr = requestUrl;
        urlStr = urlStr.replaceAll("ZIP", place.getZip());
        runWebview(htmlViewer,webView,urlStr);
    }


    private static void runWebview(HTMLViewer htmlViewer, final WebView webView, String urlStr){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(htmlViewer, "HtmlViewer");
        webView.setWebViewClient(new MyWebViewClient(webView));
        webView.loadUrl(urlStr);
    }

}


