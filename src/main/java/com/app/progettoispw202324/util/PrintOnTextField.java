package com.app.progettoispw202324.util;

import javafx.scene.control.TextArea;

public class PrintOnTextField {
    static TextArea testoBox;
    public static void stampaArticolisuTextBox(String lista, TextArea testoLibero) {
        testoBox = testoLibero;
        testoBox.setText(lista);
    }
}
