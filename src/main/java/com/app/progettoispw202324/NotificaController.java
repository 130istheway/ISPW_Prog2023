package com.app.progettoispw202324;

import com.app.progettoispw202324.util.PrintArticoli;
import com.app.progettoispw202324.util.PrintOnTextField;
import com.app.progettoispw202324.util.StringToOrdini;
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
import util.MessageToCommand;
import util.StringToList;

import java.io.IOException;
import java.util.Objects;

public class NotificaController {
    MessageToCommand messageToCommand = new MessageToCommand();
    static GestionePerUI gestionePerUI;

    private static int posizione = 0;
    private boolean finiti = false;
    @FXML
    Button precedente;
    @FXML
    Button successivo;
    @FXML
    TextArea testoLibero;
    @FXML
    Button accetta;
    @FXML
    Button rifiuta;



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
            throw new RuntimeException(e);
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
            visualizzaCarrello();
            successivo.setText(">>");
        }else {
            posizione--;
            visualizzaCarrello();
            finiti = false;
            if (posizione < 1) {
                precedente.setText("|");
            }
        }
    }

    public void conferma(){
        rifiuta.setStyle("-fx-background-color: grey;");
        MessageToCommand messageToCommand = new MessageToCommand();
        String receive = null;

        messageToCommand.setCommand("CONFERMANOTIFICA");
        messageToCommand.setPayload(String.valueOf(posizione)+"|SI");
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try{
            receive = gestionePerUI.getMessage();
        }catch (IOException e){
            System.err.println("Errore nel recupero del messaggio");
            Platform.exit();
        }
        messageToCommand.fromMessage(receive);
        System.out.println(receive);
        if (Objects.equals(messageToCommand.getCommand(), "NO")){
            testoLibero.setText("NON E ANDATA BENE");
            accetta.setStyle("-fx-background-color: red;");
        }else if (Objects.equals(messageToCommand.getCommand(), "SI")){
            testoLibero.setText(messageToCommand.getPayload());
            accetta.setStyle("-fx-background-color: green;");
        }
        visualizzaCarrello();
    }

    public void rifiuta(){
        accetta.setStyle("-fx-background-color: grey;");

        MessageToCommand messageToCommand = new MessageToCommand();
        String receive = null;

        messageToCommand.setCommand("CONFERMANOTIFICA");
        messageToCommand.setPayload(String.valueOf(posizione)+"|NO");
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try{
            receive = gestionePerUI.getMessage();
        }catch (IOException e){
            System.err.println("Errore nel recupero del messaggio");
            Platform.exit();
        }
        messageToCommand.fromMessage(receive);
        if (Objects.equals(messageToCommand.getCommand(), "NO")){
            testoLibero.setText(messageToCommand.getPayload());
            rifiuta.setStyle("-fx-background-color: red;");
        }else if (Objects.equals(messageToCommand.getCommand(), "SI")){
            testoLibero.setText(messageToCommand.getPayload());
            rifiuta.setStyle("-fx-background-color: green;");
        }
        visualizzaCarrello();
    }

    public void visualizzaCarrello(){
        MessageToCommand messageToCommand = new MessageToCommand();
        String receive = null;
        messageToCommand.setCommand("VISUALIZZANOTI");
        messageToCommand.setPayload(String.valueOf(posizione));
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try{
            receive = gestionePerUI.getMessage();
        }catch (IOException e){
            System.err.println("Errore nel recupero del messaggio");
            Platform.exit();
        }
        messageToCommand.fromMessage(receive);
        if (Objects.equals(messageToCommand.getCommand(), "NO")){
            testoLibero.setText("Articoli Finiti");
        }else if (Objects.equals(messageToCommand.getCommand(), "SI")){
            PrintOnTextField.stampaArticolisuTextBox(StringToOrdini.coverti(messageToCommand.getPayload()), testoLibero);
        }
    }


    public void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }
}
