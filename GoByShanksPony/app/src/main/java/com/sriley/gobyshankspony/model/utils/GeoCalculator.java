package com.sriley.gobyshankspony.model.utils;


import android.location.Location;

import java.util.ArrayList;

public class GeoCalculator {

    public static final double NORTH=0.0;
    public static final double SOUTH=180.0;
    public static final double WEST=270.0;
    public static final double EAST=90.0;



    public static ArrayList<Location> getListOfNearbyLocations(Location userLocation, double distance){
        ArrayList<Location> locations=new ArrayList<>();
        Location nearbyNorthLocation=GeoCalculator.calculateChangedLocation(userLocation,GeoCalculator.NORTH,distance);
        Location nearbySouthLocation=GeoCalculator.calculateChangedLocation(userLocation,GeoCalculator.SOUTH,distance);
        Location nearbyWestLocation=GeoCalculator.calculateChangedLocation(userLocation,GeoCalculator.WEST,distance);
        Location nearbyEastLocation=GeoCalculator.calculateChangedLocation(userLocation,GeoCalculator.EAST,distance);

        locations.add(nearbyNorthLocation);
        locations.add(nearbySouthLocation);
        locations.add(nearbyWestLocation);
        locations.add(nearbyEastLocation);

        return locations;
    }


    public static Location calculateChangedLocation(Location userLocation, double direction,double distanceInKM){
        double dist = distanceInKM/6371.0;
        double brng = Math.toRadians(direction);
        double lat1 = Math.toRadians(userLocation.getLatitude());
        double lon1 = Math.toRadians(userLocation.getLongitude());

        double lat2 = Math.asin( Math.sin(lat1)*Math.cos(dist) + Math.cos(lat1)*Math.sin(dist)*Math.cos(brng) );
        double a = Math.atan2(Math.sin(brng)*Math.sin(dist)*Math.cos(lat1), Math.cos(dist)-Math.sin(lat1)*Math.sin(lat2));
        System.out.println("a = " +  a);
        double lon2 = lon1 + a;

        lon2 = (lon2+ 3*Math.PI) % (2*Math.PI) - Math.PI;

        Location newLocation=new Location("dummy_provider");
        newLocation.setLatitude(Math.toDegrees(lat2));
        newLocation.setLongitude(Math.toDegrees(lon2));

        return newLocation;
    }


}
