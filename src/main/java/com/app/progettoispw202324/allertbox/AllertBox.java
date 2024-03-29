package com.app.progettoispw202324.allertbox;

import boundary.BoundaryBasicController;
import com.app.progettoispw202324.MenuController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AllertBox {

    static Logger logger = LogManager.getLogger(AllertBox.class);

    private AllertBox() {
        throw new IllegalStateException("Utility class");
    }

    public static void allertSceltaNegozio(String title, String message, GestionePerUI gestionePerUI, boolean cambioCarrello){

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
                        gestionePerUI.sendMessage(BoundaryBasicController.RETURNRESETNEGOZIOCOMMAND);
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
        MenuController.setNegozio(negozio);
    }
}
