package controller.negozio;

import java.io.IOException;

import carrello.articoli.Articoli;
import carrello.articoli.Factory;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;

import util.ConvertiStringToArticolo;

public class NegozioBDController {
    
    public boolean aggiungiDBController(Credential credential, ControllerInfoSulThread Info, String string){

        try {
            Info.getMessage();    
        } catch(IOException e1){
            Info.sendlog(LivelloInformazione.ERROR, "Errore nella Socket in aggiungiDBController ");
            return false;
        } catch (Exception e) {
            Info.sendlog(LivelloInformazione.ERROR,"Qualcosa è successo in aggiungiDBController");
            return false;
        }

        Articoli articolo = Factory.factoryProdotto(ConvertiStringToArticolo.convertToArticolo(string));
        String stringForDb = articolo.toString();

        //inserire la dao per recuperare il codice del negozio
        //inserire la dao per ottenere il numero di articoli del negozio
            //calcolare l'id che verrà applicato all'articolo
            //verificare che l'id sia entro un limite accettabile, forse scritto in un file di configurazione?
        //inserie la dao per inserire 


        return true;
    }
}