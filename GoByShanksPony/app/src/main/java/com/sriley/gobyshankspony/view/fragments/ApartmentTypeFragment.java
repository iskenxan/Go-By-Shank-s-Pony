package com.sriley.gobyshankspony.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sriley.gobyshankspony.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ApartmentTypeFragment extends Fragment {

    public static final String BEDROOM_NUM_ARGS="number_of_bedrooms";

    @BindView(R.id.ApartmentTypeImageView)ImageView mApartmentTypeImageView;
    @BindView(R.id.ApartmentTypeTextView)TextView mApartmentTypeTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_apartment_type,container,false);
        ButterKnife.bind(this,view);
        popualteViews();

        return view;
    }


    public  void popualteViews(){
        int bedroomNumImageId=getArguments().getInt(BEDROOM_NUM_ARGS,R.drawable.one_bed);
        mApartmentTypeImageView.setImageResource(bedroomNumImageId);
        setText(bedroomNumImageId);
    }


    public void setText(int imageId){
        if(imageId==R.drawable.one_bed)
            mApartmentTypeTextView.setText("One Bedroom");
        else if(imageId==R.drawable.two_bed)
            mApartmentTypeTextView.setText("Two Bedroom");
        else
            mApartmentTypeTextView.setText("Three Bedroom");
    }


}
