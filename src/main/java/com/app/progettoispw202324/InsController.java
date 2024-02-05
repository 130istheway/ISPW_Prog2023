package com.app.progettoispw202324;

import com.app.progettoispw202324.util.PrintArticoli;
import com.app.progettoispw202324.util.Comandi;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.domain.ui.GestionePerUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConvertiStringToArticolo;
import util.MessageToCommand;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class InsController {
    
    static Logger logger = LogManager.getLogger(InsController.class);

    static MessageToCommand messageToCommand = new MessageToCommand();

    private static final String INSCONTROLLER = "InsCotroller";
    static GestionePerUI gestionePerUI;

    private static Integer posizione = 0;
    private Boolean finiti = false;

    @FXML
    Button precedente;
    @FXML
    static Button successivo;
    @FXML
    TextField quantita;
    @FXML
    static TextArea testoLibero;

    public void menu(ActionEvent event){
        Comandi.menu(event, gestionePerUI);
    }

    public void vaiSuccessivo(){
        Comandi.vaiSuccessivo(finiti,posizione,successivo,precedente,testoLibero, INSCONTROLLER);
    }

    public void vaiPrecedente(){
        Comandi.vaiPrecedente(finiti,posizione,successivo,precedente,testoLibero, INSCONTROLLER);
    }

    public void inserisci(){
        String receive = "NO";
        try {
            int quant = Integer.parseInt(quantita.getText());
            quantita.setStyle("-fx-background-color: green;");
            messageToCommand.setCommand("AGGIUNGILISTA");
            String message = posizione + "|" + quant;
            messageToCommand.setPayload(message);
            gestionePerUI.sendMessage(messageToCommand.toMessage());
            receive = messaggio();
            messageToCommand.fromMessage(receive);
            if (Objects.equals(messageToCommand.getCommand(), "NO")) {
                testoLibero.setText("Articolo non Inserito");
            } else if (Objects.equals(messageToCommand.getCommand(), "SI")) {
                quantita.setStyle("-fx-background-color: white;");
                quantita.setText("1");
                testoLibero.setText("Articolo Inserito");
            }
        }catch(NumberFormatException e){
            quantita.setStyle("-fx-background-color: red;");
            quantita.setPromptText("Numero");
        }
    }

    private static String messaggio(){
        try {
            return gestionePerUI.getMessage();
        } catch (IOException e) {
            logger.error("Errore nel recupero del messaggio");
            Platform.exit();
        }
        return null;
    }


    public static void visualizzaCarrello(){
        String receive = "NO";
        messageToCommand.setCommand("VISUALIZZAART");
        messageToCommand.setPayload(String.valueOf(posizione));
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        receive = messaggio();
        messageToCommand.fromMessage(receive);
        if (Objects.equals(messageToCommand.getCommand(), "NO")){
            testoLibero.setText("Articoli Finiti");
            successivo.setText("|");
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
