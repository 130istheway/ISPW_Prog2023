package carrello;


import model.domain.ControllerInfoSulThread;
import util.MessageToCommand;

import java.util.ArrayList;
import java.util.List;

import carrello.articoli.Articoli;


/**
 * La classe {@code carello} gestisce il carrello dell'utente, contenente gli articoli selezionati per l'acquisto.
 * La lista del carrello utilizza il polimorfismo per gestire tutti gli articoli derivati dalla classe base {@code articoli}.
 * Include funzionalità per il controllo del pagamento e la gestione dei dati utente.
 * @author Stefano
 * @author Simone
 */
public class CarrelloCache{
    /** Lista degli articoli nel carrello */
    List<Articoli> carrellino;
    

    public CarrelloCache(){
        this.carrellino = new ArrayList<>();
    }


    /**
     * Costruttore della classe {@code carello}.
     *
     * @param carello Lista degli articoli nel carrello
     */
    public CarrelloCache(List<Articoli> carello) {
        this.carrellino = carello;
    }

    /**
     * Imposta la lista degli articoli nel carrello.
     *
     * @param carello Lista degli articoli nel carrello
     */
    public void setcarrellino(List<Articoli> carello) {
        this.carrellino = carello;
    }


    public void RitornaArticolo(ControllerInfoSulThread info, int number) {

        MessageToCommand message = new MessageToCommand();

        if (number > carrellino.size()) {
            message.setCommand("NO");
            message.setPayload(" Elemento non esistente ");
        }else{
        
            String yatta = carrellino.get(number).toString();

            message.setCommand("VISUALIZZAARTSERVER");
            message.setPayload(" ART " + yatta);

            info.sendMessage(message.toMessage());

        }
    }

    public Articoli RitornaArticolo(int number) {
        if (number > carrellino.size()) {
            return null;
        }else{
            return carrellino.get(number);
        }
    }
}
