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

public class OrdiniCarrelloController {
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
            throw new RuntimeException(e);
        }
    }

    public void visualizza(){
        MessageToCommand messageToCommand = new MessageToCommand();
        String receive = null;
        messageToCommand.setCommand("ORDINI");
        messageToCommand.setPayload(null);
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try{
            receive = gestionePerUI.getMessage();
        }catch (IOException e){
            System.err.println("Errore nel recupero del messaggio");
            Platform.exit();
        }
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
            testoLibero.setText("Nessun ordine che deve essere confermato");
        }
    }

    public void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }


}
