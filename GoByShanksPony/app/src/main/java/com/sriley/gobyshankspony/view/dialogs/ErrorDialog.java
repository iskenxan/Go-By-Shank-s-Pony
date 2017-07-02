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

public class ErrorDialog extends DialogFragment implements View.OnClickListener {


    public static final String BROKER_ERROR_MESSAGE ="No phone number was found for the property.For more information about " + "the property contact ";
    public static final String SIGN_IN_ERROR_MESSAGE="Error occured while trying to\n sign in with your account.Please try again later";

    private TextView mDialogMessage;
    private Button mCloseButton;


    public static void displayDialog(FragmentManager fragmentManager,String message){
        ErrorDialog errorDialog = ErrorDialog.newInstance(message);
        errorDialog.show(fragmentManager,"error_dialog");
    }


    public ErrorDialog() {

    }


    public static ErrorDialog newInstance(String broker) {
        ErrorDialog frag = new ErrorDialog();
        Bundle args = new Bundle();
        args.putString("message", broker);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.error_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDialogMessage = (TextView) view.findViewById(R.id.dialogMessageTextView);
        mCloseButton= (Button) view.findViewById(R.id.dialogCloseButton);

        String  message=getArguments().getString("message","");

        mDialogMessage.setText(message);


        mCloseButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        dismiss();
    }
}
