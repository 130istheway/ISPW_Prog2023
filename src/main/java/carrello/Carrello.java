package carrello;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.app.progettoispw202324.MenuController;

import carrello.articoli.Articoli;
import carrello.articoli.Factory;


/**
 * La classe {@code carello} gestisce il carrello dell'utente, contenente gli articoli selezionati per l'acquisto.
 * La lista del carrello utilizza il polimorfismo per gestire tutti gli articoli derivati dalla classe base {@code articoli}.
 * Include funzionalità per il controllo del pagamento e la gestione dei dati utente.
 * @author Stefano
 * @author Simone
 */
public class Carrello extends CarrelloCache{
    
    Logger logger = LogManager.getLogger(MenuController.class);
    
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

    public boolean aggiungi(Articoli articoloDaAggiungere, int count){
        if(articoloDaAggiungere != null){
            articoloDaAggiungere.setQuantitaArticolo(count);
            carrellino.add(articoloDaAggiungere);
            return true;
        }else{
            return false;
        }
    }


    public boolean elimina(int number){
        if (number < carrellino.size()) {
            carrellino.remove(number);
            return true;
        }
        return false;
    }


    public boolean modificaQuantita(int id, int quantity) {
        if (id > carrellino.size()) {
            return false;
        }else{
            carrellino.get(id).setQuantitaArticolo(quantity);
            return true;
        }
    }


    public List<Integer> ritornaIDList(){
        List<Integer> ritornaList = new ArrayList<>();
        for (Articoli articoli : carrellino) {
            for (int i = 0; i < articoli.getQuantitaArticolo(); i++) {
                ritornaList.add(articoli.getId());
            }
        }
        return ritornaList;
    }


    public String getLista(){
        StringBuilder ritornaLista = new StringBuilder();
        for (Articoli articoli : carrellino) {
            ritornaLista.append(articoli.getNomeArticolo()+" : "+articoli.getQuantitaArticolo()+",  ");
        }
        return ritornaLista.toString();
    }
}
