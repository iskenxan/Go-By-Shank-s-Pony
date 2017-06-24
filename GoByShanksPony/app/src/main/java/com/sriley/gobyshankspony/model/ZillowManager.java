package com.sriley.gobyshankspony.model;


import android.content.Context;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.interfaces.ZillowPhotoUrlRequestListener;
import com.sriley.gobyshankspony.model.interfaces.ZillowRequestListener;
import com.sriley.gobyshankspony.model.utils.XMLHandler;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class ZillowManager extends HttpRequestManager {


    public static void getNearbyPropertiesForVenueList(Context context, ArrayList<Place> places, ZillowRequestListener
            listener){
        ArrayList<ZillowProperty> allProperties=new ArrayList<>();
        for (Place place:places){
            ArrayList<ZillowProperty> venueNearbyProperties= getNearbyPropertiesForSingleVenue(context,place);
            if(venueNearbyProperties!=null)
                allProperties.addAll(venueNearbyProperties);
        }
        listener.onZillowRequestCompleted(allProperties);
    }


    public static void getNearbyPropertiesForSingleVenue(Context context, Place place, ZillowRequestListener listener){
        ArrayList<ZillowProperty> zillowProperties= getNearbyPropertiesForSingleVenue(context,place);

        listener.onZillowRequestCompleted(zillowProperties);
    }


    public static ArrayList<ZillowProperty> getNearbyPropertiesForSingleVenue(Context context, Place place){
        try {
               ArrayList<ZillowProperty> propertiesAtUserLocation= getZillowPropertyListAtLocation(context,place);

            if(propertiesAtUserLocation!=null){
                ArrayList<ZillowProperty> nearbyProperties=new ArrayList<>();
                nearbyProperties.addAll(propertiesAtUserLocation);
                 ArrayList<ZillowProperty> neightborProperties= requestPropertyNeighbors(context,propertiesAtUserLocation);
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




    public static ArrayList<ZillowProperty> getZillowPropertyListAtLocation(Context context, Place place) throws
            IOException, SAXException, ParserConfigurationException {
        String searchUrl= createZipIdRequestUrl(context,place);
        HttpURLConnection httpURLConnection=getHttpUrlConnection(searchUrl);

        int responseCode=httpURLConnection.getResponseCode();

        if(responseCode==HttpURLConnection.HTTP_OK){
           Element docEle=XMLHandler.getRootElement(httpURLConnection);

            if(docEle.getChildNodes().getLength()>2){
                ArrayList<ZillowProperty> zillowProperties =XMLHandler.extractPropertiesFromUserLocation(docEle);

                return zillowProperties;
            }
        }

        return null;
    }



    private static ArrayList<ZillowProperty> requestPropertyNeighbors(Context context, ArrayList<ZillowProperty> userLocationProperties) throws IOException, ParserConfigurationException, SAXException {

        ArrayList<ZillowProperty> allNeightbors=new ArrayList<>();

        for(ZillowProperty property:userLocationProperties){
            ArrayList<ZillowProperty> propertyNeightbors=requestSinglePropertyNeightbors(context,property);
            if(propertyNeightbors!=null)
                allNeightbors.addAll(propertyNeightbors);
        }

        return allNeightbors;
    }



    private static ArrayList<ZillowProperty> requestSinglePropertyNeightbors(Context context, ZillowProperty zillowProperty) throws IOException, ParserConfigurationException, SAXException {
        String searchUrl= createSearchRequestUrl(context,zillowProperty.getId());
        HttpURLConnection httpURLConnection=getHttpUrlConnection(searchUrl);

        int responseCode=httpURLConnection.getResponseCode();

        if(responseCode==HttpURLConnection.HTTP_OK){
            Element docEle=XMLHandler.getRootElement(httpURLConnection);
            if(docEle.getChildNodes().getLength()>2){
                ArrayList<ZillowProperty> propertyDetails=XMLHandler.extractPropertyDetails(docEle);
                return propertyDetails;
            }
        }

        return null;
    }


    public static void asyncRequestPropertyPhotoUrl(final Context context, final String zipId, final ZillowPhotoUrlRequestListener listener){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    requestPropertyPhotoUrl(context,zipId,listener);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    public static void requestPropertyPhotoUrl(Context context,String zpid,ZillowPhotoUrlRequestListener listener) throws IOException,
            ParserConfigurationException, SAXException {
        String url=createPropertyDetailsRequestUrl(context,zpid);
        HttpURLConnection httpURLConnection=getHttpUrlConnection(url);

        int responseCode=httpURLConnection.getResponseCode();

        if(responseCode==HttpURLConnection.HTTP_OK){
            Element docEle=XMLHandler.getRootElement(httpURLConnection);
            if(docEle.getChildNodes().getLength()>2){
                String photoUrl=XMLHandler.extractPhotoUrl(docEle);
                listener.onZillowUrlRequestResult(photoUrl);
            }
            else
                listener.onZillowUrlRequestResult("empty_url");
        }
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
