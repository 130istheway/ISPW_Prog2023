package com.app.progettoispw202324.util;

import javafx.scene.control.*;

public class PrintOnTextField {

    private PrintOnTextField(){
        throw new IllegalStateException("Utility class");
    }

    static TextArea testoBox;
    public static void stampaArticolisuTextBox(String lista, TextArea testoLibero) {
        testoBox = testoLibero;
        testoBox.setText(lista);
    }
}
