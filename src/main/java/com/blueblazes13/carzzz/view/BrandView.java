/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueblazes13.carzzz.view;

import com.blueblazes13.carzzz.ScreenController;
import com.blueblazes13.carzzz.model.BrandModel;
import com.blueblazes13.carzzz.model.CarzzzModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author joeyk
 */
public class BrandView extends Region {
    
    private BrandModel model;
    private CarzzzModel carzzzModel = CarzzzModel.getModel();
    
    public BrandView(BrandModel model) {
        this.model = model;
        
        createItem();
    }
    
    
    private void createItem() {
        // Background rectangle
        Rectangle background = new Rectangle(714, 140);
        background.setFill(Color.valueOf("#a1a1a1"));
        background.setArcHeight(20);
        background.setArcWidth(20);
        this.getChildren().add(background);
        
        // Brand Image
        ImageView image = new ImageView();
        if (model.getBrandImage() != null) image.setImage(CarzzzModel.stringToImage(model.getBrandImage()));
        image.setLayoutX(15);
        image.setLayoutY(15);
        image.setFitHeight(110);
        image.setFitWidth(110);
        image.setPreserveRatio(true);
        this.getChildren().add(image);
        
        // Brand title
        Label title = new Label(model.getName());
        title.setLayoutX(136);
        title.setLayoutY(15);
        title.setPrefSize(390, 48);
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("TechnicLite", 20));
        title.setUnderline(true);
        this.getChildren().add(title);
        
        // Notes field
        TextArea notes = new TextArea();
        notes.setPrefSize(390, 56);
        notes.setLayoutX(135);
        notes.setLayoutY(65);
        notes.setPromptText("Notes");
        notes.setStyle("-fx-background-color: White ; -fx-prompt-text-fill: WHITE; -fx-control-inner-background: #c2c2c2;");
        notes.setWrapText(true);
        notes.setText(this.model.getNote());
        this.getChildren().add(notes);
        
        // Edit button
        Button editButton = new Button("Edit");
        editButton.setLayoutX(538);
        editButton.setLayoutY(65);
        editButton.setStyle("-fx-background-color: #939393; -fx-border-color: #AFAFAF;");
        editButton.setTextFill(Color.WHITE);
        editButton.setFont(Font.font("SansSerif"));
        editButton.setPrefSize(98, 28);
        this.getChildren().add(editButton);
        
        // Delete button
        Button deleteButton = new Button("Delete");
        deleteButton.setLayoutX(538);
        deleteButton.setLayoutY(93);
        deleteButton.setStyle("-fx-background-color: #939393; -fx-border-color: #AFAFAF;");
        deleteButton.setTextFill(Color.WHITE);
        deleteButton.setFont(Font.font("SansSerif"));
        deleteButton.setPrefSize(98, 28);
        this.getChildren().add(deleteButton);
        
        // Show button
        Button showButton = new Button("Show");
        showButton.setLayoutX(538);
        showButton.setLayoutY(25);
        showButton.setStyle("-fx-background-color: #939393; -fx-border-color: #AFAFAF;");
        showButton.setTextFill(Color.WHITE);
        showButton.setFont(Font.font("SansSerif"));
        showButton.setPrefSize(98, 28);
        this.getChildren().add(showButton);
        
        // Set edit button
        editButton.setOnAction((ActionEvent ae) -> {
            this.carzzzModel.select(this.model.getName());
            if (!ScreenController.newScreen("EditBrandFXML")) System.err.println("Screen not opened because other screen is already open!");
        });
        
        // Set show button
        showButton.setOnAction((ActionEvent ae) -> {
            this.carzzzModel.select(this.model.getName());
            this.carzzzModel.update();
        });
        
        // Set delete button
        deleteButton.setOnAction((ActionEvent ae) -> {
            this.carzzzModel.deselect();
            this.carzzzModel.removeBrand(this.model);
            this.carzzzModel.update();
        });
        
        // Set note when text changes
        notes.textProperty().addListener((observable, oldValue, newValue) -> {
            //this.carzzzModel.infoLabels.setBrandNote(this.model.getName(), newValue);
            this.model.setNote(newValue);
        });
    }
        
}
