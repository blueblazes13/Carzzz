/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueblazes13.carzzz.model;


import com.google.gson.Gson;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joeyk
 */
public class BrandModel {
    
    // Short info about the brand
    private String name;
    private String brandImageLocation;
    private String note;
    
    // All info of the brand
    public transient ArrayList<CarModel> cars;
    transient public boolean isSelected;
    private Object[] carNames = {};
    private transient CarModel selectedCar;
    
    public BrandModel(String name) {
        this.name = name;
        this.isSelected = false;
        this.cars = new ArrayList<>();
        this.note = "";
    }

    
    public String getNote() {
        return this.note;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the carImage
     */
    public String getBrandImage() {
        return this.brandImageLocation;
    }

    /**
     * 
     */
    public void setBrandImage(File brandImage) {
        RenderedImage rImage = null;
        try {
            rImage = ImageIO.read(brandImage);
        } catch (IOException ex) {
            Logger.getLogger(BrandModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ImageIO.write(rImage, "jpg", new File("Brands/" + this.getName() + "/brandImage.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(BrandModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.brandImageLocation = "Brands/" + this.getName() + "/brandImage.jpg";
    }

    
    public void selectCar(CarModel car) {
        this.selectedCar = car;
    }
    
    
    public void deselectCar() {
        this.selectedCar = null;
    }
    
    
    public CarModel getSelectedCar() {
        return this.selectedCar;
    }
    
    
    /**
     * @return the cars
     */
    public ArrayList<CarModel> getCars() {
        if (this.cars == null) this.cars = new ArrayList<>();
        return cars;
    }

    /**
     * @param cars the cars to set
     */
    public void setCars(ArrayList<CarModel> cars) {
        this.cars = cars;
    }

    /**
     * @return the isSelected
     */
    public boolean isSelected() {
        return isSelected;
    }

    
    public void setNote(String note) {
        this.note = note;
    }
    
    
    /**
     * @param isSelected the isSelected to set
     */
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    
    
    public void addCar(CarModel car) {
        if (this.cars == null) this.cars = new ArrayList<>();
        this.cars.add(car);
    }
    
    
    public void removeCar(CarModel car) {
        if (this.cars.contains(car)) {
            this.cars.remove(car);
        }
    }
    
/**
     * Saves all the cars of this brand.
     * 
     * @throws IOException If not able to save lists.
     */
    public void saveCars() throws IOException {
        if (this.cars == null) return;
        
        ArrayList<String> names = new ArrayList<>();
        this.cars.forEach((CarModel model) -> {names.add(model.getName());});
        this.carNames = names.toArray();
        
        for (CarModel model: this.cars) {
            model.save();
        }
    }
    
    
    /**
     * Loads all saved cars of this brand.
     * 
     * @throws FileNotFoundException No file with labels found.
     */
    public void loadCars() throws FileNotFoundException {
        this.cars = new ArrayList<>();
        if (this.carNames == null) return;
        
        for (Object name: this.carNames) {
            FileReader dataReader = new FileReader("Brands/" + this.getName() + "/" + (String)name + "/carModel.txt");
            Gson gsonConverter = new Gson();
            CarModel carModel = gsonConverter.fromJson(dataReader, CarModel.class);
            carModel.checkImages();
            carModel.setModel(this);
            //this.note = CarzzzModel.getModel().infoLabels.getBrandNotes(this.name);
            this.cars.add(carModel);
        }
    }
}
