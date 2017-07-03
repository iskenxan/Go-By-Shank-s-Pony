package com.sriley.gobyshankspony.model.interfaces;


import com.sriley.gobyshankspony.model.FirebaseManagerListingRecord;

import java.util.ArrayList;

public interface FirebaseExtractManagerListingRecordsListener {
    void onListingRecordsExtracted(ArrayList<FirebaseManagerListingRecord> listingRecords);
}
