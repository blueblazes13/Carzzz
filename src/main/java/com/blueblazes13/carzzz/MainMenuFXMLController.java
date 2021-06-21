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
import javafx.event.EventHandler;
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
    private Label lblYear;

    @FXML
    private Label lblCountry;

    @FXML
    private Label lblFounder;

    @FXML
    private Label lblCosts;

    @FXML
    private Label lblMostExpensive;

    @FXML
    private Label lblBest;

    @FXML
    private MenuItem btnLabels;
    
    @FXML
    private MenuItem btnLabels2;
    
    @FXML
    private Button btnWatchCars;
    
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
    
    @FXML
    private Menu editMenu;

    private final static Stage secondStage = new Stage();
    
    private CarzzzModel model;
    
    @FXML
    void initialize() {
        this.model = CarzzzModel.getModel(this);
        CarzzzModel.setController("MainMenuFXML", this);
        
        System.out.println(this);
        btnAdd.setOnAction(this::addBrand);
        btnLabels.setOnAction(this::showLabels);
        btnLabels2.setOnAction(this::showCarLabels);
        btnSave.setOnAction(this::save);
        
        this.btnWatchCars.setOnAction((ActionEvent ae) -> {
            CarsMenuFXMLController controller = (CarsMenuFXMLController)CarzzzModel.getController("CarsMenuFXML");
            if (controller != null) controller.update();
            ScreenController.changeScreen("CarsMenuFXML");
        });
        
        update();
    }
    
    private void save(ActionEvent ae) {
        try {
            CarzzzModel.getModel().save();
        } catch (IOException ex) {
            System.err.println("Brands were not saved!");
        }
    }
    
    
    private void showLabels(ActionEvent ae) {
        boolean opened = ScreenController.newScreen("LabelsFXML");
    }
    
    private void showCarLabels(ActionEvent ae) {
        ScreenController.newScreen("CarLabelsFXML");
    }
    
    
    private void addBrand(ActionEvent ae) {
        this.model.deselect();
        boolean opened = ScreenController.newScreen("EditBrandFXML");
    }
    
    
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
                ArrayList<String> labels = this.model.infoLabels.getBrandLabels();
                HashMap<String, String> data = this.model.infoLabels.getBrandData(brand.getName());
                int posY = 330;
                
                for (String label: labels) {
                    Label lblTitle = new Label(label);
                    lblTitle.setLayoutY(posY);
                    lblTitle.setPrefSize(87, 35);
                    lblTitle.setFont(Font.font("TechnicLite", 14));
                    lblTitle.setTextFill(Color.WHITE);
                    lblTitle.setAlignment(Pos.BASELINE_LEFT);
                    
                    Label lblData = new Label(data.get(label));
                    lblData.setLayoutY(posY);
                    lblData.setPrefSize(200, 35);
                    lblData.setFont(Font.font("TechnicLite", 14));
                    lblData.setTextFill(Color.WHITE);
                    lblData.setAlignment(Pos.BASELINE_LEFT);
                    
                    HBox row = new HBox(lblTitle, lblData);
                    row.setSpacing(50);
                    this.vbLabels.getChildren().addAll(row);
                    
                    posY += 35;
                }
                
                if (brand.getBrandImage() != "") ivImage.setImage(CarzzzModel.stringToImage(brand.getBrandImage()));
                lblBrand.setText(brand.getName());
            }
        }
    }
}
