package com.app.progettoispw202324.util;

import com.app.progettoispw202324.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.MessageToCommand;

import java.io.IOException;
import java.util.Objects;

public class Comandi {
    static Logger logger = LogManager.getLogger(Comandi.class);
    static MessageToCommand messageToCommand = new MessageToCommand();

    public static void menu(ActionEvent event, GestionePerUI gestionePerUI){
        messageToCommand.setCommand("EXIT");
        messageToCommand.setPayload("0");
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("menu.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("didn't load 0x02001");
            Platform.exit();
        }
    }


    public static void vaiSuccessivo(Boolean finiti, Integer posizione, Button successivo, Button precedente, TextArea testoLibero, String lollo){
        if (!finiti) {
            posizione++;
            switch(lollo){
                case "InsCotroller":
                    InsController.visualizzaCarrello();
                    break;
                case "NotificaController":
                    NotificaController.visualizzaCarrello();
                    break;
                case "VisualizzaController":
                    VisualizzaController.visualizzaCarrello();
                    break;
                case "VisualizzaDB":
                    VisualizzaDB.visualizzaCarrello();
            }
            if (posizione > 0) {
                precedente.setText("<<");
            }
            if (Objects.equals(testoLibero.getText(),"Articoli Finiti")) {
                successivo.setText("|");
                finiti = true;
            }
        }
    }

    public static boolean vaiPrecedente(Boolean finiti, Integer posizione, Button successivo, Button precedente, String lollo){
        if (posizione == 0){
            switch(lollo){
                case "InsCotroller":
                    InsController.visualizzaCarrello();
                    break;
                case "NotificaController":
                    NotificaController.visualizzaCarrello();
                    break;
                case "VisualizzaController":
                    VisualizzaController.visualizzaCarrello();
                    break;
                case "VisualizzaDB":
                    VisualizzaDB.visualizzaCarrello();
            }
            successivo.setText(">>");
        }else {
            posizione--;
            switch(lollo){
                case "InsCotroller":
                    InsController.visualizzaCarrello();
                    break;
                case "NotificaController":
                    NotificaController.visualizzaCarrello();
                    break;
                case "VisualizzaController":
                    VisualizzaController.visualizzaCarrello();
                    break;
                case "VisualizzaDB":
                    VisualizzaDB.visualizzaCarrello();
            }
            }
            if (posizione < 1) {
                precedente.setText("|");
                return false;
            }
            return finiti;
    }

    public static void elimina(Integer posizione, GestionePerUI gestionePerUI, TextArea testoLibero){
        messageToCommand = new MessageToCommand();
        String receive = null;

        messageToCommand.setCommand("RIMUOVIART");
        messageToCommand.setPayload(String.valueOf(posizione));
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try{
            receive = gestionePerUI.getMessage();
        }catch (IOException e){
            logger.error("Errore nel recupero del messaggio");
            Platform.exit();
        }
        messageToCommand.fromMessage(receive);
        if (Objects.equals(messageToCommand.getCommand(), "NO")){
            testoLibero.setText(messageToCommand.getPayload());
        }else if (Objects.equals(messageToCommand.getCommand(), "SI")){
            testoLibero.setText("Articolo Eliminato");
        }
    }
}

