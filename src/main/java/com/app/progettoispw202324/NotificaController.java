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

    private static final String NC = "NotificaController";

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
        Comandi.vaiSuccessivo(finiti,posizione,successivo,precedente,testoLibero, NC);
    }


    public void vaiPrecedente(){
        finiti = Comandi.vaiPrecedente(finiti,posizione,successivo,precedente,NC);
    }

    public void conferma(){
        confermaRifiuta("SI");
    }

    public void rifiuta(){
        confermaRifiuta("NO");
    }

    private synchronized void confermaRifiuta(String stringa){

        accetta.setStyle("-fx-background-color: grey;");

        messageToCommand = new MessageToCommand();
        String receive = null;

        messageToCommand.setCommand("CONFERMANOTIFICA");
        messageToCommand.setPayload(String.valueOf(posizione)+"|"+stringa);
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
        Comandi.visualizzaCarrello(false,posizione,gestionePerUI,testoLibero,null);
    }


    public static void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }
}
