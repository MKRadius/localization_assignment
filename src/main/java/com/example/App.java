package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("display.fxml"));
        Parent root = loader.load();

        stage.setTitle("Database Localization");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
