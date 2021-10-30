/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueblazes13.carzzz.model;

import com.blueblazes13.carzzz.MainMenuFXMLController;
import com.blueblazes13.carzzz.model.BrandModel;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author joeyk
 */
public class CarzzzModel {
    
    public transient ArrayList<BrandModel> brands;
    private transient BrandModel brandToEdit;
    private transient BrandModel brandToOpen;
    private transient MainMenuFXMLController controller;
    private static boolean isOpen = false;
    private static CarzzzModel model;
    private static HashMap<String, Object> controllers;
    private Object[] brandNames = {};
    
    public static InfoLabels infoLabels;
    
    
    
    public CarzzzModel(MainMenuFXMLController controller) {
        CarzzzModel.infoLabels = new InfoLabels();
        try {
            this.infoLabels.load();
        } catch (FileNotFoundException ex) {
            System.err.println("No label file found!");
        }
        this.brands = new ArrayList<>();
        this.brandToEdit = null;
        this.controller = controller;
    }
    
    public static void setController(String name, Object controller) {
        if (CarzzzModel.controllers == null) CarzzzModel.controllers = new HashMap<>();
        CarzzzModel.controllers.put(name, controller);
    }
    
    public static Object getController(String name) {
        return CarzzzModel.controllers.get(name);
    }
    
    public static CarzzzModel getModel() {
        if (CarzzzModel.model == null) {
            CarzzzModel.model = new CarzzzModel((MainMenuFXMLController) CarzzzModel.getController("MainMenuFXML"));
            try {
                CarzzzModel.model.load();
            } catch (FileNotFoundException ex) {
                System.err.println("Brands were not loaded!");
                ex.printStackTrace();
            }
        }
        return CarzzzModel.model;
    }
    
    
    public static Image fileToImage(File image) {
        if (image == null) return null;
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(image);
        } catch (FileNotFoundException ex) {
            System.err.println("File has not been found!");
            ex.printStackTrace(System.out);
            return null;
        }
        return new Image(inputStream);
    }
    
    
    public static Image stringToImage(String image) {
        if (image == null) return null;
        File imFile = new File(image);
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(imFile);
        } catch (FileNotFoundException ex) {
            System.err.println("File has not been found!");
            ex.printStackTrace(System.out);
            return null;
        }
        return new Image(inputStream);
    }
    
    
    public static File showImageChooser() {
        if (CarzzzModel.isOpen) return null;
        CarzzzModel.isOpen = true;
        
        Stage stage = new Stage();
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose image");
        ExtensionFilter ext = new ExtensionFilter("jpg files (.jpg)", "*.jpg");
        fc.setSelectedExtensionFilter(ext);
        File selectedFile = fc.showOpenDialog(stage);
        
        if (selectedFile == null) {
            CarzzzModel.isOpen = false;
            return null;
        }
        

        
        CarzzzModel.isOpen = false;
        return selectedFile;
    }
    
    
    public void update(){
        this.controller.update();
    }

    
    /**
     * Checks if brand already exists.
     * 
     * @param name The name of the brand.
     * @return true if the brand exists, else false.
     */
    public boolean brandExists(String name) {
        for (BrandModel brand: this.getBrands()) {
            if (brand.getName().equals(name)) return true;
        }
        
        return false;
    }
    
    
     /**
      * If brand already exists, overwrites with the new brand.
      * If not, it adds the new brand to the list.
      * 
      * @param brand The brand to set/add.
      */
    public void setBrand(BrandModel brand) {
        if (brandExists(brand.getName())) {
            int index = -1;
            for (BrandModel curBrand: this.getBrands()) {
                if (curBrand.getName().equals(brand.getName())) index = this.getBrands().indexOf(curBrand);
            }
            
            this.getBrands().set(index, brand);
        } else {
            this.getBrands().add(brand);
        }
    }

    
    public void removeBrand(BrandModel brand) {
        this.getBrands().remove(brand);
    }
    
    
    /**
     * @return the brandToEdit
     */
    public BrandModel getBrandToEdit() {
        BrandModel brand = this.brandToEdit;
        this.brandToEdit = null;
        return brand;
    }

    /**
     * @param brandToEdit the brandToEdit to set
     */
    public void setBrandToEdit(BrandModel brandToEdit) {
        this.brandToEdit = brandToEdit;
    }
    
    
    public BrandModel getSelectedBrand() {
        for (BrandModel brand: this.getBrands()) {
            if (brand.isSelected()) return brand;
        }
        
        return null;
    }
    

    /**
     * @return the brands
     */
    public ArrayList<BrandModel> getBrands() {
        return this.brands;
    }
    
    public void select(String brandName) {
        for (BrandModel brand: this.getBrands()) {
            if (brand.isSelected()) brand.setSelected(false);
            
            if (brand.getName() == brandName) {
                brand.setSelected(true);
            }
        }
        
    }
    
    public void deselect() {
        for (BrandModel brand: this.getBrands()) {
            if (brand.isSelected()) brand.setSelected(false);
        }
    }
    
    
    /**
     * Saves the lists of the labels.
     * 
     * @throws IOException If not able to save lists.
     */
    public void save() throws IOException {
        this.infoLabels.save();
        ArrayList<String> names = new ArrayList<>();
        this.brands.forEach((BrandModel model) -> {names.add(model.getName());});
        this.brandNames = names.toArray();
        
        File file1 = new File("Brands");
        file1.delete();
        
        for (BrandModel model: this.brands) {
            File file = new File("Brands/" + model.getName());
            file.mkdirs();
            
            model.saveCars();
            
            FileWriter dataWriter = new FileWriter("Brands/" + model.getName() + "/brandModel.txt");
            Gson gsonConverter = new Gson();
            String jsonBrandModels = gsonConverter.toJson(model);
            dataWriter.write(jsonBrandModels);
            dataWriter.close();
        }
        
        FileWriter dataWriter = new FileWriter("carzzzModel.txt");
        Gson gsonConverter = new Gson();
        String jsonBrandModels = gsonConverter.toJson(this);
        dataWriter.write(jsonBrandModels);
        dataWriter.close();
    }
    
    
    /**
     * Loads all saved lists with labels.
     * Use get[Name]Labels(); to get the loaded lists.
     * 
     * @throws FileNotFoundException No file with labels found.
     */
    public void load() throws FileNotFoundException {
        FileReader dataReader = new FileReader("carzzzModel.txt");
        Gson gsonConverter = new Gson();
        CarzzzModel model = gsonConverter.fromJson(dataReader, CarzzzModel.class);
        
        for (Object name: model.brandNames) {
            FileReader dataReader1 = new FileReader("Brands/" + (String)name + "/brandModel.txt");
            Gson gsonConverter1 = new Gson();
            BrandModel brandModel = gsonConverter1.fromJson(dataReader1, BrandModel.class);
            brandModel.loadCars();
            this.brands.add(brandModel);
        }
    }
    
}
