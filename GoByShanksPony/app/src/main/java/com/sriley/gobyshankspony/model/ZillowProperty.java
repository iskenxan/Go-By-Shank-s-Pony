package com.sriley.gobyshankspony.model;


public class ZillowProperty extends Place {
    private String id;
    private String name;
    private String zip;
    private String bedrooms;
    private String bathrooms;
    private String rent;

    public String getId() {
        return id;
    }


    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }
}
