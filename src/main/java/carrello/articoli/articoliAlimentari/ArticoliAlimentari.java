package carrello.articoli.articoliAlimentari;

import java.util.ArrayList;
import java.util.List;

import carrello.articoli.Articoli;

/**
 * La classe {@code articoliAlimentari} rappresenta un tipo di articolo che è anche alimentare,
 * con attributi specifici come la lista degli ingredienti e il peso.
 * Estende la classe {@code articoli}.
 * @author Stefano
 * @author Simone
 */
public class ArticoliAlimentari extends Articoli {

    /** Lista degli ingredienti dell'articolo alimentare */
    private List<String> ingredienti;

    /** Peso dell'articolo alimentare */
    private double peso;

    /**
     * Costruttore di default. Inizializza gli attributi con valori predefiniti.
     */
    public ArticoliAlimentari() {
        super();
        this.ingredienti = new ArrayList<>();
        this.peso = 0.0;
    }

    /**
     * Costruttore che consente di specificare tutti gli attributi dell'articolo alimentare.
     *
     * @param nomeArticolo    Nome dell'articolo
     * @param prezzoArticolo  Prezzo dell'articolo
     * @param quantitaArticolo Quantità dell'articolo
     * @param ingredienti      Lista degli ingredienti
     * @param peso             Peso dell'articolo
     */
    public ArticoliAlimentari(String nomeArticolo, double prezzoArticolo, float quantitaArticolo, List<String> ingredienti, double peso) {
        super(nomeArticolo, prezzoArticolo, quantitaArticolo);
        this.ingredienti = ingredienti;
        this.peso = peso;
    }

    /**
     * Imposta la lista degli ingredienti dell'articolo alimentare.
     *
     * @param ingredienti Lista degli ingredienti
     */
    public void setIngredienti(List<String> ingredienti) {
        this.ingredienti = ingredienti;
    }

    /**
     * Imposta il peso dell'articolo alimentare.
     *
     * @param peso Peso dell'articolo
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * Restituisce la lista degli ingredienti dell'articolo alimentare.
     *
     * @return Lista degli ingredienti
     */
    public List<String> getIngredienti() {
        return ingredienti;
    }

    /**
     * Restituisce il peso dell'articolo alimentare.
     *
     * @return Peso dell'articolo
     */
    public double getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        String linea = "|";
        String str;
        str = super.toString() + "{" + ingredienti + linea + peso + "}";
        return str;
    }
}