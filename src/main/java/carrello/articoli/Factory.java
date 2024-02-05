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
