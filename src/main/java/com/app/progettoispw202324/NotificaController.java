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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import util.MessageToCommand;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotificaController {
    
    static Logger logger = LogManager.getLogger(NotificaController.class);

    private static final String ER = "Errore nel recupero del messaggio";

    private static final String NC = "NotificaController";
    private static final String SETGREY = "-fx-background-color: grey;";

    static MessageToCommand messageToCommand = new MessageToCommand();
    static GestionePerUI gestionePerUI;

    private int posizione = 0;
    private boolean finiti = false;
    @FXML
    Button precedente;
    @FXML
    Button successivo;
    @FXML
    TextArea testoLibero;
    @FXML
    Button accetta;
    @FXML
    Button rifiuta;
    @FXML
    TextField visualizza;

    Comandi comandi = new Comandi(gestionePerUI, testoLibero);


    public void menu(ActionEvent event){
        setComandi();
        comandi.menu(event, 2);
    }

    public void vaiSuccessivo(){
        setComandi();
        posizione = comandi.vaiSuccessivo(finiti,posizione,successivo,precedente,testoLibero, false);
    }

    public void vaiPrecedente(){
        setComandi();
        List<Object> ritorno = comandi.vaiPrecedente(finiti,posizione,successivo,precedente,testoLibero,false);
        finiti = (boolean)ritorno.get(0);
        posizione = (Integer)ritorno.get(1);

    }

    public void conferma(){
        confermaRifiuta("SI");
    }

    public void rifiuta(){
        confermaRifiuta("NO");
    }

    private synchronized void confermaRifiuta(String stringa){
        if (Objects.equals(stringa, "SI")) {
            accetta.setStyle("-fx-background-color: blue;");
        }else{
            rifiuta.setStyle("-fx-background-color: blue;");
        }

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
            visualizza.setStyle("-fx-background-color: red;");
            accetta.setStyle(SETGREY);
            rifiuta.setStyle(SETGREY);

        }else if (Objects.equals(messageToCommand.getCommand(), "SI")){
            testoLibero.setText(messageToCommand.getPayload());
            visualizza.setStyle("-fx-background-color: green;");
            accetta.setStyle(SETGREY);
            rifiuta.setStyle(SETGREY);
        }
        visualizzaCarrello();
    }

    public void visualizzaCarrello(){
        setComandi();
        comandi.setTestoLibero(testoLibero);
        comandi.visualizzaCarrello(false,posizione,null);
    }


    public static void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }

    private void setComandi(){
        comandi.setGestionePerUI(gestionePerUI);
    }

}
