package com.sriley.gobyshankspony.model.utils;

import android.os.AsyncTask;

import com.sriley.gobyshankspony.model.interfaces.TimerListener;


public class AsyncTimerTask extends AsyncTask<TimerListener,Void,Void> {


    @Override
    protected Void doInBackground(TimerListener... timerListeners) {
        try {
            Thread.sleep(1000);
            TimerListener[] listeners=timerListeners;
            listeners[0].onTimerComplete();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
