package com.sriley.gobyshankspony.model;


import android.content.Context;
import android.webkit.WebView;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.interfaces.ScrapeRequestListener;
import com.sriley.gobyshankspony.model.utils.MyWebViewClient;

import java.io.IOException;

public class ScrapeManager extends HttpRequestManager {



    public static void getZillowRentals(Context context, Place place, ScrapeRequestListener listener) {
        try {
            scrapeZillow(context, place,listener);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void scrapeZillow(final Context context, final Place place,ScrapeRequestListener listener) throws IOException {
        String urlStr = context.getResources().getString(R.string.listing_scrape_url);
        urlStr = urlStr.replaceAll("ZIP", place.getZip());


        final WebView webView = new WebView(context);

        webView.getSettings().setJavaScriptEnabled(true);
        HTMLViewer jInterface = new HTMLViewer(listener);
        webView.addJavascriptInterface(jInterface, "HtmlViewer");
        webView.setWebViewClient(new MyWebViewClient(webView));
        webView.loadUrl(urlStr);
    }



}


