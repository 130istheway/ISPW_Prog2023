package com.app.progettoispw202324;

import boundary.BoundaryUserControl;
import com.app.progettoispw202324.util.PrintArticoli;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.domain.ui.GestionePerUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConvertiStringToArticolo;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class InsController extends Generic {
    static Logger logger = LogManager.getLogger(InsController.class);

    @FXML
    TextField quantita;

    public void inserisci(){
        String receive = "NO";
        try {
            int quant = Integer.parseInt(quantita.getText());
            gestionePerUI.sendMessage(BoundaryUserControl.returnAggiungiAllaListaCommandVariable(posizione, quant));
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

    @Override
    public void visualizzaCarrello(){
        String receive;
        gestionePerUI.sendMessage(BoundaryUserControl.returnVisualizzaArticoloCommandVariable(posizione));
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
