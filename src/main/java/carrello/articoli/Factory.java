package carrello.articoli;

import java.util.List;

import carrello.articoli.articoliAlimentari.*;
import model.domain.LivelloInformazione;
import util.SingletonLogger;


public abstract class Factory {
    
    static SingletonLogger log = SingletonLogger.getInstance();

    private Factory() {
        throw new IllegalStateException("Utility class");
    }

    public static Articoli factoryProdotto(List<Object> ins){
        
        Articoli art;
        log.sendInformazione(LivelloInformazione.TRACE ,"Si sta inserendo da lista per " + ins.get(0));

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
