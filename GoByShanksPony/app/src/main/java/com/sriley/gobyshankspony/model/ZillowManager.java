package com.sriley.gobyshankspony.model;


import android.content.Context;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.interfaces.ListingScrapeRequestListener;
import com.sriley.gobyshankspony.model.utils.XMLHandler;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class ZillowManager extends HttpRequestManager {






    public static void getNearbyPropertiesForSingleVenue(Context context, Place place, ListingScrapeRequestListener listener){
        ArrayList<ListingProperty> zillowProperties= getNearbyPropertiesForSingleVenue(context,place);

        listener.onScrapeRequestComplete(zillowProperties);
    }


    public static ArrayList<ListingProperty> getNearbyPropertiesForSingleVenue(Context context, Place place){
        try {
               ArrayList<ListingProperty> propertiesAtUserLocation= getZillowPropertyListAtLocation(context,place);

            if(propertiesAtUserLocation!=null){
                ArrayList<ListingProperty> nearbyProperties=new ArrayList<>();
                nearbyProperties.addAll(propertiesAtUserLocation);
                 ArrayList<ListingProperty> neightborProperties= requestPropertyNeighbors(context,propertiesAtUserLocation);
                if(neightborProperties!=null&&neightborProperties.size()>0)
                    nearbyProperties.addAll(neightborProperties);

                return nearbyProperties;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return null;
    }




    public static ArrayList<ListingProperty> getZillowPropertyListAtLocation(Context context, Place place) throws
            IOException, SAXException, ParserConfigurationException {
        String searchUrl= createZipIdRequestUrl(context,place);
        HttpURLConnection httpURLConnection=getHttpUrlConnection(searchUrl);

        int responseCode=httpURLConnection.getResponseCode();

        if(responseCode==HttpURLConnection.HTTP_OK){
           Element docEle=XMLHandler.getRootElement(httpURLConnection);

            if(docEle.getChildNodes().getLength()>2){
                ArrayList<ListingProperty> zillowProperties =XMLHandler.extractPropertiesFromUserLocation(docEle);

                return zillowProperties;
            }
        }

        return null;
    }



    private static ArrayList<ListingProperty> requestPropertyNeighbors(Context context, ArrayList<ListingProperty> userLocationProperties) throws IOException, ParserConfigurationException, SAXException {

        ArrayList<ListingProperty> allNeightbors=new ArrayList<>();

        for(ListingProperty property:userLocationProperties){
            ArrayList<ListingProperty> propertyNeightbors=requestSinglePropertyNeightbors(context,property);
            if(propertyNeightbors!=null)
                allNeightbors.addAll(propertyNeightbors);
        }

        return allNeightbors;
    }



    private static ArrayList<ListingProperty> requestSinglePropertyNeightbors(Context context, ListingProperty listingProperty) throws IOException, ParserConfigurationException, SAXException {
        String searchUrl= createSearchRequestUrl(context, listingProperty.getId());
        HttpURLConnection httpURLConnection=getHttpUrlConnection(searchUrl);

        int responseCode=httpURLConnection.getResponseCode();

        if(responseCode==HttpURLConnection.HTTP_OK){
            Element docEle=XMLHandler.getRootElement(httpURLConnection);
            if(docEle.getChildNodes().getLength()>2){
                ArrayList<ListingProperty> propertyDetails=XMLHandler.extractPropertyDetails(docEle);
                return propertyDetails;
            }
        }

        return null;
    }








    private static String createSearchRequestUrl(Context context,String zipId){
        String urlSrc=context.getResources().getString(R.string.zillow_request_api_url);
        urlSrc=urlSrc.replace("ZIPID",zipId);

        return urlSrc;
    }


    private static String createZipIdRequestUrl(Context context, Place place) throws UnsupportedEncodingException {
        String urlSrc=context.getResources().getString(R.string.zillow_zipid_api_url);

        urlSrc=urlSrc.replace("ADDRESS",place.getAddress());
        urlSrc=urlSrc.replace("CITY",place.getCity());
        urlSrc=urlSrc.replace("STATE",place.getState());
        urlSrc= urlSrc.replace(" ","%20");

        return urlSrc;
    }



    private static String createPropertyDetailsRequestUrl(Context context,String zpId){
        String urlSrc=context.getResources().getString(R.string.zillow_property_details_url);
        urlSrc=urlSrc.replace("ZPID",zpId);

        return urlSrc;
    }



}
