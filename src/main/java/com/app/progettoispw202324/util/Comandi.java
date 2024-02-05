package com.app.progettoispw202324.util;

import com.app.progettoispw202324.ClientApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.MessageToCommand;

import java.io.IOException;

public class Comandi {
    static Logger logger = LogManager.getLogger(Comandi.class);
    MessageToCommand messageToCommand = new MessageToCommand();

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
    
}
