package com.sriley.gobyshankspony.view.dialogs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sriley.gobyshankspony.R;

public class SignInErrorDialog extends DialogFragment implements View.OnClickListener {

    public static final int SIGN_UP_ERROR=0;
    public static final int LOGIN_ERROR=1;

    public static final String SIGN_UP_ERROR_MESSAGE="User with the given credentials already exists";
    public static final String LOGIN_ERROR_MESSAGE="User with the given credentials does not exist. Use 'SIGN UP' " +
            "option to create a new user";

    private TextView mDialogMessage;
    private Button mCloseButton;


    public static void displayDialog(FragmentManager fragmentManager){
        SignInErrorDialog signInErrorDialog=SignInErrorDialog.newInstance(SignInErrorDialog.SIGN_UP_ERROR);
        signInErrorDialog.show(fragmentManager,"error_dialog");
    }


    public SignInErrorDialog() {

    }


    public static SignInErrorDialog newInstance(int errorType) {
        SignInErrorDialog frag = new SignInErrorDialog();
        Bundle args = new Bundle();
        args.putInt("error_type", errorType);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_in_error_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDialogMessage = (TextView) view.findViewById(R.id.dialogMessageTextView);
        mCloseButton= (Button) view.findViewById(R.id.dialogCloseButton);

        int errorType=getArguments().getInt("error_type",0);

        if(errorType==SIGN_UP_ERROR)
            mDialogMessage.setText(SIGN_UP_ERROR_MESSAGE);
        else
            mDialogMessage.setText(LOGIN_ERROR_MESSAGE);

        mCloseButton.setOnClickListener(this);
        getDialog().setTitle("Authentication Error");
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
