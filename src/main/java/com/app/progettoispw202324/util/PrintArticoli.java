package com.app.progettoispw202324.util;

import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrintArticoli {

    private static final String MINUTI = " MINUTI";

    static TextArea testoBox;

    private PrintArticoli(){
        throw new IllegalStateException("Utility class");
    }

    public static void stampaArticolisuTextBox(List<String> lista, TextArea testoLibero) {
        testoBox = testoLibero;
        switch (lista.get(0)) {
            case "pane":
                lista.remove(0);
                printPane(lista);
                break;

            case "pizza":
                lista.remove(0);
                printPizza(lista);
                break;
            default:
                break;
        }
    }


    private static void printPane(List<String> lista){
        String capo = "\n";
        List<String> listaTronca = new ArrayList<>();
        for (int i=0 ; i<4; i++) {
            listaTronca.add(lista.get(i));
        }
        lista.removeAll(listaTronca);
        String testo = "PANE" + capo + printArticolo(listaTronca);
        
        listaTronca = new ArrayList<>();
        for (int i=0 ; i<2; i++) {
            listaTronca.add(lista.get(i));
        }
        lista.removeAll(listaTronca);
        testo = testo + printArticoloAlimentare(listaTronca);

        String lievitatura;
        if (Objects.equals(lista.get(2), "true")){
            lievitatura = "Naturale";
        }else{
            lievitatura = "Non naturale";
        }

        testo = testo + "Tempo Cottura : " + lista.get(0) + MINUTI + capo + "Tempo Lievitatura : " + lista.get(1) + MINUTI + capo + "Lievitatura : " + lievitatura+ capo + "Descrizione : " + lista.get(3);
        testoBox.setText(testo);
    }


    private static void printPizza(List<String> lista){
        String capo = "\n";
        List<String> listaTronca = new ArrayList<>();
        for (int i=0 ; i<4; i++) {
            listaTronca.add(lista.get(i));
        }
        lista.remove(0);
        lista.remove(0);
        lista.remove(0);
        lista.remove(0);
        String testo = "PIZZA" + capo + printArticolo(listaTronca);

        listaTronca = new ArrayList<>();
        for (int i=0 ; i<2; i++) {
            listaTronca.add(lista.get(i));
        }
        lista.remove(0);
        lista.remove(0);
        testo = testo + printArticoloAlimentare(listaTronca);

        String forma;
        if (Objects.equals(lista.get(1), "true")){
            forma = "Tonda";
        }else{
            forma = "Rettangolare";
        }

        String lievitatura;
        if (Objects.equals(lista.get(2), "true")){
            lievitatura = "Naturale";
        }else{
            lievitatura = "Non naturale";
        }

        testo = testo + "Tempo Cottura : " + lista.get(0) + MINUTI + capo + "Forma : " + forma + capo + "Lievitatura : " + lievitatura+ capo + "Descrizione : " + lista.get(3);
        testoBox.setText(testo);
    }

    private static String printArticolo(List<String> lista){
        String capo = "\n";
        return "ID : " + lista.get(0)+ capo + "Nome : " + lista.get(1)+ capo + "Prezzo : " + lista.get(2) + "€" + capo + "Quantità : " + lista.get(3) + capo;
    }
    private static String printArticoloAlimentare(List<String> lista){
        String capo = "\n";
        return "Ingredienti : " + lista.get(0)+ capo + "Peso : " + lista.get(1) + " g" + capo;
    }
}