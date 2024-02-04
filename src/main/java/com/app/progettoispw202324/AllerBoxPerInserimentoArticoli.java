package com.app.progettoispw202324;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.MessageToCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class AllerBoxPerInserimentoArticoli {
    public static void allertSceltaNegozio(String message) {

        MessageToCommand messageToCommand = new MessageToCommand();

        AtomicBoolean inserito = new AtomicBoolean(false);
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Inserisci i dati");
        window.setMinWidth(250);

        switch (message) {
            case "pane":
                pane(window);
                break;

            case "pizza":
                pizza(window);
                break;
            default:
                break;
        }
    }

    private static void setLista(String lista){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("CosaInserire.fxml"));
            Parent root = fxmlLoader.load();
            InserisciController controller = fxmlLoader.getController();
            controller.passLista(lista);
            controller.passGiusto(true);
        }catch (IOException e){
            System.err.println("0x000105" + e.getMessage());
        }
    }

    private static void setGiusto(boolean bool){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("CosaInserire.fxml"));
            Parent root = fxmlLoader.load();
            InserisciController controller = fxmlLoader.getController();
            controller.passGiusto(bool);
        }catch (IOException e){
            System.err.println("0x000105" + e.getMessage());
        }
    }

    private static void pane(Stage window){

        setGiusto(false);

        AtomicBoolean ok = new AtomicBoolean(true);
        String lista;

        TextField nome = new TextField();
        nome.setPromptText("nome");

        TextField prezzo = new TextField();
        prezzo.setPromptText("prezzo");

        TextField quantita = new TextField();
        quantita.setPromptText("quantita");

        TextField ingredienti = new TextField();
        ingredienti.setPromptText("Ingredienti suddivisi da ,");

        TextField peso = new TextField();
        peso.setPromptText("Peso");

        TextField cottura = new TextField();
        cottura.setPromptText("Cottura");

        TextField tempoLievitatura = new TextField();
        tempoLievitatura.setPromptText("Tempo Lievitatura");

        ChoiceBox<String> lievitatura = new ChoiceBox<>();
        lievitatura.getItems().addAll("Naturale", "Non Naturale");
        // Set an initial selection
        lievitatura.setValue("Tipo lievitatura");

        TextField descrizione = new TextField();
        descrizione.setPromptText("Descrizione");


        Button closeButton = new Button("Inserisci");
        try {
            closeButton.setOnAction(e -> {
                String nome2 = nome.getText();
                if (Objects.equals(nome2, "")){
                    prezzo.setText("");
                    prezzo.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }
                double prezzo2 = 1;
                try {
                    prezzo2 = Double.parseDouble(prezzo.getText());
                } catch (NumberFormatException e1) {
                    prezzo.setText("");
                    prezzo.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }
                float quantita2 = 1;
                try {
                    quantita2 = Float.parseFloat(quantita.getText());
                } catch (NumberFormatException e1) {
                    quantita.setText("");
                    quantita.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }
                String ingredienti2 = ingredienti.getText();
                if (Objects.equals(ingredienti2, "")){
                    prezzo.setText("");
                    prezzo.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }
                String[] splitted = ingredienti2.split(",");
                double peso2 = 250;
                try {
                    peso2 = Double.parseDouble(peso.getText());
                } catch (NumberFormatException e1) {
                    peso.setText("");
                    peso.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }
                int cottura2 = 1;
                try {
                    cottura2 = Integer.parseInt(cottura.getText());
                } catch (NumberFormatException e1) {
                    cottura.setText("");
                    cottura.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }
                int tempoLievitatura2 = 1;
                try {
                    tempoLievitatura2 = Integer.parseInt(tempoLievitatura.getText());
                } catch (NumberFormatException e1) {
                    tempoLievitatura.setText("");
                    tempoLievitatura.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }
                String descrizione2 = descrizione.getText();
                if (Objects.equals(descrizione2, "")){
                    prezzo.setText("");
                    prezzo.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }

                List<String> ingredientiString = new ArrayList<>();

                if (ok.get()) {
                    for (String fruit : splitted) {
                        ingredientiString.add(fruit.trim());
                    }

                    String linea = "|";

                    String lievitatura2;
                    if (Objects.equals(lievitatura.getValue(), "Naturale")) {
                        lievitatura2 = "true";
                    } else {
                        lievitatura2 = "false";
                    }

                    String tot = "{pane}" + "{" + "0" + linea + nome2 + linea + prezzo2 + linea + quantita2 + "}" + "{" + ingredientiString.toString() + linea + peso2 + "}" + "{" + cottura2 + linea + tempoLievitatura2 + linea + lievitatura2 + linea + descrizione2 + "}";

                    setLista(tot);
                    setGiusto(true);
                }

                window.close();

            });
        } catch (Exception e) {
            closeButton.setStyle("-fx-background-color: red;");
        }

        VBox layout = new VBox(10);
        layout.getChildren().addAll(nome, prezzo, quantita, ingredienti, peso, cottura, tempoLievitatura, lievitatura, descrizione, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private static void pizza(Stage window){

        setGiusto(false);

        AtomicBoolean ok = new AtomicBoolean(true);
        String lista;

        TextField nome = new TextField();
        nome.setPromptText("nome");

        TextField prezzo = new TextField();
        prezzo.setPromptText("prezzo");

        TextField quantita = new TextField();
        quantita.setPromptText("quantita");

        TextField ingredienti = new TextField();
        ingredienti.setPromptText("Ingredienti suddivisi da ,");

        TextField peso = new TextField();
        peso.setPromptText("Peso");

        TextField cottura = new TextField();
        cottura.setPromptText("Cottura");

        ChoiceBox<String> lievitatura = new ChoiceBox<>();
        lievitatura.getItems().addAll("Naturale", "Non Naturale");
        // Set an initial selection
        lievitatura.setValue("Tipo lievitatura");

        ChoiceBox<String> forma = new ChoiceBox<>();
        forma.getItems().addAll("Tonda", "Quadrate");
        // Set an initial selection
        forma.setValue("Lievitatura");

        TextField descrizione = new TextField();
        descrizione.setPromptText("Descrizione");


        Button closeButton = new Button("Inserisci");
        try {
            closeButton.setOnAction(e -> {
                String nome2 = nome.getText();
                if (Objects.equals(nome2, "")){
                    prezzo.setText("");
                    prezzo.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }
                double prezzo2 = 1;
                try {
                    prezzo2 = Double.parseDouble(prezzo.getText());
                } catch (NumberFormatException e1) {
                    prezzo.setText("");
                    prezzo.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }
                float quantita2 = 1;
                try {
                    quantita2 = Float.parseFloat(quantita.getText());
                } catch (NumberFormatException e1) {
                    quantita.setText("");
                    quantita.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }
                String ingredienti2 = ingredienti.getText();
                if (Objects.equals(ingredienti2, "")){
                    prezzo.setText("");
                    prezzo.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }
                String[] splitted = ingredienti2.split(",");
                double peso2 = 250;
                try {
                    peso2 = Double.parseDouble(peso.getText());
                } catch (NumberFormatException e1) {
                    peso.setText("");
                    peso.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }
                int cottura2 = 1;
                try {
                    cottura2 = Integer.parseInt(cottura.getText());
                } catch (NumberFormatException e1) {
                    cottura.setText("");
                    cottura.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }
                String descrizione2 = descrizione.getText();
                if (Objects.equals(descrizione2, "")){
                    prezzo.setText("");
                    prezzo.setStyle("-fx-background-color: red;");
                    ok.set(false);
                }

                List<String> ingredientiString = new ArrayList<>();

                if (ok.get()) {
                    for (String fruit : splitted) {
                        ingredientiString.add(fruit.trim());
                    }

                    String linea = "|";

                    String forma2;
                    if (Objects.equals(forma.getValue(), "Tonda")) {
                        forma2 = "true";
                    } else {
                        forma2 = "false";
                    }

                    String lievitatura2;
                    if (Objects.equals(lievitatura.getValue(), "Naturale")) {
                        lievitatura2 = "true";
                    } else {
                        lievitatura2 = "false";
                    }

                    String tot = "{pizza}" + "{" + "0" + linea + nome2 + linea + prezzo2 + linea + quantita2 + "}" + "{" + ingredientiString.toString() + linea + peso2 + "}" + "{" + cottura2 + linea + lievitatura2 + linea + forma2 + linea + descrizione2 + "}";

                    setLista(tot);
                    setGiusto(true);
                }

                window.close();

            });
        } catch (Exception e) {
            closeButton.setStyle("-fx-background-color: red;");
        }

        VBox layout = new VBox(10);
        layout.getChildren().addAll(nome, prezzo, quantita, ingredienti, peso, cottura, lievitatura, forma, descrizione, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
