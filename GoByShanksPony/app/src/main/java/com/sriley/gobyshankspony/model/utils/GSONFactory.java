package com.sriley.gobyshankspony.model.utils;


import com.google.gson.Gson;
import com.sriley.gobyshankspony.model.ListingProperty;

public class GSONFactory {

    public static String convertVenueToString(ListingProperty listingProperty) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(listingProperty);

        return jsonStr;
    }

    public static ListingProperty convertStringToVenue(String venueStr){
        Gson gson=new Gson();
        ListingProperty venue=gson.fromJson(venueStr,ListingProperty.class);

        return venue;
    }

}
