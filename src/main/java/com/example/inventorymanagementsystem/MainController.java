package com.example.inventorymanagementsystem;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * @author place your name here
 */
public class MainController extends Application {
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
        
        
    }


    public static void main(String[] args) {
        launch();
    }
    

}
