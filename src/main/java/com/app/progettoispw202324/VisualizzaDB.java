package com.app.progettoispw202324;

import com.app.progettoispw202324.util.Comandi;
import com.app.progettoispw202324.util.PrintArticoli;
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
import util.ConvertiStringToArticolo;
import util.MessageToCommand;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VisualizzaDB {
    
    static Logger logger = LogManager.getLogger(VisualizzaDB.class);

    static MessageToCommand messageToCommand = new MessageToCommand();

    private static final String VISUALIZZADB = "VisualizzaDB";

    static GestionePerUI gestionePerUI;

    private static int posizione = 0;
    private boolean finiti = false;

    @FXML
    Button precedente;
    @FXML
    Button successivo;
    @FXML
    static TextArea testoLibero;

    public void menu(ActionEvent event){
        Comandi.menu(event, gestionePerUI);
    }

    public void vaiSuccessivo(){
        Comandi.vaiSuccessivo(finiti,posizione,successivo,precedente,testoLibero, VISUALIZZADB);
    }


    public void vaiPrecedente(){
        Comandi.vaiPrecedente(finiti,posizione,successivo,precedente,VISUALIZZADB);
    }

    public void elimina(){
        Comandi.elimina(posizione,gestionePerUI,testoLibero);
    }

    public static void visualizzaCarrello(){
        messageToCommand = new MessageToCommand();
        String receive = "";
        messageToCommand.setCommand("VISUALIZZAART");
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
            testoLibero.setText("Articoli Finiti");
        }else if (Objects.equals(messageToCommand.getCommand(), "SI")){
            String articolo = messageToCommand.getPayload();
            List<String> lista = ConvertiStringToArticolo.convertToListStringFromString(articolo);
            PrintArticoli.stampaArticolisuTextBox(lista, testoLibero);
        }
    }

    public static void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }

}
