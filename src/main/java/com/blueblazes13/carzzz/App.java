package com.blueblazes13.carzzz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static HashMap<String, Parent> screens;

    @Override
    public void start(Stage stage) throws IOException {
        App.screens = new HashMap<>();
        App.screens.put("MainMenuFXML", loadFXML("MainMenuFXML"));
        scene = new Scene(App.screens.get("MainMenuFXML"), 1200, 800);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        if (!App.screens.containsKey(fxml)) {
            App.screens.put(fxml, loadFXML(fxml));
        }
        scene.setRoot(App.screens.get(fxml));
    }

    
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}