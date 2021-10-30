/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueblazes13.carzzz.model;

import com.blueblazes13.carzzz.CarProperties;
import com.google.gson.Gson;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private Object[] carImages;
    private transient BrandModel model;
    private transient ArrayList<String> images = new ArrayList<>();
    
    
    public CarModel(String name) {
        this.model = CarzzzModel.getModel().getSelectedBrand();
        this.name = name;
        checkImages();
    }
    
    public void checkImages() {
        if (this.images == null) this.images = new ArrayList<>();
        if (this.carImages != null) {
            for (Object image: this.carImages) {
                this.images.add((String) image);
            }
        }
    }
    
    
    public String getName() {
        return this.name;
    }
    
    
    public void setModel(BrandModel model) {
        this.model = model;
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
    
    public ArrayList<String> getImages() {
        if (this.images == null) this.images = new ArrayList<>();
        return this.images;
    }
    
    public Image getFirstImage() {
        if (this.images == null) this.images = new ArrayList<>();
        if (this.images.isEmpty()) {
            return null;
        } else {
            return CarzzzModel.fileToImage(new File(this.images.get(0)));
        }
    }
    
    public String getCarImage() {
        return this.carImage;
    }
    
    public void setCarHeadImage(File image) {
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
    
    
    public void addImage(File image) {
        //this.images.add(image);
        BrandModel model = CarzzzModel.getModel().getSelectedBrand();
        RenderedImage rImage = null;
        
        try {
            rImage = ImageIO.read(image);
        } catch (IOException ex) {
            Logger.getLogger(CarModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            File file = new File("Brands/" + model.getName() + "/" + getName() + "/Images/" + image.getName());
            file.mkdirs();
            ImageIO.write(rImage, "jpg", file);
        } catch (IOException ex) {
            Logger.getLogger(CarModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.images.add("Brands/" + model.getName() + "/" + getName() + "/Images/" + image.getName());
    }
    
    
    public void removeImage(String image) {
        this.images.remove(image);
    }
    
    
    public void save() throws IOException {
        if (this.images != null) this.carImages = images.toArray();
        
        File file = new File("Brands/" + this.model.getName() + "/" + this.getName());
        file.mkdirs();

        FileWriter dataWriter = new FileWriter("Brands/" + this.model.getName() + "/" + this.getName() + "/carModel.txt");
        Gson gsonConverter = new Gson();
        String jsonCarModels = gsonConverter.toJson(this);
        dataWriter.write(jsonCarModels);
        dataWriter.close();
    }
    
}
