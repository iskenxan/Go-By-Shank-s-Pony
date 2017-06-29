package com.sriley.gobyshankspony.model;


import java.util.ArrayList;

public class ZillowApartment extends ListingProperty {
    private ArrayList<ApartmentUnit> mApartmentUnits=new ArrayList<>();

    public void addAparmentUnit(ApartmentUnit unit){
        mApartmentUnits.add(unit);
    }

    public ArrayList<ApartmentUnit> getApartmentUnits(){
        return mApartmentUnits;
    }


}
