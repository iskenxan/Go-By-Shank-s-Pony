package com.sriley.gobyshankspony.model.utils;


import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.sriley.gobyshankspony.R;

public class RadioButtonSwitcher implements CompoundButton.OnCheckedChangeListener {


    public enum ButtonType{
        BROCKERS,
        RENTERS,
        LANDLORDS
    }

    private RadioButton mBrokerRadioButton,mRenterRadioButton,mLandlordRadioButton;
    private ButtonType mCurrentButtonType;


    public RadioButtonSwitcher(RadioButton brokerButton,RadioButton renterButton,RadioButton landlordButton){
        mBrokerRadioButton=brokerButton;
        mLandlordRadioButton=landlordButton;
        mRenterRadioButton=renterButton;

        mBrokerRadioButton.setOnCheckedChangeListener(this);
        mRenterRadioButton.setOnCheckedChangeListener(this);
        mLandlordRadioButton.setOnCheckedChangeListener(this);
    }



    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        if(checked){
            int id=compoundButton.getId();
            if(id== R.id.SignUpRentersRadioButton){
             uncheckOtherTwoButtons(mBrokerRadioButton,mLandlordRadioButton);
            }
            else if(id==R.id.SignUpBrokersRadioButton){
                uncheckOtherTwoButtons(mLandlordRadioButton,mRenterRadioButton);
                mCurrentButtonType=ButtonType.BROCKERS;
            }
            else if(id==R.id.SignUpLandlordRadioButton){
                uncheckOtherTwoButtons(mBrokerRadioButton,mRenterRadioButton);
                mCurrentButtonType=ButtonType.LANDLORDS;
            }
        }

    }


    private void uncheckOtherTwoButtons(RadioButton firstButton,RadioButton secondButton){
        firstButton.setChecked(false);
        secondButton.setChecked(false);
    }



    public ButtonType getCurrentCheckedButtonType(){
        return mCurrentButtonType;
    }




}
