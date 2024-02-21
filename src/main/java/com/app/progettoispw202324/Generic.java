package com.app.progettoispw202324;

import com.app.progettoispw202324.util.Comandi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import model.domain.ui.GestionePerUI;
import util.MessageToCommand;

import java.util.List;

public class Generic {

    static MessageToCommand messageToCommand = new MessageToCommand();

    static GestionePerUI gestionePerUI;

    protected int posizione = 0;
    private boolean finiti = false;

    @FXML
    Button precedente;
    @FXML
    Button successivo;
    @FXML
    TextArea testoLibero;

    protected Comandi comandi = new Comandi(gestionePerUI, testoLibero);


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

    public void visualizzaCarrello(){
        setComandi();
        comandi.setTestoLibero(testoLibero);
        comandi.visualizzaCarrello(true,posizione,successivo);
    }

    protected void setComandi(){
        comandi.setGestionePerUI(gestionePerUI);
    }
}
