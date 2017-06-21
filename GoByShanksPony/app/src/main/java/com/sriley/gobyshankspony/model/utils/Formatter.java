package com.sriley.gobyshankspony.model.utils;



public class Formatter {

    public static String convertEmailIntoUserkey(String email){
        String username=email.replace(".","_");

        return username;
    }

}
