package carrello.articoli;

import java.util.List;

import carrello.articoli.articoliAlimentari.*;


public abstract class Factory {

    public static Articoli factoryProdotto(List<Object> ins){
        
        Articoli art;
        System.out.println("Si sta inserendo da riga di comando" + ins.get(0));

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
