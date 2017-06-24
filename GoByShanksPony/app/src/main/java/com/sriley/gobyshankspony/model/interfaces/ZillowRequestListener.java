package com.sriley.gobyshankspony.model.interfaces;


import com.sriley.gobyshankspony.model.ZillowProperty;

import java.util.ArrayList;

public interface ZillowRequestListener {
    void onZillowRequestCompleted(ArrayList<ZillowProperty> zillowProperty);
}
