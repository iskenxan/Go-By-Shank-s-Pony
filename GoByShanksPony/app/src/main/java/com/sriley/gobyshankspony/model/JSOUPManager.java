package com.sriley.gobyshankspony.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class JSOUPManager {


    public static String extractPropertyNumber(String htmlStr){
        Document doc = Jsoup.parse(htmlStr);
        Elements phoneNumberElement=doc.select("h3.ldp-rental-toll-free");

        if(phoneNumberElement.size()>0){
            String phoneNumber=phoneNumberElement.get(0).text().replace(" Call for more info","");
            return phoneNumber;
        }


        return null;
    }



    public static ArrayList<ListingProperty> extractPropertiesFromHTMLString(String htmlStr) {
        ArrayList<ListingProperty> properties = new ArrayList<>();
        Document doc = Jsoup.parse(htmlStr);
        Elements aparmentListings = doc.select("div.aspect-content");
        for (int i = 0; i < aparmentListings.size(); i++) {
            ListingProperty listingProperty = extractSingleProperty(aparmentListings.get(i));
            properties.add(listingProperty);
        }

        return properties;
    }


    private static ListingProperty extractSingleProperty(Element listingElement) {
        ListingProperty listingProperty = new ListingProperty();
        getListingName(listingProperty,listingElement);
        getImageUrl(listingProperty,listingElement);
        getRentPrice(listingElement,listingProperty);
        getAddress(listingElement,listingProperty);
        getMetadata(listingElement,listingProperty);
        getDetailsUrl(listingElement,listingProperty);
        getPropertyBroker(listingElement,listingProperty);


        return listingProperty;
    }


    private static void getListingName(ListingProperty listingProperty,Element listingElement){
        Elements nameElement = listingElement.select("span.listing-community");

        if(nameElement.size()>0){
            String listingName=nameElement.get(0).text();
            listingProperty.setName(listingName);
        }
        else
            listingProperty.setName("Property for rent");
    }



    private static void getImageUrl(ListingProperty listingProperty,Element listingElement){
        Element imageElement = listingElement.select("img.js-srp-listing-photos").get(0);
        String imageUrl = imageElement.attr("data-src");
        listingProperty.setImageUrl(imageUrl);
    }


    private static void getRentPrice(Element listingElement,ListingProperty listingProperty){
        Element priceElement = listingElement.select("div.srp-item-price").get(0);
        String price = priceElement.text();
        listingProperty.setPrice(price.replace("/month","").replace("$","").replace(",",""));
    }



    private static void getAddress(Element listingElement,ListingProperty listingProperty){
        Element addressElement = listingElement.select("span.listing-street-address").get(0);
        Element cityElement = listingElement.select("span.listing-city").get(0);
        Element stateElement = listingElement.select("span.listing-region").get(0);
        String address = addressElement.text();
        String city = cityElement.text();
        String state = stateElement.text();

        listingProperty.setAddress(address);
        listingProperty.setCity(city);
        listingProperty.setState(state);
    }



    private static void getMetadata(Element listingElement,ListingProperty listingProperty){
        Elements bedsElement=listingElement.select("li[data-label='property-meta-beds']");
        if(bedsElement.size()>0)
            listingProperty.setBedrooms(bedsElement.get(0).text().replace(" bd",""));
        else
            listingProperty.setBedrooms("n/a");

        Elements bathsElement=listingElement.select("li[data-label='property-meta-baths']");
        if(bathsElement.size()>0)
            listingProperty.setBathrooms(bathsElement.get(0).text().replace(" ba",""));
        else
            listingProperty.setBathrooms("n/a");
    }

    private static void getDetailsUrl(Element listingElement,ListingProperty listingProperty){
        Element detailsContainer=listingElement.select("div.srp-item-ldp-link").get(0);
        Element linkElement=detailsContainer.select("a").get(0);
        String relativeLink=linkElement.attr("href");
        String fullLink="http://www.realtor.com"+relativeLink;

        listingProperty.setDetailsUrl(fullLink);
    }


    private static void getPropertyBroker(Element listingElement,ListingProperty listingProperty){
        Elements brokerItem=listingElement.select("span.srp-item-broker-text-sm");
        if(brokerItem.size()>0){
            String broker=brokerItem.get(0).text();
            listingProperty.setBroker(broker);
        }
    }

}
