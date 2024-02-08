package carrello;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import carrello.articoli.Articoli;
import carrello.articoli.Factory;


/**
 * La classe {@code carello} gestisce il carrello dell'utente, contenente gli articoli selezionati per l'acquisto.
 * La lista del carrello utilizza il polimorfismo per gestire tutti gli articoli derivati dalla classe base {@code articoli}.
 * Include funzionalità per il controllo del pagamento e la gestione dei dati utente.
 * @author Stefano
 */
public class Carrello extends CarrelloCache{
    
    Logger logger = LogManager.getLogger(Carrello.class);
    
    /** Flag che indica se il pagamento è stato effettuato */
    boolean pagato;
    

    public Carrello(){
        super();
        this.pagato = false;
    }


    /**
     * Costruttore della classe {@code carello}.
     *
     * @param carrello Lista degli articoli nel carrello
     * @param pagato  Flag che indica se il pagamento è stato effettuato
     */
    public Carrello(List<Articoli> carrello, boolean pagato) {
        super(carrello);
        this.pagato = pagato;
    }

    /**
     * Imposta lo stato di pagamento.
     *
     * @param pagato Flag che indica se il pagamento è stato effettuato
     */
    public void setPagato(boolean pagato) {
        this.pagato = pagato;
    }

    /**
     * Restituisce la lista degli articoli nel carrello.
     *
     * @return Lista degli articoli nel carrello
     */
    public List<Articoli> getcarrellino() {
        return carrellino; 
    }

    /**
     * Restituisce lo stato del pagamento.
     *
     * @return {@code true} se il pagamento è stato effettuato, {@code false} altrimenti
     */
    public boolean getPagato() {
        return pagato;
    }

    /**
     * Meodo per aggiungere un prodotto al carrello attraverso la Lista di Object tramite la factory
     * @param inserire
     * @return boolean se il prodotto è stato inserito o menno
     */
    public boolean aggiungiProdotto(List<Object> inserire) {

        Articoli prodotto = Factory.factoryProdotto(inserire);
        if (prodotto == null) {
            logger.error("problem whit the insertion of the articolo from the factory");
            return false;
        }
        try {
            this.carrellino.add(prodotto);
            return true;
        } catch (Exception e) {
            logger.error("problem whit the insertion of the articolo");
            return false;
        }
    }

    /**
     * Metodo per aggiungere avendo già un Oggetto articolo 
     * @param articoloDaAggiungere 
     * @param count la quantità di questo prodotto 
     * @return boolean
     */
    public boolean aggiungi(Articoli articoloDaAggiungere, int count){
        if(articoloDaAggiungere != null){
            double y = articoloDaAggiungere.getPrezzoArticolo();
            articoloDaAggiungere.setPrezzoArticolo(count*y);
            float x = articoloDaAggiungere.getQuantitaArticolo();
            articoloDaAggiungere.setQuantitaArticolo(count*x);
            carrellino.add(articoloDaAggiungere);
            return true;
        }else{
            return false;
        }
    }


    /**
     * Metodo per eliminare un articolo dalla lista se il numero fornito in input è inferiore alla size del carrello
     * @param number
     * @return
     */
    public boolean elimina(int number){
        if (number < carrellino.size()) {
            carrellino.remove(number);
            return true;
        }
        return false;
    }


    /**
     * Metodo per modificare la QUantità dell'articolo in un determinato indice nella lista del carrello
     * @param id
     * @param quantity
     * @return
     */
    public boolean modificaQuantita(int id, int quantity) {
        if (id > carrellino.size()) {
            return false;
        }else{
            carrellino.get(id).setQuantitaArticolo(quantity);
            return true;
        }
    }


    /**
     * Metodo che fa tornare tutti gli id della lista del carrello con un metodo semplice per gestire la quantità, ossia ripetere l'id di quanto è la quantità
     * @return List<Integer>
     */
    public List<Integer> ritornaIDList(){
        List<Integer> ritornaList = new ArrayList<>();
        for (Articoli articoli : carrellino) {
            for (int i = 0; i < articoli.getQuantitaArticolo(); i++) {
                ritornaList.add(articoli.getId());
            }
        }
        return ritornaList;
    }


    /**
     * Altro metodo per far tornare la lista degli Id degli articoli sotto forma di stringa
     * @return String
     */
    public String getLista(){
        StringBuilder ritornaLista = new StringBuilder();
        for (Articoli articoli : carrellino) {
            ritornaLista.append(articoli.getNomeArticolo()+" : "+articoli.getQuantitaArticolo()+",  ");
        }
        return ritornaLista.toString();
    }
}
