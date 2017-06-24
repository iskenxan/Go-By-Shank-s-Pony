package com.sriley.gobyshankspony.model;

import android.content.Context;
import android.location.Location;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.interfaces.FoursquareRequestListener;
import com.sriley.gobyshankspony.model.utils.JSONHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;


public class FoursquareManager extends HttpRequestManager {


    public static void sendAsyncSearchVenueRequest(final Context context, final Location location,
                                                   final FoursquareRequestListener listener){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendSearchServerRequest(context,location,listener);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }



    private static void sendSearchServerRequest(Context context, Location location,FoursquareRequestListener listener) throws
            IOException,
            JSONException {
        String requestUrl=createSearchRequestUrl(context,location);

        HttpURLConnection httpURLConnection=getHttpUrlConnection(requestUrl);

        int responseCode=httpURLConnection.getResponseCode();

        if(responseCode== HttpURLConnection.HTTP_OK){

            JSONArray venuesObj= getVenueObjects(httpURLConnection);
            ArrayList<Venue> venues=  JSONHandler.extractVenues(venuesObj);
            listener.onFoursquareReturn(venues);
        }
        else
            throw new MalformedURLException("Error connecting to data source");
    }




    private static String createSearchRequestUrl(Context context,Location location) {
        double lat=location.getLatitude();
        double longt=location.getLongitude();
        double alt=location.getAltitude();

        String requestResourceStr = context.getResources().getString(R.string.foursquare_api_search_url);
        requestResourceStr=requestResourceStr.replace("LATITUDE",lat+"");
        requestResourceStr=requestResourceStr.replace("LONGITUDE",longt+"");
        requestResourceStr=requestResourceStr.replace("ALTITUDE",alt+"");

        requestResourceStr=getRequestUrlDate(requestResourceStr);

        return requestResourceStr;
    }



    private static String getRequestUrlDate(String requestResourceStr){
        ArrayList<String> formattedDate=getFormattedDate();

        requestResourceStr=requestResourceStr.replace("YEAR",formattedDate.get(0));
        requestResourceStr=requestResourceStr.replace("MONTH",formattedDate.get(1));
        requestResourceStr=requestResourceStr.replace("DAY",formattedDate.get(2));

        return requestResourceStr;
    }


    public static JSONArray getVenueObjects(HttpURLConnection httpURLConnection) throws IOException,
            JSONException {
        StringBuilder responseStrBuilder=openAndReadStream(httpURLConnection);
        JSONArray venues= JSONHandler.getJsonObjectsFromString(responseStrBuilder.toString());

        return venues;
    }


}
