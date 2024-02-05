package com.app.progettoispw202324;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.*;

public class AllerBoxPerInserimentoArticoli {

    static Logger logger = LogManager.getLogger(AllerBoxPerInserimentoArticoli.class);

    private static final String NATURALE = "NATURALE";
    private static final String NNATURALE = "Non naturale";
    private static final String IMPOSTAROSSO = "-fx-background-color: red;";
    private static final String FALSO = "false";
    private static final String VERO = "true";

    private AllerBoxPerInserimentoArticoli(){
        throw new IllegalStateException("Utility class");
    }

    public static void allertSceltaNegozio(String message) {

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
        InserisciController.passLista(lista);
        InserisciController.passGiusto(true);
    }

    private static void setGiusto(boolean bool){
        InserisciController.passGiusto(bool);
    }

    private static void pane(Stage window){

        setGiusto(false);

        AtomicBoolean ok = new AtomicBoolean(true);
        
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
        lievitatura.getItems().addAll(NATURALE, NNATURALE);
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
                    prezzo.setStyle(IMPOSTAROSSO);
                    ok.set(false);
                }
                double prezzo2 = 1;
                try {
                    prezzo2 = Double.parseDouble(prezzo.getText());
                } catch (NumberFormatException e1) {
                    prezzo.setText("");
                    prezzo.setStyle(IMPOSTAROSSO);
                    ok.set(false);
                }
                float quantita2 = 1;
                try {
                    quantita2 = Float.parseFloat(quantita.getText());
                } catch (NumberFormatException e1) {
                    quantita.setText("");
                    quantita.setStyle(IMPOSTAROSSO);
                    ok.set(false);
                }
                String ingredienti2 = ingredienti.getText();
                if (Objects.equals(ingredienti2, "")){
                    prezzo.setText("");
                    prezzo.setStyle(IMPOSTAROSSO);
                    ok.set(false);
                }
                String[] splitted = ingredienti2.split(",");
                double peso2 = 250;
                try {
                    peso2 = Double.parseDouble(peso.getText());
                } catch (NumberFormatException e1) {
                    peso.setText("");
                    peso.setStyle(IMPOSTAROSSO);
                    ok.set(false);
                }
                int cottura2 = 1;
                try {
                    cottura2 = Integer.parseInt(cottura.getText());
                } catch (NumberFormatException e1) {
                    cottura.setText("");
                    cottura.setStyle(IMPOSTAROSSO);
                    ok.set(false);
                }
                int tempoLievitatura2 = 1;
                try {
                    tempoLievitatura2 = Integer.parseInt(tempoLievitatura.getText());
                } catch (NumberFormatException e1) {
                    tempoLievitatura.setText("");
                    tempoLievitatura.setStyle(IMPOSTAROSSO);
                    ok.set(false);
                }
                String descrizione2 = descrizione.getText();
                if (Objects.equals(descrizione2, "")){
                    prezzo.setText("");
                    prezzo.setStyle(IMPOSTAROSSO);
                    ok.set(false);
                }

                List<String> ingredientiString = new ArrayList<>();

                if (ok.get()) {
                    for (String fruit : splitted) {
                        ingredientiString.add(fruit.trim());
                    }

                    String linea = "|";

                    String lievitatura2;
                    if (Objects.equals(lievitatura.getValue(), NATURALE)) {
                        lievitatura2 = VERO;
                    } else {
                        lievitatura2 = FALSO;
                    }

                    String tot = "{pane}" + "{" + "0" + linea + nome2 + linea + prezzo2 + linea + quantita2 + "}" + "{" + ingredientiString.toString() + linea + peso2 + "}" + "{" + cottura2 + linea + tempoLievitatura2 + linea + lievitatura2 + linea + descrizione2 + "}";

                    setLista(tot);
                    setGiusto(true);
                }

                window.close();

            });
        } catch (Exception e) {
            closeButton.setStyle(IMPOSTAROSSO);
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
        lievitatura.getItems().addAll(NATURALE, NNATURALE);
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
                    prezzo.setStyle(IMPOSTAROSSO);
                    ok.set(false);
                }
                double prezzo2 = 1;
                try {
                    prezzo2 = Double.parseDouble(prezzo.getText());
                } catch (NumberFormatException e1) {
                    prezzo.setText("");
                    prezzo.setStyle(IMPOSTAROSSO);
                    ok.set(false);
                }
                float quantita2 = 1;
                try {
                    quantita2 = Float.parseFloat(quantita.getText());
                } catch (NumberFormatException e1) {
                    quantita.setText("");
                    quantita.setStyle(IMPOSTAROSSO);
                    ok.set(false);
                }
                String ingredienti2 = ingredienti.getText();
                if (Objects.equals(ingredienti2, "")){
                    prezzo.setText("");
                    prezzo.setStyle(IMPOSTAROSSO);
                    ok.set(false);
                }
                String[] splitted = ingredienti2.split(",");
                double peso2 = 250;
                try {
                    peso2 = Double.parseDouble(peso.getText());
                } catch (NumberFormatException e1) {
                    peso.setText("");
                    peso.setStyle(IMPOSTAROSSO);
                    ok.set(false);
                }
                int cottura2 = 1;
                try {
                    cottura2 = Integer.parseInt(cottura.getText());
                } catch (NumberFormatException e1) {
                    cottura.setText("");
                    cottura.setStyle(IMPOSTAROSSO);
                    ok.set(false);
                }
                String descrizione2 = descrizione.getText();
                if (Objects.equals(descrizione2, "")){
                    prezzo.setText("");
                    prezzo.setStyle(IMPOSTAROSSO);
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
                        forma2 = VERO;
                    } else {
                        forma2 = FALSO;
                    }

                    String lievitatura2;
                    if (Objects.equals(lievitatura.getValue(), NATURALE)) {
                        lievitatura2 = VERO;
                    } else {
                        lievitatura2 = FALSO;
                    }

                    String tot = "{pizza}" + "{" + "0" + linea + nome2 + linea + prezzo2 + linea + quantita2 + "}" + "{" + ingredientiString.toString() + linea + peso2 + "}" + "{" + cottura2 + linea + lievitatura2 + linea + forma2 + linea + descrizione2 + "}";

                    setLista(tot);
                    setGiusto(true);
                }

                window.close();

            });
        } catch (Exception e) {
            closeButton.setStyle(IMPOSTAROSSO);
        }

        VBox layout = new VBox(10);
        layout.getChildren().addAll(nome, prezzo, quantita, ingredienti, peso, cottura, lievitatura, forma, descrizione, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
