package com.app.progettoispw202324;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import util.MessageToCommand;

import java.io.IOException;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MenuController {
    
    Logger logger = LogManager.getLogger(MenuController.class);
    
    private static final String IMPOSTAROSSO = "-fx-background-color: red;";
    private static final String IMPOSTAVERDE = "-fx-background-color: green;";
    private static final String STOPIT = "STOPIT";

    MessageToCommand messageToCommand = new MessageToCommand();

    FXMLLoader fxmlLoader;
    Parent root;
    static GestionePerUI gestionePerUI;
    static int livello;

    @FXML
    Button scegliNegozio;

    @FXML
    Button confermaCarrello;

    @FXML
    Button ordine;

    public void scegliNegozio() {
        if (gestionePerUI.getNegozio() == null) {
            AllertBox.allertSceltaNegozio("Scelta", "L'id del negozio?", gestionePerUI, false);
            scegliNegozio.setStyle(IMPOSTAROSSO);
        }else {
            AllertBox.allertSceltaNegozio("Scelta", "L'id del negozio è " + gestionePerUI.getNegozio(), gestionePerUI, true);
            scegliNegozio.setStyle(IMPOSTAVERDE);
        }
    }

    public void visualizza(ActionEvent event){
        if (livello>=3){return;}
        String input;
        messageToCommand.setCommand("VISUALIZZA");
        messageToCommand.setPayload(null);
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try {
            input = gestionePerUI.getMessage();
        } catch (IOException e){
            input = "NO";
        }

        if (Objects.equals(input, "OK")) {
            try {
                fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("VisualizzaCarrello.fxml"));
                root = fxmlLoader.load();
                VisualizzaController.passGestione(gestionePerUI);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Visualizza");
                stage.show();

            } catch (IOException e) {
                logger.error("0x000110    %s", e.getMessage());
                Platform.exit();
            }
        }   else if (input.contains(STOPIT)){
            Platform.exit();
        }
    }

    public void inserisci(ActionEvent event) {
        if (livello>=3){return;}
        String input;
        if (gestionePerUI.getNegozio() == null) {
            scegliNegozio();
        } else {
            messageToCommand.setCommand("AGGIUNGILISTA");
            messageToCommand.setPayload(gestionePerUI.getNegozio());
            gestionePerUI.sendMessage(messageToCommand.toMessage());
            try {
                input = gestionePerUI.getMessage();
            } catch (IOException e){
                input = "NO";
            }
            if (Objects.equals(input, "OK")) {
                try {
                    fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("InserisciCarrello.fxml"));
                    root = fxmlLoader.load();
                    InsController.passGestione(gestionePerUI);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Inserisci");
                    stage.show();

                } catch (IOException e) {
                    logger.error("0x000111    %s", e.getMessage());
                    Platform.exit();
                }
            } else if (input.contains(STOPIT)){
            Platform.exit();
        }
        }
    }

    public void conferma() {
        if (livello>=3){return;}
        String input;
        if (gestionePerUI.getNegozio() == null){
            scegliNegozio();
        }else {
            messageToCommand.setCommand("CONFERMALISTA");
            messageToCommand.setPayload(gestionePerUI.getNegozio());
            gestionePerUI.sendMessage(messageToCommand.toMessage());
            try {
                input = gestionePerUI.getMessage();
            } catch (IOException e) {
                input = "NO";
            }
            if (Objects.equals(input, "OK")) {
                confermaCarrello.setStyle(IMPOSTAVERDE);
                messageToCommand.setCommand("RESETNEGOZIO");
                messageToCommand.setPayload(null);
                gestionePerUI.sendMessage(messageToCommand.toMessage());
            } else if (input.contains(STOPIT)){
            Platform.exit();
            } else {
                confermaCarrello.setStyle(IMPOSTAROSSO);
            }
        }
    }

    public void confermeRicevute(ActionEvent event) {
        if (livello>=3){return;}
        try {
            fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("OrdiniCarrello.fxml"));
            root = fxmlLoader.load();
            OrdiniCarrelloController.passGestione(gestionePerUI);


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Verifica se le tue prenotazioni sono accettate");
            stage.show();
        }catch (IOException e){
            logger.error("0x000112    %s", e.getMessage());
            Platform.exit();
        }
    }


    public void aggiungiDaDb(ActionEvent event) {
        if (livello>=3){return;}
        try {
            fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("CosaInserire.fxml"));
            root = fxmlLoader.load();
            InserisciController.passGestione(gestionePerUI);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Aggiungi al DB");
            stage.show();
        } catch (IOException e) {
            logger.error("0x000113    %s", e.getMessage());
            Platform.exit();
        }
    }

    public void visualizzaDaDb (ActionEvent event) {
        if (livello>=2){return;}

        String input;
        messageToCommand.setCommand("VISUALIZZAARTICOLODB");
        messageToCommand.setPayload(null);
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try {
            input = gestionePerUI.getMessage();
        } catch (IOException e){
            input = "NO";
        }
        if (Objects.equals(input, "OK")) {
            try {
                fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("VisualizzaDB.fxml"));
                root = fxmlLoader.load();
                VisualizzaDB.passGestione(gestionePerUI);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Visualizza dal DB");
                stage.show();

            } catch (IOException e) {
                logger.error("0x000114    %s", e.getMessage());
                Platform.exit();
            }
        }
    }


    public void notificheDaAccettare(ActionEvent event) {
        if (livello>=2){return;}

        String input;
        messageToCommand.setCommand("NOTIFICA");
        messageToCommand.setPayload(null);
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try {
            input = gestionePerUI.getMessage();
        } catch (IOException e){
            input = "NO";
        }
        if (Objects.equals(input, "OK")) {
            ordine.setText("ORDINE");
            try {
                fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("VisualizzaNotifica.fxml"));
                root = fxmlLoader.load();
                NotificaController.passGestione(gestionePerUI);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Visualizza Ordini");
                stage.show();
            } catch (IOException e) {
                logger.error("0x000115    %s", e.getMessage());
                Platform.exit();
            }
        } else if (Objects.equals(input, "NULL")) {
            ordine.setText("Nessun Ordine");
        }
    }
    
    public void ordiniPerOggi() {
        if (livello>=2){return;}
        VisualizzaController.passGestione(gestionePerUI);
    }

    public void logOut() {
        messageToCommand.setCommand("EXIT");
        messageToCommand.setPayload(null);
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        Platform.exit();
    }


    public static void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }
    public static void passLivello(int n){
        livello = n;
    }
    public static void setNegozio(String negozio){
        gestionePerUI.setNegozio(negozio);
    }
}
