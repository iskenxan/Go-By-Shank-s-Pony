package com.sriley.gobyshankspony.model.interfaces;


import com.sriley.gobyshankspony.model.ListingProperty;

import java.util.ArrayList;

public interface ScrapeRequestListener {
    void onScrapeRequestComplete(ArrayList<ListingProperty> listingProperty);
}
