package com.app.progettoispw202324;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import util.MessageToCommand;

import java.io.IOException;
import java.util.Objects;

public class MenuController {
    MessageToCommand messageToCommand = new MessageToCommand();

    FXMLLoader fxmlLoader;
    Parent root;
    static GestionePerUI gestionePerUI;
    static int livello;

    @FXML
    Button scegliNegozio;

    @FXML
    Button confermaCarrello;

    @FXML
    Button ordine;

    public void scegliNegozio(ActionEvent event) {
        if (gestionePerUI.getNegozio() == null) {
            AllertBox.allertSceltaNegozio("Scelta", "L'id del negozio?", gestionePerUI, false);
            scegliNegozio.setStyle("-fx-background-color: green;");
        }else {
            AllertBox.allertSceltaNegozio("Scelta", "L'id del negozio è " + gestionePerUI.getNegozio(), gestionePerUI, true);
            scegliNegozio.setStyle("-fx-background-color: green;");
        }
    }

    public void visualizza(ActionEvent event){
        if (!(livello<3)){return;}
        String input;
        messageToCommand.setCommand("VISUALIZZA");
        messageToCommand.setPayload(null);
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try {
            input = gestionePerUI.getMessage();
        } catch (IOException e){
            input = "NO";
        }

        if (Objects.equals(input, "OK")) {
            try {
                fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("VisualizzaCarrello.fxml"));
                root = fxmlLoader.load();
                VisualizzaController controller = fxmlLoader.getController();
                controller.passGestione(gestionePerUI);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Visualizza");
                stage.show();

            } catch (IOException e) {
                System.err.println("0x000110" + e.getMessage());
            }
        }   else if (input.contains("STOPIT")){
            Platform.exit();
        }
    }

    public void inserisci(ActionEvent event) {
        if (!(livello<3)){return;}
        String input;
        if (gestionePerUI.getNegozio() == null) {
            scegliNegozio(event);
        } else {
            messageToCommand.setCommand("AGGIUNGILISTA");
            messageToCommand.setPayload(gestionePerUI.getNegozio());
            gestionePerUI.sendMessage(messageToCommand.toMessage());
            try {
                input = gestionePerUI.getMessage();
            } catch (IOException e){
                input = "NO";
            }
            if (Objects.equals(input, "OK")) {
                try {
                    fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("InserisciCarrello.fxml"));
                    root = fxmlLoader.load();
                    InsController controller = fxmlLoader.getController();
                    controller.passGestione(gestionePerUI);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Inserisci");
                    stage.show();

                } catch (IOException e) {
                    System.err.println("0x000111" + e.getMessage());
                }
            } else if (input.contains("STOPIT")){
            Platform.exit();
        }
        }
    }

    public void conferma(ActionEvent event) {
        if (!(livello<3)){return;}
        String input;
        if (gestionePerUI.getNegozio() == null){
            scegliNegozio(event);
        }else {
            messageToCommand.setCommand("CONFERMALISTA");
            messageToCommand.setPayload(gestionePerUI.getNegozio());
            gestionePerUI.sendMessage(messageToCommand.toMessage());
            try {
                input = gestionePerUI.getMessage();
            } catch (IOException e) {
                input = "NO";
            }
            if (Objects.equals(input, "OK")) {
                confermaCarrello.setStyle("-fx-background-color: green;");
                messageToCommand.setCommand("RESETNEGOZIO");
                messageToCommand.setPayload(null);
                gestionePerUI.sendMessage(messageToCommand.toMessage());
            } else if (input.contains("STOPIT")){
            Platform.exit();
            } else {
                confermaCarrello.setStyle("-fx-background-color: red;");
            }
        }
    }

    public void confermeRicevute(ActionEvent event) {
        if (!(livello<3)){return;}
        try {
            FXMLLoader loader = new FXMLLoader(ClientApplication.class.getResource("OrdiniCarrello.fxml"));
            Parent root = loader.load();
            OrdiniCarrelloController controller = loader.getController();
            controller.passGestione(gestionePerUI);


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Verifica se le tue prenotazioni sono accettate");
            stage.show();
        }catch (IOException e){
            System.err.println("0x000112" + e.getMessage());
        }
    }


    public void aggiungiDaDb(ActionEvent event) {
        if (!(livello<2)){return;}
        try {
            FXMLLoader loader = new FXMLLoader(ClientApplication.class.getResource("CosaInserire.fxml"));
            Parent root = loader.load();
            InserisciController controller = loader.getController();
            controller.passGestione(gestionePerUI);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Aggiungi al DB");
            stage.show();
        } catch (IOException e) {
            System.err.println("0x000113" + e.getMessage());
        }
    }

    public void visualizzaDaDb (ActionEvent event) {
        if (!(livello<2)){return;}

        String input;
        messageToCommand.setCommand("VISUALIZZAARTICOLODB");
        messageToCommand.setPayload(null);
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try {
            input = gestionePerUI.getMessage();
        } catch (IOException e){
            input = "NO";
        }
        if (Objects.equals(input, "OK")) {
            try {
                FXMLLoader loader = new FXMLLoader(ClientApplication.class.getResource("VisualizzaDB.fxml"));
                Parent root = loader.load();
                VisualizzaDB controller = loader.getController();
                controller.passGestione(gestionePerUI);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Visualizza dal DB");
                stage.show();

            } catch (IOException e) {
                System.err.println("0x000114" + e.getMessage());
            }
        }
    }


    public void notificheDaAccettare(ActionEvent event) {
        if (!(livello<2)){return;}

        String input;
        messageToCommand.setCommand("NOTIFICA");
        messageToCommand.setPayload(null);
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        try {
            input = gestionePerUI.getMessage();
        } catch (IOException e){
            input = "NO";
        }
        if (Objects.equals(input, "OK")) {
            ordine.setText("ORDINE");
            try {
                FXMLLoader loader = new FXMLLoader(ClientApplication.class.getResource("VisualizzaNotifica.fxml"));
                Parent root = loader.load();
                NotificaController controller = loader.getController();
                controller.passGestione(gestionePerUI);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Visualizza Ordini");
                stage.show();
            } catch (IOException e) {
                System.err.println("0x000115" + e.getMessage());
            }
        } else if (Objects.equals(input, "NULL")) {
            ordine.setText("Nessun Ordine");
        }
    }
    public void OrdiniPerOggi(ActionEvent event) {
        if (!(livello<2)){return;}
        try {
            FXMLLoader loader = new FXMLLoader(ClientApplication.class.getResource("VisualizzaController.fxml"));
            Parent root = loader.load();
            VisualizzaController controller = loader.getController();
            controller.passGestione(gestionePerUI);

            
        }catch (IOException e){
            System.err.println("0x000116" + e.getMessage());
        }
        //fargli cambiare scena
    }

    public void logOut(ActionEvent event) {
        messageToCommand.setCommand("EXIT");
        messageToCommand.setPayload(null);
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        Platform.exit();
    }


    public void passGestione(GestionePerUI temporaneo){
        this.gestionePerUI = temporaneo;
    }
    public void passLivello(int n){
        livello = n;
    }
    public void setNegozio(String negozio){
        gestionePerUI.setNegozio(negozio);
    }
}
