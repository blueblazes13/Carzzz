
package com.blueblazes13.carzzz.model;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Makes it possible to add new labels to add extra info to the images.
 * 
 * @author Joey Koster
 */
public class InfoLabels {
    
    private ArrayList<String> brandLabels;
    private ArrayList<String> carLabels;
    
    private HashMap<String, HashMap<String, String>> brandData;
    private HashMap<String, HashMap<String, String>> carData;
    private HashMap<String, String> notes;
    
    public InfoLabels() {
        this.brandLabels = new ArrayList<>();
        this.carLabels = new ArrayList<>();
        this.brandData = new HashMap<>();
        this.carData = new HashMap<>();
        this.notes = new HashMap<>();
    }
    
    
    // Getters
    
    /**
     * Gets the list of brand labels.
     * 
     * @return ArrayList of brandlabels.
     */
    public ArrayList<String> getBrandLabels() {
        return this.brandLabels;
    }
    
    
    /**
     * Gets the list of car labels.
     * 
     * @return ArrayList of carlabels.
     */
    public ArrayList<String> getCarLabels() {
        return this.carLabels;
    }
    
    
    /**
     * Gets the list of brandNotes.
     * 
     * @return ArrayList of notes.
     */
    public String getBrandNotes(String brand) {
        if (this.notes.containsKey(brand)) {
            return this.notes.get(this);
        }
        return null;
    }
    
    
    public HashMap<String, String> getBrandData(String brand) {
        if (this.brandData.containsKey(brand)) {
            return this.brandData.get(brand);
        }
        return new HashMap<>();
    }
    
    public HashMap<String, String> getCarData(String car) {
        if (this.carData.containsKey(car)) {
            return this.carData.get(car);
        }
        return new HashMap<>();
    }
    
    
    // Setters
    
    
    public void renameBrand(String oldLabel, String newLabel) {
        if (this.brandData.containsKey(oldLabel)) {
            HashMap<String, String> data = this.brandData.remove(oldLabel);
            this.brandData.put(newLabel, data);
        }
    }
    
    public void renameCar(String oldLabel, String newLabel) {
        if (this.carData.containsKey(oldLabel)) {
            HashMap<String, String> data = this.carData.remove(oldLabel);
            this.carData.put(newLabel, data);
        }
    }
    
    
    /**
     * Adds a new label tot the list of brand labels.
     * Saves after new label has been added.
     * 
     * @param label The name of the label to add.
     */
    public void addBrandLabel(String label) {
        if (this.brandLabels.contains(label)) return;
        this.brandLabels.add(label);
        for (String brand: this.brandData.keySet()) {
            this.brandData.get(brand).put(label, "Not set");
        }
        try {
            this.save();
        } catch (IOException ex) {
            System.err.println("Error while trying to save new brandlabel.");
        }
    }
    
    
    public void addCarLabel(String label) {
        if (this.carLabels.contains(label)) return;
        this.carLabels.add(label);
        for (String car: this.carData.keySet()) {
            this.carData.get(car).put(label, "Not set");
        }
        try {
            this.save();
        } catch (IOException ex) {
            System.err.println("Error while trying to save new car label.");
        }
    }
    
    
     /**
      * Adds data to a specified label.
      * Replaces old data if data already existed.
      * 
      * @param brand The brand to add the data to.
      * @param label The label to change the data of.
      * @param data The data to set to the label.
      */
    public void setBrandData(String brand, String label, String data) {
        if (!this.brandData.containsKey(brand)) { // If brand does not exist
            this.brandData.put(brand, new HashMap<>());
        }

        HashMap<String, String> labels = this.brandData.get(brand);
        labels.put(label, data);
        this.brandData.put(brand, labels);
    }
    
    
    public void setCarData(String car, String label, String data) {
        if (!this.carData.containsKey(car)) { // If car does not exist
            this.carData.put(car, new HashMap<>());
            
        }

        HashMap<String, String> labels = this.carData.get(car);
        labels.put(label, data);
        this.carData.put(car, labels);
    }
    
    
    public void setBrandNote(String brand, String note) {
        if (this.brandData.containsKey(brand)) {
            notes.put(brand, note);
            System.out.println("Gelukt!");
        }
    }
    
    
    public void removeBrandLabel(String label) {
        if (!this.brandLabels.contains(label)) return;
        
        Set<String> brands = this.brandData.keySet();
        
        for (String brand: brands) {
            HashMap<String, String> labels = this.brandData.get(brand);
            if (labels.containsKey(label)) {
                labels.remove(label);
                this.brandData.put(brand, labels);
            }
        }
        this.brandLabels.remove(label);
        try {
            this.save();
        } catch (IOException ex) {
            System.err.println("Error while saving after removing brand label");
        }
    }
    
    
    public void removeCarLabel(String label) {
        if (!this.carLabels.contains(label)) return;
        
        Set<String> cars = this.carData.keySet();
        
        for (String car: cars) {
            HashMap<String, String> labels = this.carData.get(car);
            if (labels.containsKey(label)) {
                labels.remove(label);
                this.carData.put(car, labels);
            }
        }
        this.carLabels.remove(label);
        try {
            this.save();
        } catch (IOException ex) {
            System.err.println("Error while saving after removing car label");
        }
    }
    
    /**
     * Saves the lists of the labels.
     * 
     * @throws IOException If not able to save lists.
     */
    public void save() throws IOException {
        FileWriter dataWriter = new FileWriter("infoLabels.txt");
        Gson gsonConverter = new Gson();
        
        String jsonInfoLabels = gsonConverter.toJson(this);
        dataWriter.write(jsonInfoLabels);
        dataWriter.close();
    }
    
    
    /**
     * Loads all saved lists with labels.
     * Use get[name]Labels(); to get the loaded lists.
     * 
     * @throws FileNotFoundException No file with labels found.
     */
    public void load() throws FileNotFoundException {
        FileReader dataReader = new FileReader("infoLabels.txt");
        Gson gsonConverter = new Gson();
        InfoLabels model = gsonConverter.fromJson(dataReader, InfoLabels.class);
        
        this.brandLabels = model.brandLabels;
        this.carLabels = model.carLabels;
        this.carData = model.carData;
        this.brandData = model.brandData;
        this.notes = model.notes;
    }
    
}
