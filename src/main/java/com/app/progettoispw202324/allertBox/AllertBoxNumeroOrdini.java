package com.app.progettoispw202324.allertBox;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.domain.ui.GestionePerUI;
import util.MessageToCommand;

public class AllertBoxNumeroOrdini {

    private AllertBoxNumeroOrdini() {
        throw new IllegalStateException("Utility class");
    }
    public static void allertOrdini(String title, String message){

        MessageToCommand messageToCommand = new MessageToCommand();

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("Hai " + message + " Ordini da controllare");

        Button closeButton = new Button("Close the window");

        closeButton.setOnAction(e -> {
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
