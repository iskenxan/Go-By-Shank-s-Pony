package com.sriley.gobyshankspony.view.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sriley.gobyshankspony.R;


public class ProgressBarDialog extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.progress_bar_dialog,container,false);
        setCancelable(false);
        return view;
    }



}
