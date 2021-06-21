package com.blueblazes13.carzzz;

import com.blueblazes13.carzzz.model.BrandModel;
import com.blueblazes13.carzzz.model.CarzzzModel;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EditBrandFXMLController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClose;
    
    @FXML
    private ImageView ivBrandImage;
    
    @FXML
    private VBox vbLabels;
    
    
    private CarzzzModel model = CarzzzModel.getModel();
    private BrandModel brandModel;
    
    @FXML
    void initialize() {
        CarzzzModel.setController("EditBrandFXML", this);
        // Get brandModel of the brand-to-edit
        this.brandModel = this.model.getSelectedBrand(); // checken!!!!
        if (this.brandModel == null) this.brandModel = new BrandModel("No name set");
        this.model.setBrand(this.brandModel); // Voegt de brand toe als deze er nog niet in stond.
        model.select(this.brandModel.getName());
        
        if (this.brandModel.getBrandImage() != null) {
            ivBrandImage.setImage(CarzzzModel.stringToImage(this.brandModel.getBrandImage()));
        }
        
        
        // Adds listener that normally closes the screen
        ScreenController.addListener("EditBrandFXML", this::close);
        
        // delete button
        btnDelete.setOnAction((ActionEvent ae) -> {
            model.removeBrand(this.brandModel);
            model.update();
            ScreenController.closeScreen("EditBrandFXML");
        });
        
        // Close button
        btnClose.setOnAction((ActionEvent ae) -> {
            close(null);
        });

        
        // Checks for image change
        ivBrandImage.setOnMouseClicked((MouseEvent me) -> {
            File image = CarzzzModel.showImageChooser();
            if (image != null) ivBrandImage.setImage(CarzzzModel.fileToImage(image));
            brandModel.setBrandImage(image);
            
        });
        
        setTextFields();
    }
    
    public void update() {
        this.setTextFields();
    }
    
    
    public void setTextFields() {
        this.vbLabels.getChildren().clear();
        Label title1 = createLabel("Merknaam");

        TextField infoField1 = new TextField();
        if (brandModel.getName() != "No name set") infoField1.setText(brandModel.getName());
        infoField1.setPrefSize(190, 25);
        infoField1.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            this.brandModel.setName(t1);
            CarzzzModel.infoLabels.renameBrand(t, t1);
        });

        HBox row1 = new HBox(title1, infoField1);
        row1.setSpacing(30);
        
        this.vbLabels.getChildren().add(row1);
        
        for (String label: CarzzzModel.infoLabels.getBrandLabels()) {
            Label title = createLabel(label);

            TextField infoField = new TextField();
            if (CarzzzModel.infoLabels.getBrandData(this.brandModel.getName()).containsKey(label)) {
                infoField.setText(CarzzzModel.infoLabels.getBrandData(this.brandModel.getName()).get(label));
            }
            
            infoField.setPrefSize(190, 25);
            infoField.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
                CarzzzModel.infoLabels.setBrandData(this.brandModel.getName(), label, t1);
            });

            HBox row = new HBox(title, infoField);
            row.setSpacing(30);
            
            this.vbLabels.getChildren().add(row);
        }
    }
    
    private Label createLabel(String label) {
        Label title = new Label(label);
        title.setPrefSize(87, 35);
        title.textFillProperty().setValue(Color.WHITE);
        title.setPrefSize(87, 35);
        title.setFont(Font.font("TechnicLite", 14));
        title.setTextFill(Color.WHITE);
        title.setAlignment(Pos.BASELINE_LEFT);
        
        return title;
    }
    
    
    public void close(String fxml) {
        model.select(this.brandModel.getName());
        model.update();
        ScreenController.closeScreen("EditBrandFXML");
    }
}
