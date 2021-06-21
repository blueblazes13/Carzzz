/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueblazes13.carzzz;

import static com.blueblazes13.carzzz.App.loadFXML;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author joeyk
 */
public class ScreenController {
    
    private static ArrayList<String> openedScreens = new ArrayList<>();
    private static ArrayList<Stage> stages = new ArrayList<>();
    
    
    /**
     * Opens a new screen with the fxml as content
     * 
     * @param fxml The content that has to be shown on the screen
     * @return true if screen has been opened. False if not.
     */
    public static boolean newScreen(String fxml) {
        if (ScreenController.openedScreens.contains(fxml)) return false;
        
        try {
            Stage stage = new Stage();
            ScreenController.openedScreens.add(fxml);
            ScreenController.stages.add(stage);
            
            Scene scene = new Scene(loadFXML(fxml), 500, 450);
            stage.setScene(scene);
            
            stage.setOnCloseRequest((WindowEvent e) -> {
                ScreenController.closeScreen(fxml);
            });
            
            stage.show();
            
            return true;
        } catch (IOException ex) {
            closeScreen(fxml);
            ex.printStackTrace(System.out);
            return false;
        }
    }
    
    
    /**
     * Opens a new screen with the fxml as content
     * 
     * @param fxml The content that has to be shown on the screen
     * @param listener The listener that's called after hitting the close button
     * 
     * @return true if screen has been opened. False if not.
     */
    public static boolean newScreen(String fxml, Consumer<String> listener) {
        if (ScreenController.openedScreens.contains(fxml)) return false;
        
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(loadFXML(fxml), 500, 450);
            stage.setScene(scene);
            stage.setOnCloseRequest((WindowEvent e) -> {
                listener.accept(null);
                ScreenController.closeScreen(fxml);
            });
            stage.show();
            ScreenController.openedScreens.add(fxml);
            ScreenController.stages.add(stage);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            return false;
        }
    }
    
    
    /**
     * Closes the screen that has the fxml has content
     * 
     * @param fxml The fxml that is shown on the screen
     */
    public static void closeScreen(String fxml) {
        if (ScreenController.openedScreens.contains(fxml)) {
            int index = ScreenController.openedScreens.indexOf(fxml);
            Stage stage = ScreenController.stages.get(index);
            stage.close();
            ScreenController.stages.remove(stage);
            ScreenController.openedScreens.remove(fxml);
            
        }
    }
 
    
    /**
     * Adds a listener to an already opened screen.
     * 
     * @param fxml The fxml that is shown on the screen
     * @param listener The listener that's called after hitting the close button
     */
    public static void addListener(String fxml, Consumer<String> listener) {
        if (ScreenController.openedScreens.contains(fxml)) {
            int index = ScreenController.openedScreens.indexOf(fxml);
            Stage stage = ScreenController.stages.get(index);
            
            stage.addEventHandler(EventType.ROOT, (Event e) -> {
                if (e instanceof WindowEvent) {
                    WindowEvent we = (WindowEvent) e;
                    if (we.getEventType().getName().equals("WINDOW_CLOSE_REQUEST")) {
                        listener.accept(null);
                        ScreenController.closeScreen(fxml);
                    }
                }
            });
        }
    }
    
    public static void changeScreen(String fxml) {
        try {
            App.setRoot(fxml);
        } catch (IOException ex) {
            System.out.println("Error while trying to change to screen: " + fxml);
            ex.printStackTrace();
        }
    }
    
}
