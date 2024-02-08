package carrello;


import java.util.ArrayList;
import java.util.List;

import carrello.articoli.Articoli;


/**
 * La classe {@code carello} gestisce il carrello dell'utente, contenente gli articoli selezionati per l'acquisto.
 * La lista del carrello utilizza il polimorfismo per gestire tutti gli articoli derivati dalla classe base {@code articoli}.
 * Include funzionalità per il controllo del pagamento e la gestione dei dati utente.
 * @author Stefano
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

    /**
     * un metodo per richiamare il metodo toString() di un articolo all'interno della lista di articoli di carrellino
     * @param number indice
     * @return
     */
    public String ritornaArticoloString(int number) {
        
        if (number >= carrellino.size()) {
            return null;
        }else if (carrellino.isEmpty()){
            return null;
        }else{
            String yatta = carrellino.get(number).toString();
            if (yatta == null) {
                return null;
            }
            return yatta;
        }
    }


    /**
     * Metodo per far tornare un oggetto di tipo Articoli che sta nell'indice della lista del carrelloache
     * @param number
     * @return
     */
    public Articoli ritornaArticolo(int number) {
        if (number > carrellino.size() || carrellino.isEmpty()) {
            return null;
        }else{
            return carrellino.get(number);
        }
    }
}
