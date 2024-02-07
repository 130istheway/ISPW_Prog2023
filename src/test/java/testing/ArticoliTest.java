package testing;

import carrello.articoli.articoli_alimentari.Pane;
import carrello.articoli.Articoli;
import carrello.articoli.Factory;
import org.junit.jupiter.api.Test;
import util.ConvertiStringToArticolo;

import java.util.ArrayList;
import java.util.List;

public class ArticoliTest {

    @Test
    void controllaConversioneStringaArticolo(){
        Pane articolo = new Pane();
        articolo.setId(1);
        articolo.setNomeArticolo("pane");
        articolo.setPrezzoArticolo(0);
        articolo.setQuantitaArticolo(0);
        List<String> ingredienti = new ArrayList<>();
        ingredienti.add("farina");
        ingredienti.add("uova");
        articolo.setIngredienti(ingredienti);
        articolo.setPeso(0);
        articolo.setTempoCottura(0);
        articolo.setTempoLievitatura(0);
        articolo.setLievitatura(true);
        articolo.setDescrizione("qwertyuiopasdfghjklzxcvbnm");
        assert(articolo.toString().equals("{pane}{1|pane|0.0|0.0}{[farina, uova]|0.0}{0|0|true|qwertyuiopasdfghjklzxcvbnm}"));
        Articoli appoggio = articolo;
        assert(appoggio.toString().equals("{pane}{1|pane|0.0|0.0}{[farina, uova]|0.0}{0|0|true|qwertyuiopasdfghjklzxcvbnm}"));
    }

    @Test
    void testConvertiStringToArticolo(){
        List<String> lista = ConvertiStringToArticolo.convertToListStringFromString("{pane}{1|pane|0.0|0.0}{[farina, uova]|0.0}{0|0|true|qwertyuiopasdfghjklzxcvbnm}");
        assert(lista.get(0).equals("pane"));
        assert(lista.get(1).equals("1"));
        assert(lista.get(2).equals("pane"));
        assert(lista.get(3).equals("0.0"));
        assert(lista.get(4).equals("0.0"));
        assert(lista.get(5).equals("[farina, uova]"));
        assert(lista.get(6).equals("0.0"));
        assert(lista.get(7).equals("0"));
        assert(lista.get(7).equals("0"));
        assert(lista.get(9).equals("true"));
        assert(lista.get(10).equals("qwertyuiopasdfghjklzxcvbnm"));

        List<Object> listaOggetti = ConvertiStringToArticolo.convertToArticoloList("{pane}{1|pane|0.0|0.0}{[farina, uova]|0.0}{0|0|true|qwertyuiopasdfghjklzxcvbnm}");

        String primo = (String)listaOggetti.get(0);
        int secondo = (int)listaOggetti.get(1);
        String terzo = (String)listaOggetti.get(2);
        double quarto = (double)listaOggetti.get(3);
        float quinto = (float)listaOggetti.get(4);
        List<String> sesto = (List<String>)listaOggetti.get(5);
        double settimo = (double)listaOggetti.get(6);
        int ottavo = (int)listaOggetti.get(7);
        int nono = (int)listaOggetti.get(7);
        boolean decimo = (boolean)listaOggetti.get(9);
        String undicesimo = (String)listaOggetti.get(10);

        assert(primo.equals("pane"));
        assert(secondo == 1);
        assert(terzo.equals("pane"));
        assert(Math.abs(quarto - 0.0) < 0.000001);
        assert(Math.abs(quinto - 0.0) < 0.000001);
        assert(sesto.toString().equals("[farina, uova]"));
        assert((Math.abs(settimo - 0.0) < 0.000001));
        assert(ottavo == 0);
        assert(nono == 0);
        assert(decimo);
        assert(undicesimo.equals("qwertyuiopasdfghjklzxcvbnm"));
    }

    @Test
    void testFactoryPerArticoli(){
        List<Object> lista = ConvertiStringToArticolo.convertToArticoloList("{pane}{1|pane|0.0|0.0}{[farina, uova]|0.0}{0|0|true|qwertyuiopasdfghjklzxcvbnm}");
        Articoli articolo = Factory.factoryProdotto(lista);
        assert(articolo.toString().equals("{pane}{1|pane|0.0|0.0}{[farina, uova]|0.0}{0|0|true|qwertyuiopasdfghjklzxcvbnm}"));
    }
}
