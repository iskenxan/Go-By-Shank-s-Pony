package com.sriley.gobyshankspony.model.utils;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.sriley.gobyshankspony.model.Place;
import com.sriley.gobyshankspony.model.Venue;
import com.sriley.gobyshankspony.model.ListingProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Formatter {

    public static String convertEmailIntoUserkey(String email){
        String username=email.replace(".","_");

        return username;
    }


    public static String formatUsertypeForDatabase(String spinnerItem){
        if(spinnerItem.equals("Renter"))
            return "renter";
        else if(spinnerItem.equals("Landlord or Manager"))
            return "landlord";
        else if(spinnerItem.equals("Agent or Broker"))
            return "agent";
        else
            return "";
    }


    public static ArrayList<Venue> removePartialAddressVenues(ArrayList<Venue> venues){
        ArrayList<Venue> fullAddressVenues=new ArrayList<>();
        for (Venue venue:venues){
            if(!venue.getAddress().equals("")&&!venue.getCity().equals("")&&!venue.getState().equals(""))
                fullAddressVenues.add(venue);
        }

        return fullAddressVenues;
    }



    public static Place getPlaceFromLocation(Context context, Location location){
        Address address=getAddressFromLocation(context,location);

        Place place=new Place();
        place.setAddress(address.getAddressLine(0));
        String state=address.getAdminArea();
        state=getStateAbreviation(state);

        place.setState(state);
        place.setCity(address.getLocality());
        place.setZip(address.getPostalCode());
        return place;
    }



    public static String getStateAbreviation(String state){
        HashMap<String,String> states=StatesHashmap.getAbreviationHashMap();

        return states.get(state);
    }




    public static Address getAddressFromLocation(Context context,Location location){
        Geocoder geoCoder = new Geocoder(context);
        List<Address> matches = null;
        try {
            matches = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
        } catch (IOException e) {
        }
        Address address=matches.get(0);

        return address;
    }




    public static void removePropertyDuplicates(ArrayList<ListingProperty> properties){

        for (int i=0;i<properties.size();i++){
            ListingProperty listingProperty =properties.get(i);
            for (int x=i+1;x<properties.size();x++){
                ListingProperty temp=properties.get(x);
                if(listingProperty.getAddress().equals(temp.getAddress()))
                    properties.remove(temp);
            }
        }
    }



}
