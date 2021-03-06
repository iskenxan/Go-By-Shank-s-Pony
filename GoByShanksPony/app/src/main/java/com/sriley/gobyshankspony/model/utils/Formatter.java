package com.sriley.gobyshankspony.model.utils;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.Place;
import com.sriley.gobyshankspony.model.Venue;
import com.sriley.gobyshankspony.model.ListingProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Formatter {
    public static final String USERTYPE_RENTER="renter";
    public static final String USERTYPE_LANDLORD="landlord";
    public static final String USERTYPE_AGENT="agent";
    public static final String RENTAL="Rental";
    public static final String SALE="For Sale";


    public static String getUserFirstName(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String fullName=user.getDisplayName();
        String firstName=fullName.split(" ")[0];

        return firstName;
    }


    public static List<String> getRoomNumbersList(){
        List<String> roomNumbers=new ArrayList<>();
        roomNumbers.add("0");
        roomNumbers.add("1");
        roomNumbers.add("2");
        roomNumbers.add("3");
        roomNumbers.add("4");
        roomNumbers.add("5");

        return roomNumbers;
    }


    public static List<String> getPropertyTypeList(){
        List<String> properties=new ArrayList<>();
        properties.add(RENTAL);
        properties.add(SALE);

        return properties;
    }


    public static void setupSpinner(List<String> collection, Spinner spinner){
        ArrayAdapter<String> adapter=new ArrayAdapter<>(spinner.getContext(), R.layout.spinner_list_item, collection);
        spinner.setAdapter(adapter);
    }


    public static void setupSpinner(String[] collection, Spinner spinner){
        ArrayAdapter<String> adapter=new ArrayAdapter<>(spinner.getContext(), R.layout.spinner_list_item, collection);
        spinner.setAdapter(adapter);
    }



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



    public static String removeFirebaseInvalidPathChars(String string){
        String formattedStr=string.replaceAll("\\.","").replaceAll("#"," ").replaceAll("$","").replaceAll("\\[", "")
                .replaceAll("]","").replaceAll("/","-").replaceAll("\\\\","");

        return formattedStr;
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
