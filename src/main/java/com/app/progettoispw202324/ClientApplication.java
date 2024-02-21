package com.app.progettoispw202324;

import boundary.BoundaryLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.MessageToCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class ClientApplication extends Application {

    static Logger logger = LogManager.getLogger(ClientApplication.class);

    @Override
    public void start(Stage stage){
        final String host = "localhost";
        int port = 5000;

        try(Socket socket = new Socket(host, port)) {
            BufferedReader in = null;
            PrintWriter out = null;
    
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException | NullPointerException e) {
                logger.error("Errore nell apertura del Lettore e Scrittore sulla socket");
            }
    
            //far arrivare il server al LOGIN
            GestionePerUI gestionePerUI = new GestionePerUI(in, out);
            MessageToCommand messageToCommand = new MessageToCommand();
            messageToCommand.setCommand("CRIPT");
            messageToCommand.setPayload("Inserisci la chiave di criptazione");
            gestionePerUI.sendMessage(messageToCommand.toMessage());
    
            String ricevi;
            //Qui verrà fatto tornare qualcosa dal server quando inseriro il metodo di criptazione
            gestionePerUI.getMessage();
            gestionePerUI.sendMessage(BoundaryLogin.RETURNLOGIN);
            ricevi = gestionePerUI.getMessage();
            if(!Objects.equals(ricevi, "Autenticarsi: ")) return;
    
            LoginController.passGestione(gestionePerUI);
    
            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("ControllerLogin.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 650, 400);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            logger.error("Something wrong here");
        }
    }



    public static void starter() {
        launch();
    }
}