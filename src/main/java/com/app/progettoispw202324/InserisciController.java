package com.app.progettoispw202324;

import com.app.progettoispw202324.util.Comandi;
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
import util.MessageToCommand;

import java.io.IOException;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InserisciController {

    Logger logger = LogManager.getLogger(InserisciController.class);

    private static final String TAG = "menu.fxml";
    private static final String IMPOSTAROSSO = "-fx-background-color: red;";
    private static final String FTL = "Failed to load";

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

    TextArea testoLibero = null;


    Comandi comandi = new Comandi(gestionePerUI, testoLibero);

    public void menu(ActionEvent event){
        setComandi();
        comandi.menu(event, 0);
    }

    private void cosaSuccesso(ActionEvent event){
        setComandi();
        if (Objects.equals(messageToCommand.getCommand(), "NO")) {
            infoDiInserire.setStyle(IMPOSTAROSSO);
        } else if (Objects.equals(messageToCommand.getCommand(), "SI")) {
            infoDiInserire.setText("Articolo Aggiunto");
        } else if (Objects.equals(messageToCommand.getCommand(), "NON AUTORIZATO")) {
            try {
                fxmlLoader = new FXMLLoader(ClientApplication.class.getResource(TAG));
                root = fxmlLoader.load();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                logger.error(FTL);
                Platform.exit();
            }
        }
    }



    private String messaggio(){
        setComandi();
        try {
            return gestionePerUI.getMessage();
        } catch (IOException e) {
            logger.error("Errore nel recupero del messaggio");
            Platform.exit();
        }
        return null;
    }

    private void setArticolo(ActionEvent event, String pippo){
        setComandi();
        String receive = "";
        cosaInserisco = pippo;
        BoxInserimentoArticoli allert = new BoxInserimentoArticoli();
        allert.allertSceltaNegozio(cosaInserisco);
        if (giusto) {
            messageToCommand.setCommand("AGGIUNGIARTICOLODB");
            messageToCommand.setPayload(lista);
            gestionePerUI.sendMessage(messageToCommand.toMessage());
            receive = messaggio();
            messageToCommand.fromMessage(receive);
            pizza.setStyle("-fx-background-color: green;");
            cosaSuccesso(event);
        }else{
            pizza.setStyle(IMPOSTAROSSO);
        }
    }

    public void setPizza(ActionEvent event){
        setArticolo(event, "pizza");
    }

    public void setPane(ActionEvent event){
        setArticolo(event, "pane");
    }

    private void setComandi(){
        comandi.setGestionePerUI(gestionePerUI);
    }
    public static void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }
    public static void passLista(String temporaneo){
        lista = temporaneo;
    }
    public static void passGiusto(boolean lollo){
        giusto = lollo;
    }
}
