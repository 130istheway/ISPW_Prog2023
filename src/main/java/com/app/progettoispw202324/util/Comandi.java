package com.app.progettoispw202324.util;

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
import java.util.List;
import java.util.Objects;

public class Comandi {
    static Logger logger = LogManager.getLogger(Comandi.class);
    static MessageToCommand messageToCommand = new MessageToCommand();

    private static final String IC = "InsCotroller";
    private static final String NC = "NotificaController";
    private static final String VC = "VisualizzaController";
    private static final String VDB = "VisualizzaDB";

    private Comandi() {
        throw new IllegalStateException("Utility class");
    }

    public static void menu(ActionEvent event, GestionePerUI gestionePerUI){
        messageToCommand.setCommand("EXIT");
        messageToCommand.setPayload("0");
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        gestionePerUI.sendMessage(messageToCommand.toMessage());
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


    public static void vaiSuccessivo(Boolean finiti, Integer posizione, Button successivo, Button precedente, TextArea testoLibero, String lollo){
        if (Boolean.FALSE.equals(finiti)) {
            posizione++;
            pippo(lollo);
            if (posizione > 0) {
                precedente.setText("<<");
            }
            if (Objects.equals(testoLibero.getText(),"Articoli Finiti")) {
                successivo.setText("|");
                finiti = true;
            }
        }
    }

    public static boolean vaiPrecedente(Boolean finiti, Integer posizione, Button successivo, Button precedente, String lollo){
        if (posizione == 0){
            pippo(lollo);
            successivo.setText(">>");
        }else {
            posizione--;
            pippo(lollo);
            }
            if (posizione < 1) {
                precedente.setText("|");
                return false;
            }
            return finiti;
    }

    private static void pippo(String lollo){
        switch(lollo){
            case IC:
                InsController.visualizzaCarrello();
                break;
            case NC:
                NotificaController.visualizzaCarrello();
                break;
            case VC:
                VisualizzaController.visualizzaCarrello();
                break;
            case VDB:
                VisualizzaDB.visualizzaCarrello();  
            default:
                logger.error("Non dovrei poter entrare qua dentro 0x0703");
            break;
        }
    }

    private static void riceviMessaggio(String string, GestionePerUI gestionePerUI, int posizione){
        messageToCommand = new MessageToCommand();
        String receive = "";

        messageToCommand.setCommand(string);
        messageToCommand.setPayload(String.valueOf(posizione));
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try{
            receive = gestionePerUI.getMessage();
        }catch (IOException e){
            logger.error("Errore nel recupero del messaggio");
            Platform.exit();
        }
        messageToCommand.fromMessage(receive);
    }

    public static void elimina(Integer posizione, GestionePerUI gestionePerUI, TextArea testoLibero){
        riceviMessaggio("RIMUOVIART", gestionePerUI, posizione);
        if (Objects.equals(messageToCommand.getCommand(), "NO")){
            testoLibero.setText(messageToCommand.getPayload());
        }else if (Objects.equals(messageToCommand.getCommand(), "SI")){
            testoLibero.setText("Articolo Eliminato");
        }
    }

    public static void visualizzaCarrello(boolean scelta, int posizione, GestionePerUI gestionePerUI, TextArea testoLibero, Button successivo){
        riceviMessaggio("VISUALIZZAART", gestionePerUI,posizione);
        if (Objects.equals(messageToCommand.getCommand(), "NO")){
            testoLibero.setText("Articoli Finiti");
            if (scelta) successivo.setText("|");
        }else if (Objects.equals(messageToCommand.getCommand(), "SI")){
            String articolo = messageToCommand.getPayload();
            List<String> lista = ConvertiStringToArticolo.convertToListStringFromString(articolo);
            PrintArticoli.stampaArticolisuTextBox(lista, testoLibero);
        }
    }
}

