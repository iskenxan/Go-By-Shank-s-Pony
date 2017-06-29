package com.sriley.gobyshankspony.model.utils;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MyWebViewClient extends WebViewClient {
    boolean mTimeout;
    WebView mWebView;

    public MyWebViewClient(WebView webView) {
        mWebView=webView;
        mTimeout = true;
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        mTimeout=false;
        mWebView.loadUrl("javascript:window.HtmlViewer.showHTML ('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
    }
}
