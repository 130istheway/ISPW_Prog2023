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

public class AllerBoxPerInserimentoArticoli {

    static AtomicBoolean ok = new AtomicBoolean(true);

    private static final String NATURALE = "NATURALE";
    private static final String NNATURALE = "Non naturale";
    private static final String IMPOSTAROSSO = "-fx-background-color: red;";
    private static final String FALSO = "false";
    private static final String VERO = "true";


    

        
    static TextField nome = new TextField();
    static TextField prezzo = new TextField();
    static TextField quantita = new TextField();
    static TextField ingredienti = new TextField();
    static TextField peso = new TextField();
    static TextField cottura = new TextField();
    static TextField tempoLievitatura = new TextField();
    static ChoiceBox<String> lievitatura = new ChoiceBox<>();
    static TextField descrizione = new TextField();
    static ChoiceBox<String> forma = new ChoiceBox<>();


    private static void setEverithing(){
        setGiusto(false);
        nome.setPromptText("nome");
        prezzo.setPromptText("prezzo");
        quantita.setPromptText("quantita");
        ingredienti.setPromptText("Ingredienti suddivisi da ,");
        cottura.setPromptText("Cottura");
        tempoLievitatura.setPromptText("Tempo Lievitatura");
        lievitatura.getItems().addAll(NATURALE, NNATURALE);
        lievitatura.setValue("Tipo lievitatura");
        descrizione.setPromptText("Descrizione");
        forma.getItems().addAll("Tonda", "Quadrate");
        forma.setValue("Lievitatura");
    }
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

    private static String getNome(){
        String nome2 = nome.getText();
        if (Objects.equals(nome2, "")){
            nome.setText("");
            nome.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return null;
        }
        return nome2;
    }

    private static double getPrezzo(){
        double prezzo2;
        try {
            prezzo2 = Double.parseDouble(prezzo.getText());
        } catch (NumberFormatException e1) {
            prezzo.setText("");
            prezzo.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return 0;
        }
        return prezzo2;
    }

    private static float getQuantita(){
        float quantita2;
        try {
            quantita2 = Float.parseFloat(quantita.getText());
        } catch (NumberFormatException e1) {
            quantita.setText("");
            quantita.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return 0;
        }
        return quantita2;
    }

    private static String[] getIngredienti(){
        String ingredienti2 = ingredienti.getText();
        if (Objects.equals(ingredienti2, "")){
            ingredienti.setText("");
            ingredienti.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return null;
        }
        return ingredienti2.split(",");
    }

    private static double getPeso(){
        double peso2;
        try {
            peso2 = Double.parseDouble(peso.getText());
        } catch (NumberFormatException e1) {
            peso.setText("");
            peso.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return 0;
        }
        return peso2;
    }

    private static int getCottura(){
        int cottura2;
        try {
            cottura2 = Integer.parseInt(cottura.getText());
        } catch (NumberFormatException e1) {
            cottura.setText("");
            cottura.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return 0;
        }
        return cottura2;
    }

    private static int getTempoLievitatura(){
        int tempoLievitatura2;
        try {
            tempoLievitatura2 = Integer.parseInt(tempoLievitatura.getText());
        } catch (NumberFormatException e1) {
            tempoLievitatura.setText("");
            tempoLievitatura.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return 0;
        }
        return tempoLievitatura2;
    }

    private static String getDescrizione(){
        String descrizione2 = descrizione.getText();
        if (Objects.equals(descrizione2, "")){
            descrizione.setText("");
            descrizione.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return null;
        }
        return descrizione2;
    }

    private static void pane(Stage window){

        setEverithing();

        Button closeButton = new Button("Inserisci");
        try {
            closeButton.setOnAction(e -> {

                String nome2 = getNome();

                double prezzo2 = getPrezzo();
                
                float quantita2 = getQuantita();
                
                String[] splitted = getIngredienti();
                
                double peso2 = getPeso();
                
                int cottura2 = getCottura();
                
                int tempoLievitatura2 = getTempoLievitatura();
                
                String descrizione2 = getDescrizione();

                List<String> ingredientiString = new ArrayList<>();

                if (ok.get()) {
                    assert splitted != null;
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

                    String tot = "{pane}" + "{" + "0" + linea + nome2 + linea + prezzo2 + linea + quantita2 + "}" + "{" + ingredientiString + linea + peso2 + "}" + "{" + cottura2 + linea + tempoLievitatura2 + linea + lievitatura2 + linea + descrizione2 + "}";

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

        setEverithing();

        Button closeButton = new Button("Inserisci");
        try {
            closeButton.setOnAction(e -> {
                String nome2 = getNome();
                
                double prezzo2 = getPrezzo();

                float quantita2 = getQuantita();

                String[] splitted = getIngredienti();
                
                double peso2 = getPeso();

                int cottura2 = getCottura();

                String descrizione2 = getDescrizione();

                List<String> ingredientiString = new ArrayList<>();

                if (ok.get()) {
                    assert splitted != null;
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

                    String tot = "{pizza}" + "{" + "0" + linea + nome2 + linea + prezzo2 + linea + quantita2 + "}" + "{" + ingredientiString + linea + peso2 + "}" + "{" + cottura2 + linea + lievitatura2 + linea + forma2 + linea + descrizione2 + "}";

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
