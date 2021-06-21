package com.blueblazes13.carzzz;

import com.blueblazes13.carzzz.model.CarzzzModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LabelsFXMLController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox VBoxLabels;

    @FXML
    private Button btnNewLabel;

    @FXML
    private Button btnClose;
    
    @FXML
    private TextField tfLabelName;

    @FXML
    void initialize() {
        setLabels();
        this.btnNewLabel.setOnMouseClicked((MouseEvent me) -> {
            if (tfLabelName.getText() != "") {
                CarzzzModel.infoLabels.addBrandLabel(this.tfLabelName.getText());
                this.setLabels();
            }
        });
        
        ScreenController.addListener("LabelsFXML", this::close);
    }
    
    
    private void close(String fxml) {
        ScreenController.closeScreen("LabelsFXML");
    }
    
    
    private void setLabels() {
        this.VBoxLabels.getChildren().clear();
        for (String label: CarzzzModel.infoLabels.getBrandLabels()) {
            Label title = new Label(label);
            title.setPrefSize(80, 30);
            title.textFillProperty().setValue(Color.WHITE);
            title.setLayoutX(70);
            title.setPrefSize(87, 35);
            title.setFont(Font.font("TechnicLite", 14));
            title.setTextFill(Color.WHITE);
            title.setAlignment(Pos.BASELINE_LEFT);
            title.setOnMouseClicked((MouseEvent me) -> {removeLabel(label);});
            
            this.VBoxLabels.getChildren().add(title);
        }
    }
    
    private void removeLabel(String label) {
        CarzzzModel.infoLabels.removeBrandLabel(label);
        System.out.println("pressed!");
        this.setLabels();
    }
}
