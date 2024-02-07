package com.app.progettoispw202324;

import com.app.progettoispw202324.util.Comandi;
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
    
    Logger logger = LogManager.getLogger(OrdiniCarrelloController.class);

    MessageToCommand messageToCommand = new MessageToCommand();

    static GestionePerUI gestionePerUI;

    @FXML
    TextArea testoLibero;

    Comandi comandi = new Comandi(gestionePerUI, testoLibero);

    public void menu(ActionEvent event){
        comandi.menu(event, 0);
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
            StringBuilder bld = new StringBuilder();
            for (String string : ordini) {
                bld = bld.append(string + "\n");
            }
            testoLibero.setText(bld.toString());
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
