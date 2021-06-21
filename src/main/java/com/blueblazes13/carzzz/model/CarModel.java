/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueblazes13.carzzz.model;

import com.blueblazes13.carzzz.CarProperties;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author joeyk
 */
public class CarModel {
    
    private transient ArrayList<String> carProperties = new ArrayList<>();
    private String name;
    private String carImage;
    private transient ArrayList<Image> images = new ArrayList<>();
    
    
    public CarModel(String name) {
        
        CarProperties.getProperties().forEach(_item -> {
            this.carProperties.add(null);
        });
        
        this.name = name;
    }
    
    
    public String getName() {
        return this.name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    public String getProperty(String property) {
        return this.carProperties.get(carProperties.indexOf(property));
    }
    
    
    public void setProperty(String property, String value) {
        if (CarProperties.propertyExists(property)) {
            this.carProperties.set(carProperties.indexOf(property), value);
        }
    }
    
    public ArrayList<Image> getImages() {
        if (this.images == null) this.images = new ArrayList<>();
        return this.images;
    }
    
    public Image getFirstImage() {
        if (this.images == null) this.images = new ArrayList<>();
        if (this.images.isEmpty()) {
            return null;
        } else {
            return this.images.get(0);
        }
    }
    
    public String getCarImage() {
        return this.carImage;
    }
    
    public void setCarImage(File image) {
        BrandModel model = CarzzzModel.getModel().getSelectedBrand();
        RenderedImage rImage = null;
        try {
            rImage = ImageIO.read(image);
        } catch (IOException ex) {
            Logger.getLogger(CarModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ImageIO.write(rImage, "jpg", new File("Brands/" + model.getName() + "/" + getName() + "/carImage.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(BrandModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.carImage = "Brands/" + model.getName() + "/" + getName() + "/carImage.jpg";
    }
    
    
    public void addImage(Image image) {
        this.images.add(image);
    }
    
}
