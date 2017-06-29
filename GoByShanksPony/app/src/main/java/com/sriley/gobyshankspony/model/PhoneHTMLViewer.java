package com.sriley.gobyshankspony.model;

import android.webkit.JavascriptInterface;

import com.sriley.gobyshankspony.model.interfaces.PhoneScrapeRequestListener;


public class PhoneHTMLViewer {
    private PhoneScrapeRequestListener mPhoneScrapeRequestListener;

    PhoneHTMLViewer(PhoneScrapeRequestListener listener) {
        mPhoneScrapeRequestListener =listener;
    }


    @JavascriptInterface
    public void showHTML(String _html) {
        String phoneNumber =JSOUPManager.extractPropertyNumber(_html);
            mPhoneScrapeRequestListener.onPhoneRetrieved(phoneNumber);
    }
}
