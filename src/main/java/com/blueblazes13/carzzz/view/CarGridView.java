/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueblazes13.carzzz.view;

import com.blueblazes13.carzzz.model.CarModel;
import com.blueblazes13.carzzz.model.CarzzzModel;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author joeyk
 */
public class CarGridView extends Region {
    
    private CarzzzModel model = CarzzzModel.getModel();
    private ArrayList<CarModel> cars;
    
    public CarGridView() {
        update();
    }
    
    
    public void update() {
        this.cars = this.model.getSelectedBrand().getCars();
        this.getChildren().clear();
        GridPane grid = new GridPane();
        grid.setPrefSize(714, 725);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(30);
        
        if (this.cars == null) {
            this.cars = new ArrayList<>();
            //this.cars.add(new CarModel("Test wagen"));
        }
        int rows = -1;
        int i = 3;
        for (CarModel car: this.cars) {
            if (i > 2) {
                rows++;
                grid.addRow(rows, new CarView(car));
                i = 1;
            } else {
                grid.add(new CarView(car), i, rows);
                i++;
            }
        }
        this.getChildren().add(grid);
    }
    
    
    private AnchorPane getAddItem() {
        AnchorPane ap = new AnchorPane();
        
        // Rectangle
        Rectangle rect = new Rectangle(200, 200);
        rect.setFill(Color.valueOf("#939393"));
        ap.getChildren().add(rect);
        
        // Car name
        Label name = new Label("Add new car");
        name.setPrefSize(200, 30);
        name.setLayoutY(160);
        name.setFont(Font.font("TechnicLite", 16));
        name.setTextAlignment(TextAlignment.CENTER);
        name.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6)");
        ap.getChildren().add(name);
        
        // Color filling rectangle
        Rectangle bottomRect = new Rectangle(200, 10);
        bottomRect.setFill(Color.rgb(255, 255, 255, 0.6));
        bottomRect.setLayoutY(190);
        ap.getChildren().add(bottomRect);
        
        rect.setOnMousePressed((MouseEvent me) -> {
            System.out.println("Clicked!");
        });
        
        return ap;
    }
    
}
