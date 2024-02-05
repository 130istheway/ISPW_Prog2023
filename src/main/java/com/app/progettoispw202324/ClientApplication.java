package com.app.progettoispw202324;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import util.MessageToCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientApplication extends Application {

    static Logger logger = LogManager.getLogger(ClientApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        final String host = "localhost";
        int port = 5000;
        Socket socket = null;

        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            logger.error("Something wrong here");
        }

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
        ricevi = gestionePerUI.getMessage();
        messageToCommand.setCommand("LOGIN");
        messageToCommand.setPayload(null);
        gestionePerUI.sendMessage(messageToCommand.toMessage());
        ricevi = gestionePerUI.getMessage();
        if(!Objects.equals(ricevi, "Autenticarsi: ")) return;

        LoginController.passGestione(gestionePerUI);

        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("ControllerLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 400);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }



    public static void starter() {
        launch();
    }
}