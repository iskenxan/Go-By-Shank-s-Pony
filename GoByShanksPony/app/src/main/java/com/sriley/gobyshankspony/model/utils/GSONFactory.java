package com.sriley.gobyshankspony.model.utils;


import com.google.gson.Gson;
import com.sriley.gobyshankspony.model.Venue;
import com.sriley.gobyshankspony.model.ZillowProperty;

public class GSONFactory {

    public static String convertVenueToString(ZillowProperty zillowProperty) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(zillowProperty);

        return jsonStr;
    }

    public static ZillowProperty convertStringToVenue(String venueStr){
        Gson gson=new Gson();
        ZillowProperty venue=gson.fromJson(venueStr,ZillowProperty.class);

        return venue;
    }

}
