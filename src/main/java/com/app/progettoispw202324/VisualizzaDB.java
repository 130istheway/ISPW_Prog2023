package com.app.progettoispw202324;

import com.app.progettoispw202324.util.Comandi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import model.domain.ui.GestionePerUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.MessageToCommand;

import java.util.List;
import java.util.Objects;

public class VisualizzaDB {
    
    static Logger logger = LogManager.getLogger(VisualizzaDB.class);

    static MessageToCommand messageToCommand = new MessageToCommand();

    private static final String VDB = "VisualizzaDB";

    static GestionePerUI gestionePerUI;

    private static int posizione = 0;
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
        posizione = comandi.vaiSuccessivo(finiti,posizione,successivo,precedente,testoLibero, false);
    }


    public void vaiPrecedente(){
        setComandi();
        List<Object> ritorno = comandi.vaiPrecedente(finiti,posizione,successivo,precedente,testoLibero,false);
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
        comandi.visualizzaCarrello(false,posizione,null);
    }

    public static void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }

    private void setComandi(){
        comandi.setGestionePerUI(gestionePerUI);
    }

}
