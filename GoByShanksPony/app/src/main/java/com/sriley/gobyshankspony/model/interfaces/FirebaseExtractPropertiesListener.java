package com.sriley.gobyshankspony.model.interfaces;


import com.sriley.gobyshankspony.model.ListingProperty;

import java.util.ArrayList;

public interface FirebaseExtractPropertiesListener {
    void onPropertiesExtracted(ArrayList<ListingProperty> favorites);
}
