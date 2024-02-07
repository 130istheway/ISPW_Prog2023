package com.app.progettoispw202324;

import com.app.progettoispw202324.util.Comandi;
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

import com.app.progettoispw202324.util.PrintArticoli;

public class VisualizzaController {
    
    static Logger logger = LogManager.getLogger(VisualizzaController.class);

    static MessageToCommand messageToCommand = new MessageToCommand();

    private static final String VC = "VisualizzaController";

    static GestionePerUI gestionePerUI;

    private int posizione = 0;
    private boolean finiti = false;

    @FXML
    Button precedente;
    @FXML
    Button successivo;
    @FXML
    TextArea testoLibero;

    Comandi comandi = new Comandi(gestionePerUI, testoLibero);


    public void menu(ActionEvent event){
        setComandi();
        comandi.menu(event, 2);
    }

    public void vaiSuccessivo(){
        setComandi();
        posizione = comandi.vaiSuccessivo(finiti,posizione,successivo,precedente,testoLibero, true);
    }


    public void vaiPrecedente(){
        setComandi();
        List<Object> ritorno = comandi.vaiPrecedente(finiti,posizione,successivo,precedente,testoLibero,true);
        finiti = (boolean)ritorno.get(0);
        posizione = (Integer)ritorno.get(1);
    }

    public void elimina(){
        setComandi();
        comandi.elimina(posizione,testoLibero);
    }

    public void visualizzaCarrello(){
        setComandi();
        comandi.setTestoLibero(testoLibero);
        comandi.visualizzaCarrello(true,posizione,successivo);
    }

    public static void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }

    private void setComandi(){
        comandi.setGestionePerUI(gestionePerUI);
    }
}
