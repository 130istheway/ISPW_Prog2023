package com.app.progettoispw202324;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import util.MessageToCommand;

import java.io.IOException;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrdiniCarrelloController {
    
    Logger logger = LogManager.getLogger(AllerBoxPerInserimentoArticoli.class);

    MessageToCommand messageToCommand = new MessageToCommand();

    static GestionePerUI gestionePerUI;

    @FXML
    TextArea testoLibero;


    public void menu(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("menu.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("0x004001" + e.getMessage());
            Platform.exit();
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

    public void visualizza(){
        messageToCommand = new MessageToCommand();
        String receive = null;
        messageToCommand.setCommand("ORDINI");
        messageToCommand.setPayload(null);
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        receive = messaggio();
        assert receive != null;
        messageToCommand.fromMessage(receive);
        if (Objects.equals(messageToCommand.getCommand(), "PREGO")) {
            String[] ordini = messageToCommand.getPayload().split("_");
            String str = "";
            for (String string : ordini) {
                str = str + string + "\n";
            }
            testoLibero.setText(str);
        } else if (Objects.equals(messageToCommand.getCommand(),"STOPIT")){
        Platform.exit();
        }else{
            testoLibero.setText(messageToCommand.getPayload());
        }
    }

    public static void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }


}
