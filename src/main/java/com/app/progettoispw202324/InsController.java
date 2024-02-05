package com.app.progettoispw202324;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import util.ConvertiStringToArticolo;
import util.MessageToCommand;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InsController {
    
    Logger logger = LogManager.getLogger(InsController.class);

    MessageToCommand messageToCommand = new MessageToCommand();


    static GestionePerUI gestionePerUI;

    private int posizione = 0;
    private boolean finiti = false;

    @FXML
    Button precedente;
    @FXML
    Button successivo;
    @FXML
    TextField quantita;
    @FXML
    TextArea testoLibero;

    public void menu(ActionEvent event){
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

    public void vaiSuccessivo(){
        if (!finiti) {
            posizione++;
            visualizzaCarrello();
            if (posizione > 0) {
                precedente.setText("<<");
            }
            if (Objects.equals(testoLibero.getText(), "Articoli Finiti")) {
                finiti = true;
            }
        }
    }


    public void vaiPrecedente(){
        if (posizione == 0){
            testoLibero.setText("Il primo elemento è stato raggiunto");
            successivo.setText(">>");
        }else {
            posizione--;
            visualizzaCarrello();
            if (posizione < 1) {
                precedente.setText("|");
                finiti = false;
            }
        }
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

    private String messaggio(){
        try {
            return gestionePerUI.getMessage();
        } catch (IOException e) {
            logger.error("Errore nel recupero del messaggio");
            Platform.exit();
        }
        return null;
    }


    public void visualizzaCarrello(){
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
