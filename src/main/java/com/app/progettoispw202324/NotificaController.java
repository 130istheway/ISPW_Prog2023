package com.app.progettoispw202324;

import com.app.progettoispw202324.util.Comandi;
import com.app.progettoispw202324.util.PrintOnTextField;
import com.app.progettoispw202324.util.StringToOrdini;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import util.MessageToCommand;
import java.io.IOException;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotificaController {
    
    static Logger logger = LogManager.getLogger(NotificaController.class);

    private static final String ER = "Errore nel recupero del messaggio";

    private static final String NOTIFICACONTROLLER = "NotificaController";

    static MessageToCommand messageToCommand = new MessageToCommand();
    static GestionePerUI gestionePerUI;

    private static int posizione = 0;
    private boolean finiti = false;
    @FXML
    Button precedente;
    @FXML
    Button successivo;
    @FXML
    static TextArea testoLibero;
    @FXML
    Button accetta;
    @FXML
    Button rifiuta;



    public void menu(ActionEvent event){
        Comandi.menu(event, gestionePerUI);
    }

    public void vaiSuccessivo(){
        Comandi.vaiSuccessivo(finiti,posizione,successivo,precedente,testoLibero, NOTIFICACONTROLLER);
    }


    public void vaiPrecedente(){
        Comandi.vaiPrecedente(finiti,posizione,successivo,precedente,NOTIFICACONTROLLER);
    }

    public void conferma(){
        rifiuta.setStyle("-fx-background-color: grey;");
        messageToCommand = new MessageToCommand();
        String receive = null;

        messageToCommand.setCommand("CONFERMANOTIFICA");
        messageToCommand.setPayload(String.valueOf(posizione)+"|SI");
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try{
            receive = gestionePerUI.getMessage();
        }catch (IOException e){
            logger.error(ER);
            Platform.exit();
        }
        messageToCommand.fromMessage(receive);
        if (Objects.equals(messageToCommand.getCommand(), "NO")){
            testoLibero.setText("NON E ANDATA BENE");
            accetta.setStyle("-fx-background-color: red;");
        }else if (Objects.equals(messageToCommand.getCommand(), "SI")){
            testoLibero.setText(messageToCommand.getPayload());
            accetta.setStyle("-fx-background-color: green;");
        }
        visualizzaCarrello();
    }

    public void rifiuta(){
        accetta.setStyle("-fx-background-color: grey;");

        messageToCommand = new MessageToCommand();
        String receive = null;

        messageToCommand.setCommand("CONFERMANOTIFICA");
        messageToCommand.setPayload(String.valueOf(posizione)+"|NO");
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try{
            receive = gestionePerUI.getMessage();
        }catch (IOException e){
            logger.error(ER);
            Platform.exit();
        }
        messageToCommand.fromMessage(receive);
        if (Objects.equals(messageToCommand.getCommand(), "NO")){
            testoLibero.setText(messageToCommand.getPayload());
            rifiuta.setStyle("-fx-background-color: red;");
        }else if (Objects.equals(messageToCommand.getCommand(), "SI")){
            testoLibero.setText(messageToCommand.getPayload());
            rifiuta.setStyle("-fx-background-color: green;");
        }
        visualizzaCarrello();
    }

    public static void visualizzaCarrello(){
        messageToCommand = new MessageToCommand();
        String receive = null;
        messageToCommand.setCommand("VISUALIZZANOTI");
        messageToCommand.setPayload(String.valueOf(posizione));
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try{
            receive = gestionePerUI.getMessage();
        }catch (IOException e){
            logger.error(ER);
            Platform.exit();
        }
        messageToCommand.fromMessage(receive);
        if (Objects.equals(messageToCommand.getCommand(), "NO")){
            testoLibero.setText("Articoli Finiti");
        }else if (Objects.equals(messageToCommand.getCommand(), "SI")){
            PrintOnTextField.stampaArticolisuTextBox(StringToOrdini.coverti(messageToCommand.getPayload()), testoLibero);
        }
    }


    public static void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }
}
