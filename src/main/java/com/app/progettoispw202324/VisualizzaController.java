package com.app.progettoispw202324;

import model.domain.ui.GestionePerUI;

public class VisualizzaController extends Generic {
    public void elimina(){
        setComandi();
        comandi.elimina(posizione,testoLibero);
    }

    public static void passGestione(GestionePerUI temporaneo){
        gestionePerUI = temporaneo;
    }
}
