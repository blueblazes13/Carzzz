/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueblazes13.carzzz.model;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author joeyk
 */
public class BrandsHandler {
    
    ArrayList<BrandModel> brands = new ArrayList<>();
    
    public BrandsHandler(ArrayList<BrandModel> brands) {
        this.brands = brands;
    }
    
    // TODO: Alle brands opslaan in een mapje met 1 brand per file
    
    /**
     * Saves the lists of the labels.
     * 
     * @throws IOException If not able to save lists.
     */
    public void save() throws IOException {
        FileWriter dataWriter = new FileWriter("brands.txt");
        Gson gsonConverter = new Gson();
        
        String jsonBrandsHandler = gsonConverter.toJson(this);
        dataWriter.write(jsonBrandsHandler);
        dataWriter.close();
    }
    
    
    /**
     * Loads all saved lists with labels.
     * Use get[name]Labels(); to get the loaded lists.
     * 
     * @throws FileNotFoundException No file with labels found.
     */
    public void load() throws FileNotFoundException {
        FileReader dataReader = new FileReader("brands.txt");
        Gson gsonConverter = new Gson();
        BrandsHandler model = gsonConverter.fromJson(dataReader, BrandsHandler.class);
        
        this.brands = model.brands;
    }
    
}
