package com.sriley.gobyshankspony.model.utils;


import com.sriley.gobyshankspony.model.Venue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONHandler {






    public static JSONArray getJsonObjectsFromString(String serverStr) throws JSONException {
        JSONObject serverResponse= new JSONObject(serverStr);
        JSONObject response=serverResponse.getJSONObject("response");
        JSONArray venues=response.getJSONArray("venues");

        return venues;
    }



    public static ArrayList<Venue> extractVenues(JSONArray venues) throws JSONException {
        ArrayList<Venue> venueArrayList=new ArrayList<>();
        for(int i=0;i<20;i++){
            Venue venue=extractSingleVenue(venues.getJSONObject(i));
            venueArrayList.add(venue);
        }

        return venueArrayList;
    }



    private static Venue extractSingleVenue(JSONObject venueObj) throws JSONException {
        Venue venue=new Venue();

        venue= getVenueNameAndID(venueObj,venue);
        venue=getVenueAddress(venueObj,venue);

        return venue;
    }


    private static Venue getVenueNameAndID(JSONObject venueObj, Venue venue) throws JSONException {
        String name= venueObj.getString("name");
        venue.setName(name);

        return venue;
    }

    private static Venue getVenueAddress(JSONObject venueObj,Venue venue) throws JSONException {
        JSONObject locationObj=venueObj.getJSONObject("location");
        String address="";
        String city="";
        String state="";

        if(locationObj.has("address"))
            address=locationObj.getString("address");
        if(locationObj.has("city"))
            city=locationObj.getString("city");
        if(locationObj.has(("state")))
            state=locationObj.getString("state");

        venue.setAddress(address);
        venue.setCity(city);
        venue.setState(state);

        return venue;
    }
}
