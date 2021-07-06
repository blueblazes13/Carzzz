package com.blueblazes13.carzzz;

import com.blueblazes13.carzzz.model.BrandModel;
import com.blueblazes13.carzzz.model.CarzzzModel;
import com.blueblazes13.carzzz.view.BrandView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenuFXMLController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane apItemField;
    
    @FXML
    private AnchorPane apMainMenu;

    @FXML
    private ImageView ivImage;

    @FXML
    private Label lblBrand;

    @FXML
    private MenuItem btnLabels;
    
    @FXML
    private MenuItem btnLabels2;
    
    @FXML
    private Button btnWatchCars;
    
    @FXML
    private Button btnDeleteBrand;
    
    @FXML
    private Button btnEditBrand;
    
    @FXML
    private VBox vbItemField;
    
    @FXML
    private VBox vbLabels;
    
    @FXML
    private MenuItem btnClose;

    @FXML
    private MenuItem btnSave;

    @FXML
    private MenuItem btnAdd;
    
    private CarzzzModel model;
    
    @FXML
    void initialize() {
        CarzzzModel.setController("MainMenuFXML", this);
        this.model = CarzzzModel.getModel();
        
        initButtons();
        update();
    }

    
    /**
     * Initializes all buttons used in the main menu
     */
    private void initButtons() {
        // Opens menu to add a new brand
        btnAdd.setOnAction((ActionEvent) -> {
            BrandModel oldBrand = this.model.getSelectedBrand();
            this.model.deselect();
            
            if (!ScreenController.newScreen("EditBrandFXML")) {
                System.err.println("Not able to open new brand menu.");
                this.model.select(oldBrand.getName());
            }
        });
        
        // Opens menu to edit the list of brand labels
        btnLabels.setOnAction((ActionEvent ae) -> {
            if (!ScreenController.newScreen("LabelsFXML")) {
                System.err.println("Not able to open labels menu.");
            }
        });
        
        // Edit brand
        btnEditBrand.setOnAction((ActionEvent ae) -> {
            if (this.model.getSelectedBrand() != null) {
                if (!ScreenController.newScreen("EditBrandFXML")) {
                    System.err.println("Screen not opened because other screen is already open!");
                }
            }
        });
        
        // Delete brand
        btnDeleteBrand.setOnAction((ActionEvent ae) -> {
            if (this.model.getSelectedBrand() != null) {
                this.model.removeBrand(this.model.getSelectedBrand());
                this.model.deselect();
                this.model.update();
            }
        });
        
        // Opens menu to edit the list of car labels
        btnLabels2.setOnAction((ActionEvent ae) -> {
            if (!ScreenController.newScreen("CarLabelsFXML")) {
                System.err.println("Not able to open car labels menu.");
            }
        });
        
        // Saves the programm
        btnSave.setOnAction((ActionEvent ae) -> {
            try {
                CarzzzModel.getModel().save();
            } catch (IOException ex) {
                System.err.println("Brands were not saved!");
            }
        });
        
        // Opens car menu
        this.btnWatchCars.setOnAction((ActionEvent ae) -> {
            CarsMenuFXMLController controller = (CarsMenuFXMLController)CarzzzModel.getController("CarsMenuFXML");
            if (controller != null) controller.update();
            ScreenController.changeScreen("CarsMenuFXML");
        });
    }
    
    
    /**
     * Updates the main menu
     */
    public void update() {
        this.vbItemField.getChildren().clear();
        this.vbLabels.getChildren().clear();
        
        for (BrandModel brand: this.model.getBrands()) {
            BrandView view = new BrandView(brand);
            view.setOnMouseClicked((MouseEvent me) -> {
                this.model.select(brand.getName());
                this.model.update();
            });
            this.vbItemField.getChildren().add(view);
            
            if (brand.isSelected()) {
                ArrayList<String> labels = CarzzzModel.infoLabels.getBrandLabels();
                HashMap<String, String> data = CarzzzModel.infoLabels.getBrandData(brand.getName());
                
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
                
                if (brand.getBrandImage() != "") ivImage.setImage(CarzzzModel.stringToImage(brand.getBrandImage()));
                lblBrand.setText(brand.getName());
            }
        }
    }
}
