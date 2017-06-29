package com.sriley.gobyshankspony.model;


public class ListingProperty extends Place {
    private String id;
    private String name;
    private String bedrooms;
    private String bathrooms;
    private String rent;
    private String imageUrl;
    private String detailsUrl;
    private String broker;


    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
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
