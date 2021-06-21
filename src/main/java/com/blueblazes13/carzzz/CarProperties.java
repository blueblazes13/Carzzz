/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueblazes13.carzzz;

import java.util.ArrayList;

/**
 *
 * @author joeyk
 */
public class CarProperties {
    
    private static final ArrayList<String> properties = new ArrayList<>();
    
    
    public static void addProperty(String property) {
        if (!CarProperties.properties.contains(property)) {
            CarProperties.properties.add(property);
        }
    }
    
    public static boolean propertyExists(String property) {
        return CarProperties.properties.contains(property);
    }
    
    
    public static String getProperty(int index) {
        return CarProperties.getProperty(index);
    }
    
    
    public static ArrayList<String> getProperties() {
        return CarProperties.properties;
    }
    
    
    public static int indexOf(String property) {
        return CarProperties.properties.indexOf(property);
    }
    
    
    public static void removeProperty(String property) {
        if (CarProperties.properties.contains(property)) {
            CarProperties.properties.remove(property);
        }
    }
    
}
