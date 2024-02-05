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

    AtomicBoolean ok = new AtomicBoolean(true);

    private static final String NATURALE = "NATURALE";
    private static final String NNATURALE = "Non naturale";
    private static final String IMPOSTAROSSO = "-fx-background-color: red;";
    private static final String FALSO = "false";
    private static final String VERO = "true";


    

    setGiusto(false);
        
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

    ChoiceBox<String> forma = new ChoiceBox<>();
    forma.getItems().addAll("Tonda", "Quadrate");
    // Set an initial selection
    forma.setValue("Lievitatura");

    TextField descrizione = new TextField();
    descrizione.setPromptText("Descrizione");


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

    private String getNome(TextField nome){
        String nome2 = this.nome.getText();
        if (Objects.equals(nome2, "")){
            this.prezzo.setText("");
            this.prezzo.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return;
        }
        return nome2;
    }

    private double getPrezzo(TextField prezzo){
        double prezzo2 = 0;
        try {
            prezzo2 = Double.parseDouble(this.prezzo.getText());
        } catch (NumberFormatException e1) {
            this.prezzo.setText("");
            this.prezzo.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return;
        }
        return prezzo2;
    }

    private float getQuantita(TextField quantita){
        float quantita2 = 0.0;
        try {
            quantita2 = Float.parseFloat(this.quantita.getText());
        } catch (NumberFormatException e1) {
            this.quantita.setText("");
            this.quantita.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return;
        }
        return quantita2;
    }

    private String[] getIngredienti(TextField ingredienti){
        String ingredienti2 = this.ingredienti.getText();
        if (Objects.equals(ingredienti2, "")){
            this.ingredienti.setText("");
            this.ingredienti.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return;
        }
        String[] splitted = ingredienti2.split(",");
        return splitted
    }

    private double getPeso(TextField peso){
        double peso2 = 0;
        try {
            peso2 = Double.parseDouble(this.peso.getText());
        } catch (NumberFormatException e1) {
            this.peso.setText("");
            this.peso.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return;
        }
        return peso2;
    }

    private int getCottura(TextField cottura){
        int cottura2 = 0;
        try {
            cottura2 = Integer.parseInt(this.cottura.getText());
        } catch (NumberFormatException e1) {
            this.cottura.setText("");
            this.cottura.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return;
        }
        return cottura2;
    }

    private int getTempoLievitatura(TextField tempoLievitatura){
        int tempoLievitatura2 = 0;
        try {
            tempoLievitatura2 = Integer.parseInt(this.tempoLievitatura.getText());
        } catch (NumberFormatException e1) {
            this.tempoLievitatura.setText("");
            this.tempoLievitatura.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return;
        }
        return tempoLievitatura2;
    }

    private String getDescrizione(TextField descrizione){
        String descrizione2 = this.descrizione.getText();
        if (Objects.equals(descrizione2, "")){
            this.descrizione.setText("");
            this.descrizione.setStyle(IMPOSTAROSSO);
            ok.set(false);
            return;
        }
        return descrizione2;
    }

    private static void pane(Stage window){

        Button closeButton = new Button("Inserisci");
        try {
            closeButton.setOnAction(e -> {

                String nome2 = getNome(nome);

                double prezzo2 = getPrezzo(prezzo);
                
                float quantita2 = getQuantita(quantita);
                
                String[] splitted = getIngredienti(ingredienti);
                
                double peso2 = getPeso(peso);
                
                int cottura2 = getCottura(cottura);
                
                int tempoLievitatura2 = getTempoLievitatura(tempoLievitatura);
                
                String descrizione2 = getDescrizione(descrizione);

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

        Button closeButton = new Button("Inserisci");
        try {
            closeButton.setOnAction(e -> {
                String nome2 = getNome(nome);
                
                double prezzo2 = getPrezzo(prezzo);
                
                float quantita2 = getQuantita(quantita);

                String[] splitted = getIngredienti(ingredienti);
                
                double peso2 = getPeso(peso);

                int cottura2 = getCottura(cottura);

                String descrizione2 = getDescrizione(descrizione);

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
