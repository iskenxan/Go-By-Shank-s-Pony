package com.sriley.gobyshankspony.model.interfaces;

import com.sriley.gobyshankspony.model.Venue;

import java.util.ArrayList;



public interface FoursquareRequestListener {
    void onFoursquareReturn(ArrayList<Venue> venues);
}
