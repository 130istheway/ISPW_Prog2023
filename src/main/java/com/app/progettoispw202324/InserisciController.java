package com.app.progettoispw202324;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import util.MessageToCommand;

import java.io.IOException;
import java.util.Objects;

public class InserisciController {

    MessageToCommand messageToCommand = new MessageToCommand();

    static GestionePerUI gestionePerUI;
    static String lista;
    static boolean giusto;

            FXMLLoader fxmlLoader;
    Parent root;
    String cosaInserisco;

    @FXML
    TextField infoDiInserire;

    @FXML
    Button pane;
    @FXML
    Button pizza;



    public void menu(ActionEvent event){
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

    public void setPane(ActionEvent event){
        String receive = "";
        cosaInserisco = "pane";
        AllerBoxPerInserimentoArticoli.allertSceltaNegozio(cosaInserisco);
        if (giusto) {
            messageToCommand.setCommand("AGGIUNGIARTICOLODB");
            messageToCommand.setPayload(lista);
            gestionePerUI.sendMessage(messageToCommand.toMessage());
            try {
                receive = gestionePerUI.getMessage();
            } catch (IOException e) {
                System.err.println("Errore nel recupero del messaggio");
                Platform.exit();
            }
            messageToCommand.fromMessage(receive);
            pane.setStyle("-fx-background-color: green;");
            if (Objects.equals(messageToCommand.getCommand(), "NO")) {
                infoDiInserire.setStyle("-fx-background-color: red;");
            } else if (Objects.equals(messageToCommand.getCommand(), "SI")) {
                infoDiInserire.setText("Articolo Aggiunto");
            } else if (Objects.equals(messageToCommand.getCommand(), "NON AUTORIZATO")) {
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
        }else{
            pane.setStyle("-fx-background-color: red;");
        }
    }

    public void setPizza(ActionEvent event){
        String receive = "";
        cosaInserisco = "pizza";
        AllerBoxPerInserimentoArticoli.allertSceltaNegozio(cosaInserisco);
        if (giusto) {
            messageToCommand.setCommand("AGGIUNGIARTICOLODB");
            messageToCommand.setPayload(lista);
            gestionePerUI.sendMessage(messageToCommand.toMessage());
            try {
                receive = gestionePerUI.getMessage();
            } catch (IOException e) {
                System.err.println("Errore nel recupero del messaggio");
                Platform.exit();
            }
            messageToCommand.fromMessage(receive);
            pizza.setStyle("-fx-background-color: green;");
            if (Objects.equals(messageToCommand.getCommand(), "NO")) {
                infoDiInserire.setStyle("-fx-background-color: red;");
            } else if (Objects.equals(messageToCommand.getCommand(), "SI")) {
                infoDiInserire.setText("Articolo Aggiunto");
            } else if (Objects.equals(messageToCommand.getCommand(), "NON AUTORIZATO")) {
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
        }else{
            pizza.setStyle("-fx-background-color: red;");
        }
    }


    private void change(ActionEvent event){

    }

    public void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }

    public void passLista(String temporaneo){
        lista = temporaneo;
    }

    public void passGiusto(boolean lollo){
        giusto = lollo;
    }
}
