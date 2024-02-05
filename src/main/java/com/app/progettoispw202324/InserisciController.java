package com.app.progettoispw202324;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import util.MessageToCommand;

import java.io.IOException;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InserisciController {

    Logger logger = LogManager.getLogger(AllerBoxPerInserimentoArticoli.class);

    private static final String TAG = "menu.fxml";
    private static final String impostarosso = "-fx-background-color: red;";

    MessageToCommand messageToCommand = new MessageToCommand();

    static GestionePerUI gestionePerUI;
    static String lista;
    static boolean giusto;

    FXMLLoader fxmlLoader;
    Parent root;
    String cosaInserisco;

    @FXML
    TextField infoDiInserire;

    @FXML
    Button pane;
    @FXML
    Button pizza;

    public void menu(ActionEvent event){
        try {
            fxmlLoader = new FXMLLoader(ClientApplication.class.getResource(TAG));
            root = fxmlLoader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("Failed to load");
            Platform.exit();
        }
    }

    public void setPane(ActionEvent event){
        String receive = "";
        cosaInserisco = "pane";
        AllerBoxPerInserimentoArticoli.allertSceltaNegozio(cosaInserisco);
        if (giusto) {
            messageToCommand.setCommand("AGGIUNGIARTICOLODB");
            messageToCommand.setPayload(lista);
            gestionePerUI.sendMessage(messageToCommand.toMessage());
            receive = messaggio();
            messageToCommand.fromMessage(receive);
            pane.setStyle("-fx-background-color: green;");
            if (Objects.equals(messageToCommand.getCommand(), "NO")) {
                infoDiInserire.setStyle(impostarosso);
            } else if (Objects.equals(messageToCommand.getCommand(), "SI")) {
                infoDiInserire.setText("Articolo Aggiunto");
            } else if (Objects.equals(messageToCommand.getCommand(), "NON AUTORIZATO")) {
                try {
                    fxmlLoader = new FXMLLoader(ClientApplication.class.getResource(TAG));
                    root = fxmlLoader.load();
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    logger.error("Failed to load");
                    Platform.exit();
                }
            }
        }else{
            pane.setStyle(impostarosso);
        }
    }

    private String messaggio(){
        try {
            return gestionePerUI.getMessage();
        } catch (IOException e) {
            logger.error("Errore nel recupero del messaggio");
            Platform.exit();
        }
        return null;
    }

    public void setPizza(ActionEvent event){
        String receive = "";
        cosaInserisco = "pizza";
        AllerBoxPerInserimentoArticoli.allertSceltaNegozio(cosaInserisco);
        if (giusto) {
            messageToCommand.setCommand("AGGIUNGIARTICOLODB");
            messageToCommand.setPayload(lista);
            gestionePerUI.sendMessage(messageToCommand.toMessage());
            receive = messaggio();
            messageToCommand.fromMessage(receive);
            pizza.setStyle("-fx-background-color: green;");
            if (Objects.equals(messageToCommand.getCommand(), "NO")) {
                infoDiInserire.setStyle(impostarosso);
            } else if (Objects.equals(messageToCommand.getCommand(), "SI")) {
                infoDiInserire.setText("Articolo Aggiunto");
            } else if (Objects.equals(messageToCommand.getCommand(), "NON AUTORIZATO")) {
                try {
                    fxmlLoader = new FXMLLoader(ClientApplication.class.getResource(TAG));
                    root = fxmlLoader.load();
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    logger.error("Failed to load");
                    Platform.exit();
                }
            }
        }else{
            pizza.setStyle(impostarosso);
        }
    }

    public static void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }

    public static void passLista(String temporaneo){
        lista = temporaneo;
    }

    public static void passGiusto(boolean lollo){
        giusto = lollo;
    }
}
