/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueblazes13.carzzz.view;

import com.blueblazes13.carzzz.EditCarsFXMLController;
import com.blueblazes13.carzzz.ScreenController;
import com.blueblazes13.carzzz.model.CarModel;
import com.blueblazes13.carzzz.model.CarzzzModel;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author joeyk
 */
public class CarView extends Region {
    
    private CarModel model;
    
    public CarView(CarModel model) {
        this.model = model;
        createItem();
    }
    
    
    private void createItem() {
        // Rectangle
        Rectangle rect = new Rectangle(180, 180);
        rect.setFill(Color.valueOf("#ebebeb"));
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        this.getChildren().add(rect);
        
        // Car Image
        ImageView image = new ImageView();
        if (this.model.getCarImage() != null) image.setImage(CarzzzModel.stringToImage(this.model.getCarImage()));
        image.setFitHeight(170);
        image.setFitWidth(160);
        image.setX(10);
        image.setY(10);
        image.toBack();
        image.setPreserveRatio(true);
        this.getChildren().add(image);
        
        // Label background square
        Rectangle backRect = new Rectangle(180,42);
        backRect.setLayoutY(158);
        backRect.setFill(Color.valueOf("#a6a6a6"));
        backRect.setOpacity(0.9);
        backRect.setArcHeight(10);
        backRect.setArcWidth(10);
        backRect.toFront();
        this.getChildren().add(backRect);
        
        // Car name
        Label name = new Label("  " + this.model.getName());
        name.setPrefSize(180, 42);
        name.setLayoutY(158);
        name.setFont(Font.font("TechnicLite", 13));
        name.setTextAlignment(TextAlignment.LEFT);
        // name.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6)");
        this.getChildren().add(name);
        
        // Color filling rectangle
        Rectangle bottomRect = new Rectangle(200, 200);
        bottomRect.setFill(Color.rgb(255, 255, 255, 0));
        this.getChildren().add(bottomRect);
        bottomRect.toFront();
        
        bottomRect.setOnMousePressed((MouseEvent me) -> {
            CarzzzModel.getModel().getSelectedBrand().selectCar(this.model);
            EditCarsFXMLController controller = (EditCarsFXMLController) CarzzzModel.getController("EditCarsFXML");
            if (controller != null) controller.update();
            ScreenController.changeScreen("EditCarsFXML");
        });
    }
    
}
