package com.sriley.gobyshankspony.model;

import com.sriley.gobyshankspony.model.utils.Formatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class JSOUPManager {


    public static ArrayList<String> extractGalleryImageUrls(String htmlStr){
        ArrayList<String> imageUrls=new ArrayList<>();

        Document doc = Jsoup.parse(htmlStr);
        Elements galleryItemElements=doc.select(".owl-lazy");

        for (Element container:galleryItemElements){
            String imageUrl=extractImageUrl(container);
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }


    private static String extractImageUrl(Element imageElement){

        String url=imageElement.attr("data-src");

        return url;
    }


    public static String extractPropertyNumber(String htmlStr){
        Document doc = Jsoup.parse(htmlStr);
        Elements phoneNumberElement=doc.select("h3.ldp-rental-toll-free");

        if(phoneNumberElement.size()>0){
            String phoneNumber=phoneNumberElement.get(0).text().replace(" Call for more info","");
            return phoneNumber;
        }


        return null;
    }



    public static ArrayList<ListingProperty> extractPropertiesFromHTMLString(String htmlStr,String propertyType) {
        ArrayList<ListingProperty> properties = new ArrayList<>();
        Document doc = Jsoup.parse(htmlStr);
        Elements apartmentListings = doc.select("div.srp-item");
        for (int i = 0; i < apartmentListings.size(); i++) {
            ListingProperty listingProperty = extractSingleProperty(apartmentListings.get(i),propertyType);
            properties.add(listingProperty);
        }

        return properties;
    }


    private static ListingProperty extractSingleProperty(Element listingElement,String propertyType) {
        ListingProperty listingProperty = new ListingProperty();
        getListingName(listingProperty,listingElement,propertyType);
        getImageUrl(listingProperty,listingElement);
        getPrice(listingElement,listingProperty,propertyType);
        getAddress(listingElement,listingProperty);
        getMetadata(listingElement,listingProperty);
        getDetailsUrl(listingElement,listingProperty);
        getPropertyBroker(listingElement,listingProperty);


        return listingProperty;
    }


    private static void getListingName(ListingProperty listingProperty,Element listingElement,String propertyType){
        Elements nameElement = listingElement.select("span.listing-community");

        if(nameElement.size()>0){
            String listingName=nameElement.get(0).text();
            listingProperty.setName(listingName);
        }
        else{
            if(propertyType.equals(Formatter.RENTAL))
                listingProperty.setName("Property for rent");
            else
                listingProperty.setName("Property for sale");
        }
    }



    private static void getImageUrl(ListingProperty listingProperty,Element listingElement){
        Element imageElement = listingElement.select("img.js-srp-listing-photos").get(0);
        String imageUrl = imageElement.attr("data-src");
        listingProperty.setImageUrl(imageUrl);
    }


    private static void getPrice(Element listingElement, ListingProperty listingProperty, String propertyType){
        Element priceElement = listingElement.select("div.srp-item-price").get(0);
        String price = priceElement.text();
        listingProperty.setPrice(price.replace("/month","").replace("$","").replace(",",""));
        setPropertyType(propertyType,listingProperty);

        price=listingProperty.getPrice();
        price=price.replaceAll("No Estimate Available/m","n/a");
        price=price.replaceAll("No Estimate Available","n/a");
        listingProperty.setPrice(price);
    }


    
    private static void setPropertyType(String propertyType,ListingProperty listingProperty){
        if(propertyType.equals(Formatter.RENTAL)){
            listingProperty.setPropertyType(Formatter.RENTAL);
            listingProperty.setPrice(listingProperty.getPrice()+"/m");
        }
        else
            listingProperty.setPropertyType(Formatter.SALE);
    }



    private static void getAddress(Element listingElement,ListingProperty listingProperty){
            Element addressElement = listingElement.select(".listing-street-address").get(0);
            Element cityElement = listingElement.select(".listing-city").get(0);
            Element stateElement = listingElement.select(".listing-region").get(0);
            Element postalElement=listingElement.select(".listing-postal").get(0);

            String address = addressElement.text();
            address= Formatter.removeFirebaseInvalidPathChars(address);

            String city = cityElement.text();
            String state = stateElement.text();
            String postal=postalElement.text();

            listingProperty.setAddress(address);
            listingProperty.setCity(city);
            listingProperty.setState(state);
            listingProperty.setZip(postal);
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
        listingProperty.setManagerUsername(null);
    }

}
