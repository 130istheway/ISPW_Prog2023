package controller.negozio;

import java.io.IOException;

import carrello.articoli.Articoli;
import carrello.articoli.Factory;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;

import server.com.server.exception.PersonalException;

import util.ConvertiStringToArticolo;
import util.MessageToCommand;

public class NegozioBDController {

    MessageToCommand message = new MessageToCommand();
    
    public boolean aggiungiDBArticolo(Credential credential, ControllerInfoSulThread Info, String string){

        Articoli articolo = Factory.factoryProdotto(ConvertiStringToArticolo.convertToArticolo(string));
        String stringForDb = articolo.toString();

        if (/* Recuperare ID negozio */) {
            message.setCommand("NO");
            message.setPayload("ID negozio non recuperato");

            info.sendMessage(message);
            return false;
        }
        /* Ottenere il numero di articoli che ha il negozio */
        
        if (/* Calcolare l'ID per l'inserimento e controllare che questo ID sia valido per l'inserimento con le dovute limitazioni */) {
            message.setCommand("NO");
            message.setPayload("ID non valido");

            info.sendMessage(message);
            return false;
        }
        
        if (/* Aggiungere funzione che inserisca l'elemento e faccia tornare un bool */) {
            message.setCommand("NO");
            message.setPayload("articolo non aggiunto");

            info.sendMessage(message);
            return false;
        }

        info.sendMessage(message);
        return true;
    }

    public boolean rimuoviDBArticolo(Credential credential, ControllerInfoSulThread Info, String string){
        int number = Integer.parseInt(string);

        if (number > /*Aggiungere funzione che faccia tornare il count degli elementi*/ || number <= 1 ) {
            message.setCommand("NO");
            message.setPayload("number fuori da standard");

            info.sendMessage(message);

            throw new PersonalException("Out of range");
        }
        if (/* Aggiungere funzione che elimini l'elemento e faccia tornare un booleano */) {
            message.setCommand("NO");
            message.setPayload("articolo non eliminato");

            info.sendMessage(message);
            return false;

        }
        info.sendMessage("SI");
        return true;
    }
}