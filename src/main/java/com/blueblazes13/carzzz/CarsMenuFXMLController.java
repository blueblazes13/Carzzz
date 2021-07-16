package com.blueblazes13.carzzz;

import com.blueblazes13.carzzz.model.CarzzzModel;
import com.blueblazes13.carzzz.view.CarGridView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CarsMenuFXMLController {

    @FXML
    private ResourceBundle resources;
    
    @FXML
    private URL location;

    @FXML
    private AnchorPane apItemField;

    @FXML
    private MenuItem btnClose;

    @FXML
    private MenuItem btnSave;
    
    @FXML
    private ImageView ivBrandImage;
    
    @FXML
    private VBox vbLabels;
    
    @FXML
    private MenuItem btnAdd;
    
    @FXML
    private Button btnBack;
    
    private final CarGridView gridView = new CarGridView();
    
    @FXML
    void initialize() {
        CarzzzModel.setController("CarsMenuFXML", this);
        
        this.btnAdd.setOnAction(this::newCar);
        this.btnBack.setOnAction(this::back);
        this.btnSave.setOnAction(this::save);
        
        update();
    }
    
    
    /**
     * Updates the car menu
     */
    public void update() {
        this.apItemField.getChildren().clear();
        this.gridView.update();
        this.apItemField.getChildren().add(this.gridView);
        
        CarzzzModel model = CarzzzModel.getModel();
        
        this.ivBrandImage.setImage(CarzzzModel.stringToImage(model.getSelectedBrand().getBrandImage()));
        this.vbLabels.getChildren().clear();
        
        ArrayList<String> labels = model.infoLabels.getBrandLabels();
        HashMap<String, String> data = model.infoLabels.getBrandData(model.getSelectedBrand().getName());

        for (String label: labels) {
            Label lblTitle = new Label(label);
            lblTitle.setPrefSize(87, 35);
            lblTitle.setFont(Font.font("TechnicLite", 14));
            lblTitle.setTextFill(Color.web("#656565"));
            lblTitle.setAlignment(Pos.BASELINE_LEFT);

            Label lblData = new Label(data.get(label));
            lblData.setPrefSize(200, 35);
            lblData.setFont(Font.font("TechnicLite", 14));
            lblData.setTextFill(Color.web("#656565"));
            lblData.setAlignment(Pos.BASELINE_LEFT);

            HBox row = new HBox(lblTitle, lblData);
            row.setSpacing(50);
            this.vbLabels.getChildren().addAll(row);
        }

    }
    
    
    /**
     * Returns back to the main menu
     * 
     * @param ae ActionEvent of buttonClick
     */
    private void back(ActionEvent ae) {
        CarzzzModel.getModel().deselect();
        ((MainMenuFXMLController)CarzzzModel.getController("MainMenuFXML")).update();
        ScreenController.changeScreen("MainMenuFXML");
    }
    
    
    /**
     * Opens the menu to add a new car to the list of cars
     * 
     * @param ae ActionEvent of buttonClick
     */
    private void newCar(ActionEvent ae) {
        EditCarsFXMLController controller = (EditCarsFXMLController) CarzzzModel.getController("EditCarsFXML");
        if (controller != null) controller.update();
        
        ScreenController.changeScreen("EditCarsFXML");
        this.gridView.update();
    }
    
    
    /**
     * Saves program
     * 
     * @param ae ActionEvent of buttonClick
     */
    private void save(ActionEvent ae) {
        try {
            CarzzzModel.getModel().save();
        } catch (IOException ex) {
            System.err.println("Not able to save!");
            ex.printStackTrace();
        }
    }
    

}
