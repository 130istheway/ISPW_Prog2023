package com.app.progettoispw202324;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.domain.Role;
import model.domain.ui.GestionePerUI;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginController {
    
    Logger logger = LogManager.getLogger(LoginController.class);

    static GestionePerUI gestionePerUI;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button buttonLogin;

    @FXML
    public void login(ActionEvent event) {
        String ricevi;
        String invio = "user:" + username.getText() + ",pass:" + password.getText();
        gestionePerUI.sendMessage(invio);
        try {
            ricevi = gestionePerUI.getMessage();
        } catch (IOException e){
            ricevi = "non riuscito a recuperare il messaggio";
        }
        System.out.println(ricevi);
        if (ricevi.contains("ACCETTATA")){
            int n = Integer.parseInt(ricevi.substring(ricevi.length()-1));
            buttonLogin.setStyle("-fx-background-color: green;");
            Role ruolo;
            ruolo = Role.values()[Integer.parseInt(ricevi.substring(ricevi.length()-1))];
            gestionePerUI.setCredential(username.getText(), ruolo);
            setControllerMenu(event, n);
        } else if (ricevi.contains("Riprova")){
            buttonLogin.setText("Riprova");
            buttonLogin.setStyle("-fx-background-color: red;");
        } else if (ricevi.contains("STOPIT")){
            buttonLogin.setText("Sbagliato");
            buttonLogin.setStyle("-fx-background-color: black;");
            Platform.exit();
        } else{
            System.out.println("Cose che non capisco");
        }
    }

    public static void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }

    private void setControllerMenu(ActionEvent event, int n){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("menu.fxml"));
            Parent root = fxmlLoader.load();
            MenuController.passGestione(gestionePerUI);
            MenuController.passLivello(n);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Menu");
            stage.show();

        }catch (IOException e){
            logger.error("0x000100   %s", e.getMessage());
        }
    }
}
