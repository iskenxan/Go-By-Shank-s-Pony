package com.sriley.gobyshankspony.model.HTMLViewers;


import android.webkit.JavascriptInterface;

public abstract class HTMLViewer {

    @JavascriptInterface
    public abstract void showHTML(String html);
}
