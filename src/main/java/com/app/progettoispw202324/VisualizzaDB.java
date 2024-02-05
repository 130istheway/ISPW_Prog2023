package com.app.progettoispw202324;

import com.app.progettoispw202324.util.Comandi;
import com.app.progettoispw202324.util.PrintArticoli;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import model.domain.ui.GestionePerUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConvertiStringToArticolo;
import util.MessageToCommand;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
        finiti = Comandi.vaiPrecedente(finiti,posizione,successivo,precedente,VISUALIZZADB);
    }

    public void elimina(){
        Comandi.elimina(posizione,gestionePerUI,testoLibero);
    }

    public static void visualizzaCarrello(){
        Comandi.visualizzaCarrello(false,posizione,gestionePerUI,testoLibero,null);
    }

    public static void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }

}
