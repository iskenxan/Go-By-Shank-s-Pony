package com.sriley.gobyshankspony.model.HTMLViewers;

import android.webkit.JavascriptInterface;

import com.sriley.gobyshankspony.model.JSOUPManager;
import com.sriley.gobyshankspony.model.interfaces.PhoneScrapeRequestListener;


public class PhoneHTMLViewer extends HTMLViewer {
    private PhoneScrapeRequestListener mPhoneScrapeRequestListener;

    public PhoneHTMLViewer(PhoneScrapeRequestListener listener) {
        mPhoneScrapeRequestListener =listener;
    }


    @JavascriptInterface
    public void showHTML(String _html) {
        String phoneNumber = JSOUPManager.extractPropertyNumber(_html);
            mPhoneScrapeRequestListener.onPhoneRetrieved(phoneNumber);
    }
}
