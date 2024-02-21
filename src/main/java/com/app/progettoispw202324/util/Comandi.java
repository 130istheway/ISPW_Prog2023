package com.app.progettoispw202324.util;

import boundary.BoundaryBasicController;
import boundary.BoundaryUserControl;
import com.app.progettoispw202324.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConvertiStringToArticolo;
import util.MessageToCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Comandi {
    Logger logger = LogManager.getLogger(Comandi.class);
    MessageToCommand messageToCommand = new MessageToCommand();

    GestionePerUI gestionePerUI;
    TextArea testoLibero;

    public Comandi(GestionePerUI lollo, TextArea testoLibero2) {
        gestionePerUI = lollo;
        testoLibero = testoLibero2;
    }

    public void menu(ActionEvent event, int nExit){
        for (int i=0; i<nExit; i++){
            gestionePerUI.sendMessage(BoundaryBasicController.RETURNEXITCOMMAND);
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("menu.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("didn't load 0x02001");
            Platform.exit();
        }
    }


    public int vaiSuccessivo(Boolean finiti, Integer posizione, Button successivo, Button precedente, TextArea testoLibero2, boolean scelta){
        testoLibero = testoLibero2;
        if (Boolean.FALSE.equals(finiti)) {
            posizione++;
            visualizzaCarrello(scelta, posizione, successivo);
            if (posizione > 0) {
                precedente.setText("<<");
            }
            if (Objects.equals(testoLibero.getText(),"Articoli Finiti")) {
                finiti = Boolean.TRUE;
                successivo.setText("|");
            }
        }
        return posizione;
    }

    public List<Object> vaiPrecedente(Boolean finiti, Integer posizione, Button successivo, Button precedente, TextArea testoLibero2, boolean scelta){
        List<Object> ritorno = new ArrayList<>();
        testoLibero = testoLibero2;
        if (posizione == 0){
            visualizzaCarrello(scelta, posizione, successivo);
            successivo.setText(">>");
        }else {
            posizione--;
            visualizzaCarrello(scelta, posizione, successivo);
            }
        if (posizione < 1) {
            precedente.setText("|");
            ritorno.add(false);
            ritorno.add(posizione);
            return ritorno;
        }
        ritorno.add(finiti);
        ritorno.add(posizione);
        return ritorno;
    }

    private String riceviMessaggio(String string){
        String receive = "";
        gestionePerUI.sendMessage(string);
        try{
            receive = gestionePerUI.getMessage();
        }catch (IOException e){
            logger.error("Errore nel recupero del messaggio");
            Platform.exit();
        }
        return receive;
    }

    public void elimina(Integer posizione, TextArea testoLibero){
        messageToCommand.fromMessage(riceviMessaggio(BoundaryUserControl.returnRimuoviArticoloCommandVariable(posizione)));
        if (Objects.equals(messageToCommand.getCommand(), "NO")){
            testoLibero.setText(messageToCommand.getPayload());
        }else if (Objects.equals(messageToCommand.getCommand(), "SI")){
            testoLibero.setText("Articolo Eliminato");
        }
    }

    public void visualizzaCarrello(boolean scelta, int posizione, Button successivo){
        messageToCommand.fromMessage(riceviMessaggio(BoundaryUserControl.returnVisualizzaArticoloCommandVariable(posizione)));
        if (Objects.equals(messageToCommand.getCommand(), "NO")){
            testoLibero.setText("Articoli Finiti");
            if (scelta) successivo.setText("|");
        }else if (Objects.equals(messageToCommand.getCommand(), "SI")){
            String articolo = messageToCommand.getPayload();
            List<String> lista = ConvertiStringToArticolo.convertToListStringFromString(articolo);
            PrintArticoli.stampaArticolisuTextBox(lista, testoLibero);
        }else if (Objects.equals(messageToCommand.getCommand(), "SINOTI")){
            testoLibero.setText(StringToOrdini.coverti(messageToCommand.getPayload()));
        }
    }

    public void setGestionePerUI(GestionePerUI lollo){
        gestionePerUI = lollo;
    }

    public void setTestoLibero(TextArea testo){
        testoLibero = testo;
    }
}

