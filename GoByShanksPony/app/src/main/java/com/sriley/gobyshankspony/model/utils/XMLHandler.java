package com.sriley.gobyshankspony.model.utils;

import com.sriley.gobyshankspony.model.ZillowProperty;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class XMLHandler {

    public static ArrayList <ZillowProperty> extractPropertiesFromUserLocation(Element docEle){
        ArrayList<ZillowProperty> zillowProperties=new ArrayList<>();
        NodeList response = docEle.getElementsByTagName("response");
        Element responseElement= (Element) response.item(0);
        Element resultsElement= (Element) responseElement.getElementsByTagName("results").item(0);

        for (int i=0;i<resultsElement.getElementsByTagName("result").getLength();i++){
            Element resultElement= (Element) resultsElement.getElementsByTagName("result").item(i);

            ZillowProperty zillowProperty=extractSinglePropertyFromPropertiesElement(resultElement);
            zillowProperties.add(zillowProperty);
        }

        return zillowProperties;
    }


    public static Element getRootElement(HttpURLConnection httpURLConnection) throws IOException, ParserConfigurationException, SAXException {
        InputStream inputStream=httpURLConnection.getInputStream();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document dom = db.parse(inputStream);
        Element docEle = dom.getDocumentElement();

        return docEle;
    }


    public static ArrayList<ZillowProperty> extractPropertyDetails(Element docEle){
        ArrayList<ZillowProperty> zillowProperties=new ArrayList<>();

        NodeList response = docEle.getElementsByTagName("response");
        Element responseElement= (Element) response.item(0);
        Element propertiesElement= (Element) responseElement.getElementsByTagName("properties").item(0);
        NodeList comparablesElement=  propertiesElement.getElementsByTagName("comp");

        for (int i=0;i<comparablesElement.getLength();i++){
            Element comparableElement= (Element) comparablesElement.item(i);

            ZillowProperty comparableProperty= extractSinglePropertyFromPropertiesElement(comparableElement);
            zillowProperties.add(comparableProperty);
        }

        return zillowProperties;
    }


    public static ZillowProperty extractSinglePropertyFromPropertiesElement(Element propertyElement){
        ZillowProperty zillowProperty=new ZillowProperty();
        Element zipIdElement= (Element) propertyElement.getElementsByTagName("zpid").item(0);
        Element bathroomsElement= (Element) propertyElement.getElementsByTagName("bathrooms").item(0);
        Element bedroomsElement=(Element)propertyElement.getElementsByTagName("bedrooms").item(0);
        Element rentzestimateElement= (Element) propertyElement.getElementsByTagName("rentzestimate").item(0);
        if (rentzestimateElement!=null) {
            Element amountElement= (Element) rentzestimateElement.getElementsByTagName("amount").item(0);
            zillowProperty.setRent(amountElement.getFirstChild().getNodeValue());
        }
        Element addressElement= (Element) propertyElement.getElementsByTagName("address").item(0);
        Element streetElement= (Element) addressElement.getElementsByTagName("street").item(0);
        Element zipcode= (Element) addressElement.getElementsByTagName("zipcode").item(0);
        Element city= (Element) addressElement.getElementsByTagName("city").item(0);
        Element state= (Element) addressElement.getElementsByTagName("state").item(0);

        zillowProperty.setAddress(streetElement.getFirstChild().getNodeValue());

        if(bathroomsElement!=null){
            String bathrooms=bathroomsElement.getFirstChild().getNodeValue();
            bathrooms=bathrooms.replace(".","");
            bathrooms=bathrooms.replace("0","");
            zillowProperty.setBathrooms(bathrooms);
        }

        if(bedroomsElement!=null){
            String bedrooms=bedroomsElement.getFirstChild().getNodeValue();
            bedrooms=bedrooms.replace(".","");
            bedrooms=bedrooms.replace("0","");
            zillowProperty.setBedrooms(bedrooms);
        }


        zillowProperty.setCity(city.getFirstChild().getNodeValue());
        zillowProperty.setState(state.getFirstChild().getNodeValue());
        zillowProperty.setZip(zipcode.getFirstChild().getNodeValue());
        zillowProperty.setId(zipIdElement.getFirstChild().getNodeValue());

        return zillowProperty;
    }


    public static String extractPhotoUrl(Element docEle){
        Element response = (Element) docEle.getElementsByTagName("response").item(0);
        Element imagesElement= (Element) response.getElementsByTagName("images").item(0);
        if(imagesElement!=null){
            Element imageElement= (Element) imagesElement.getElementsByTagName("image").item(0);
            Element url= (Element) imageElement.getElementsByTagName("url").item(0);
            return url.getFirstChild().getNodeValue();
        }
        else
            return "empty_url";
    }

}
