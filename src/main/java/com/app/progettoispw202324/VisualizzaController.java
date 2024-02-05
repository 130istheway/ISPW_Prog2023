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

    private static int posizione = 0;
    private boolean finiti = false;

    @FXML
    Button precedente;
    @FXML
    static Button successivo;
    @FXML
    static TextArea testoLibero;

    public void initializa(){
        visualizzaCarrello();
    }

    public void menu(ActionEvent event){
        Comandi.menu(event, gestionePerUI);
    }

    public void vaiSuccessivo(){
        Comandi.vaiSuccessivo(finiti,posizione,successivo,precedente,testoLibero, VC);
    }


    public void vaiPrecedente(){
        finiti = Comandi.vaiPrecedente(finiti,posizione,successivo,precedente,VC);
    }

    public void elimina(){
        Comandi.elimina(posizione,gestionePerUI,testoLibero);
    }

    public static void visualizzaCarrello(){
        Comandi.visualizzaCarrello(true,posizione,gestionePerUI,testoLibero,successivo);
    }

    public static void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }
}
