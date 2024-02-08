package testing;

import carrello.articoli.articoli_alimentari.Pane;
import carrello.articoli.Articoli;
import carrello.articoli.Factory;
import org.junit.jupiter.api.Test;
import util.ConvertiStringToArticolo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Stefano
 */
class ArticoliTest {

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
        assertEquals(articolo.toString(),("{pane}{1|pane|0.0|0.0}{[farina, uova]|0.0}{0|0|true|qwertyuiopasdfghjklzxcvbnm}"));
        Articoli appoggio = articolo;
        assertEquals(appoggio.toString(),("{pane}{1|pane|0.0|0.0}{[farina, uova]|0.0}{0|0|true|qwertyuiopasdfghjklzxcvbnm}"));
    }

    @Test
    void testConvertiStringToArticolo(){
        List<String> lista = ConvertiStringToArticolo.convertToListStringFromString("{pane}{1|pane|0.0|0.0}{[farina, uova]|0.0}{0|0|true|qwertyuiopasdfghjklzxcvbnm}");
        assertEquals("pane", lista.get(0));
        assertEquals("1",lista.get(1));
        assertEquals("pane",lista.get(2));
        assertEquals("0.0",lista.get(3));
        assertEquals("0.0",lista.get(4));
        assertEquals("[farina, uova]",lista.get(5));
        assertEquals("0.0",lista.get(6));
        assertEquals("0",lista.get(7));
        assertEquals("0",lista.get(7));
        assertEquals("true",lista.get(9));
        assertEquals("qwertyuiopasdfghjklzxcvbnm",lista.get(10));

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

        assertEquals("pane", primo);
        assertEquals(1, secondo);
        assertEquals("pane", terzo);
        assertEquals(0.0, quarto);
        assertEquals(0.0, quinto);
        assertEquals("[farina, uova]", sesto.toString());
        assertEquals(0.0, settimo);
        assertEquals(0, ottavo);
        assertEquals(0, nono);
        assertTrue(decimo);
        assertEquals("qwertyuiopasdfghjklzxcvbnm",undicesimo);
    }

    @Test
    void testFactoryPerArticoli(){
        List<Object> lista = ConvertiStringToArticolo.convertToArticoloList("{pane}{1|pane|0.0|0.0}{[farina, uova]|0.0}{0|0|true|qwertyuiopasdfghjklzxcvbnm}");
        Articoli articolo = Factory.factoryProdotto(lista);
        assertEquals(articolo.toString(), ("{pane}{1|pane|0.0|0.0}{[farina, uova]|0.0}{0|0|true|qwertyuiopasdfghjklzxcvbnm}"));
    }
}
