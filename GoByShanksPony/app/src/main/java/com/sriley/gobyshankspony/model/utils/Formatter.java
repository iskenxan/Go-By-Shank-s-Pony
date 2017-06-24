package com.sriley.gobyshankspony.model.utils;


import com.sriley.gobyshankspony.model.Venue;
import com.sriley.gobyshankspony.model.ZillowProperty;

import java.util.ArrayList;

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


    public static void removeZillowPropertyDuplicates(ArrayList<ZillowProperty> properties){

        for (int i=0;i<properties.size();i++){
            ZillowProperty zillowProperty=properties.get(i);
            for (int x=i+1;x<properties.size();x++){
                ZillowProperty temp=properties.get(x);
                if(zillowProperty.getId().equals(temp.getId()))
                    properties.remove(temp);
            }
        }
    }



}
