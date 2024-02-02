package com.app.progettoispw202324;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.getLogger;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ControllerLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("dfbhvffvbifvbifivnfvidnvndfinjinvjivffvj");

        // In the caller class
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("ControllerLogin.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.setValue("piuytrewqasdfghjklmnbvcxz"); // Pass the value to the second controller


        launch();
    }
}