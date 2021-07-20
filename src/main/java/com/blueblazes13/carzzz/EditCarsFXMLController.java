
package com.blueblazes13.carzzz;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import com.blueblazes13.carzzz.model.BrandModel;
import com.blueblazes13.carzzz.model.CarModel;
import com.blueblazes13.carzzz.model.CarzzzModel;
import com.blueblazes13.carzzz.model.InfoLabels;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EditCarsFXMLController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox hbItemField;

    @FXML
    private TextField tfName;
    
    @FXML
    private MenuItem btnClose;

    @FXML
    private ImageView ivCarImage;
    
    @FXML
    private MenuItem btnSave;

    @FXML
    private Menu editMenu;

    @FXML
    private MenuItem btnAdd;

    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnDelete;
    
    @FXML
    private VBox vbLabels;

    private BrandModel model;
    private CarModel carModel;
    
    
    @FXML
    void initialize() {
        CarzzzModel.setController("EditCarsFXML", this);
        this.model = CarzzzModel.getModel().getSelectedBrand();
        
        this.btnBack.setOnAction(this::back);
        this.btnDelete.setOnAction(this::delete);
        this.ivCarImage.setOnMouseClicked((MouseEvent me) -> {
            File image = CarzzzModel.showImageChooser();
            if (image != null) this.ivCarImage.setImage(CarzzzModel.fileToImage(image));
            CarzzzModel.getModel().getSelectedBrand().getSelectedCar().setCarImage(image);
        });
        
        update();
    }
    
    
    public void update() {
        if (this.model.getSelectedCar() == null) {
            this.carModel = new CarModel("No name set");
            this.model.addCar(this.carModel);
            this.model.selectCar(this.carModel);
        } else {
            this.carModel = this.model.getSelectedCar();
        }
        this.tfName.setText(this.carModel.getName());
        
        if (this.carModel.getCarImage()== null) {
            this.ivCarImage.setImage(CarzzzModel.stringToImage("clickToAdd.png"));
        } else {
            this.ivCarImage.setImage(CarzzzModel.stringToImage(this.carModel.getCarImage()));
        }
        
        this.vbLabels.getChildren().clear();
        
        InfoLabels labels = CarzzzModel.getModel().infoLabels;
        HashMap<String, String> carData = labels.getCarData(this.carModel.getName());
        ArrayList<String> carLabels = labels.getCarLabels();
        
        Label title1 = new Label("Name");
        title1.setPrefSize(87, 35);
        title1.textFillProperty().setValue(Color.BLACK);
        title1.setPrefSize(87, 35);
        title1.setFont(Font.font("TechnicLite", 14));
        title1.setTextFill(Color.BLACK);
        title1.setAlignment(Pos.BASELINE_LEFT);

        TextField infoField1 = new TextField();
        infoField1.setStyle("-fx-background-color: rgb(240, 240, 240)");
        infoField1.setPrefWidth(180);
        infoField1.setText(this.carModel.getName());
        infoField1.textProperty().addListener((ov, t, t1) -> {
            this.carModel.setName(t1);
            this.tfName.setText(t1);
            labels.renameCar(t, t1);
        });

        HBox row1 = new HBox(title1, infoField1);
        row1.setSpacing(30);
        this.vbLabels.getChildren().add(row1);
        System.out.println(carData);
        
        for (String label: carLabels) {
            Label title = new Label(label);
            title.setPrefSize(87, 35);
            title.textFillProperty().setValue(Color.BLACK);
            title.setPrefSize(87, 35);
            title.setFont(Font.font("TechnicLite", 14));
            title.setTextFill(Color.BLACK);
            title.setAlignment(Pos.BASELINE_LEFT);
            
            TextField infoField = new TextField();
            infoField.setStyle("-fx-background-color: rgb(240, 240, 240)");
            infoField.setPrefWidth(180);
            infoField.setText(CarzzzModel.infoLabels.getCarData(this.carModel.getName()).get(label));
            infoField.textProperty().addListener((ov, t, t1) -> {
                CarzzzModel.getModel().infoLabels.setCarData(this.carModel.getName(), label, t1);
            });
            
            HBox row = new HBox(title, infoField);
            row.setSpacing(30);
            this.vbLabels.getChildren().add(row);
        }
    }

    
    private void back(ActionEvent ae) {
        this.model.deselectCar();
        ((CarsMenuFXMLController)CarzzzModel.getController("CarsMenuFXML")).update();
        ScreenController.changeScreen("CarsMenuFXML");
    }
    
    
    private void delete(ActionEvent ae) {
        this.model.removeCar(this.carModel);
        this.back(ae);
    }
}