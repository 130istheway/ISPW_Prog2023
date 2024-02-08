package carrello.articoli;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import carrello.articoli.articoli_alimentari.*;


public abstract class Factory {
    
    Logger logger = LogManager.getLogger(Factory.class);

    private Factory() {
        throw new IllegalStateException("Utility class");
    }


    /**
     * Factory per creare i diversi Articoli per sapere quale articolo creare va a vedere nello specifico il primo elemento dell lista di Object ma in realtà puà andare a vedere tutti gli elementi, ma tanto non conosce che tipo sono e in che ordine sono messi quindi è inutile
     * 
     * @param ins List<Object>
     * @return un Oggetto di tipo Articoli 
     */
    public static Articoli factoryProdotto(List<Object> ins){
        
        Articoli art;

        String tipo = (String)ins.get(0);
        ins.remove(0);

        switch (tipo) {
            case "pane":
                Pane pane = new Pane();
                pane.inserisciDati(ins);
                art = pane;
                break;

            case "pizza":
                Pizza pizza = new Pizza();
                pizza.inserisciDati(ins);
                art = pizza;
                break;

            //case per altri prodotti, basta aggiungere qui la condizzione
            
            default:
                art = null;
                break;
        }
        return art;
    }
}
