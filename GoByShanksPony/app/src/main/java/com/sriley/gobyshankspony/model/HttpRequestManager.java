package com.sriley.gobyshankspony.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;


public class HttpRequestManager {

        protected static HttpURLConnection getHttpUrlConnection(String requestUrl) throws IOException {

            URL url=new URL(requestUrl);
            URLConnection urlConnection=url.openConnection();
            HttpURLConnection httpURLConnection= (HttpURLConnection) urlConnection;

            return httpURLConnection;
        }

        protected static StringBuilder openAndReadStream(HttpURLConnection httpURLConnection) throws IOException {
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            return responseStrBuilder;
        }


        protected static ArrayList<String> getFormattedDate(){
            ArrayList<String> formattedDate=new ArrayList<>();
            int year =  Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            int day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

            String dayFormatted=String.format("%02d", day);
            String monthFormatted=String.format("%02d", month+1);

            formattedDate.add(year+"");
            formattedDate.add(monthFormatted);
            formattedDate.add(dayFormatted);

            return formattedDate;
        }
    }
