package com.app.progettoispw202324;

import boundary.BoundaryLogin;
import com.app.progettoispw202324.allertbox.AllertBoxNumeroOrdini;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

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
        gestionePerUI.sendMessage(BoundaryLogin.returnAutentication(username.getText(), password.getText()));
        try {
            ricevi = gestionePerUI.getMessage();
        } catch (IOException e){
            ricevi = "non riuscito a recuperare il messaggio";
            logger.error("0x000103   %s",e.getMessage());
        }
        if (ricevi.contains("ACCETTATA")){
            int n = Integer.parseInt(ricevi.substring(ricevi.length()-1));
            buttonLogin.setStyle("-fx-background-color: green;");
            Role ruolo;
            ruolo = Role.values()[Integer.parseInt(ricevi.substring(ricevi.length()-1))];
            gestionePerUI.setCredential(username.getText(), ruolo);
            if (n<2){
                controllaNotifica();
            }
            setControllerMenu(event, n);
        } else if (ricevi.contains("Riprova")){
            buttonLogin.setText("Riprova");
            buttonLogin.setStyle("-fx-background-color: red;");
        } else if (ricevi.contains("STOPIT")){
            buttonLogin.setText("Sbagliato");
            buttonLogin.setStyle("-fx-background-color: black;");
            Platform.exit();
        } else{
            logger.error("Noon so cosa mi ha ridato");
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
            logger.error("0x000100   %s",e.getMessage());
        }
    }

    private void controllaNotifica(){
        String invioNotifica = "RECUPERANORDINI";
        gestionePerUI.sendMessage(invioNotifica);
        String ordini = null;
        try {
            ordini = gestionePerUI.getMessage();
        } catch (IOException e) {
            ordini = "non riuscito a recuperare il messaggio";
            logger.error("0x000104   %s",e.getMessage());
        }
        if (ordini.contains("SI")) AllertBoxNumeroOrdini.allertOrdini("Allerta Ordini", ordini.substring(4));
    }
}
