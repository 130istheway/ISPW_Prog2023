package com.app.progettoispw202324;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import util.MessageToCommand;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AllertBox {

    static Logger logger = LogManager.getLogger(AllerBoxPerInserimentoArticoli.class);

    private AllertBox() {
        throw new IllegalStateException("Utility class");
    }

    public static void allertSceltaNegozio(String title, String message, GestionePerUI gestionePerUI, boolean cambioCarrello){

        MessageToCommand messageToCommand = new MessageToCommand();

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        TextField textField = new TextField();
        textField.setPromptText("ID negozio");

        Button closeButton = new Button("Close the window");
            try {
                closeButton.setOnAction(e -> {
                    int id = Integer.parseInt(textField.getText());
                    setControllerMenu(String.valueOf(id));
                    if (cambioCarrello) {
                        messageToCommand.setCommand("RESETNEGOZIO");
                        messageToCommand.setPayload(null);
                        gestionePerUI.sendMessage(messageToCommand.toMessage());
                    }
                    window.close();
                });
            } catch (NumberFormatException e) {
                textField.setText("");
                textField.setPromptText("Inserire un numero");
            }

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, textField,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }


    private static void setControllerMenu(String negozio){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("menu.fxml"));
            Parent root = fxmlLoader.load();
            MenuController controller = fxmlLoader.getController();
            controller.setNegozio(negozio);

        }catch (IOException e){
            logger.error("0x000106" + e.getMessage());
        }
    }
}
