package carrello.articoli;
/**
 * La classe {@code articoli} rappresenta una classe di base per gli articoli.
 * Contiene attributi come ID, nome, prezzo e quantità dell'articolo.
 *
 * <p>Gli ID vengono generati in modo casuale utilizzando {@code get_Random()}.
 * @author Stefano
 * @author Simone
 */

 
public class Articoli {

    java.util.Random rand = new java.util.Random();

    /** ID univoco dell'articolo */
    private int id;

    /** Nome dell'articolo */
    private String nomeArticolo;

    /** Prezzo dell'articolo */
    private double prezzoArticolo;

    /** Quantità disponibile dell'articolo */
    private float quantitaArticolo;

    /**
     * Costruttore di default.
     * Inizializza gli attributi con valori predefiniti.
     */
    public Articoli() {
        setId(getRandom());
        this.nomeArticolo = "Prova";
        this.prezzoArticolo = 0.0;
        this.quantitaArticolo = 0;
    }

    /**
     * Costruttore che consente di specificare nome, prezzo e quantità dell'articolo.
     * L'ID viene generato casualmente utilizzando {@code get_Random()}.
     *
     * @param nome_articolo     Nome dell'articolo
     * @param prezzo_articolo   Prezzo dell'articolo
     * @param quantita_articolo Quantità dell'articolo
     */
    public Articoli(String nomeArticolo, double prezzoArticolo, float quantitaArticolo) {
        setId(getRandom());
        this.nomeArticolo = nomeArticolo;
        this.prezzoArticolo = prezzoArticolo;
        this.quantitaArticolo = quantitaArticolo;
    }

    /**
     * Imposta l'ID dell'articolo.
     *
     * @param id ID univoco dell'articolo
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Imposta il nome dell'articolo.
     *
     * @param nome_articolo Nome dell'articolo
     */
    public void setNomeArticolo(String nomeArticolo) {
        this.nomeArticolo = nomeArticolo;
    }

    /**
     * Imposta il prezzo dell'articolo.
     *
     * @param prezzo_articolo Prezzo dell'articolo
     */
    public void setPrezzoArticolo(double prezzoArticolo) {
        this.prezzoArticolo = prezzoArticolo;
    }

    /**
     * Imposta la quantità dell'articolo disponibile.
     *
     * @param quantita_articolo Quantità dell'articolo
     */
    public void setQuantitaArticolo(float quantitaArticolo) {
        this.quantitaArticolo = quantitaArticolo;
    }

    /**
     * Restituisce l'ID dell'articolo.
     *
     * @return ID dell'articolo
     */
    public int getId() {
        return id;
    }

    /**
     * Restituisce il nome dell'articolo.
     *
     * @return Nome dell'articolo
     */
    public String getNomeArticolo() {
        return nomeArticolo;
    }

    /**
     * Restituisce il prezzo dell'articolo.
     *
     * @return Prezzo dell'articolo
     */
    public double getPrezzoArticolo() {
        return prezzoArticolo;
    }

    /**
     * Restituisce la quantità dell'articolo disponibile.
     *
     * @return Quantità dell'articolo
     */
    public float getQuantitaArticolo() {
        return quantitaArticolo;
    }

    /**
     * Genera un numero casuale e restituisce il suo valore assoluto come ID.
     *
     * @return ID generato casualmente
     */
    private int getRandom() {
        return rand.nextInt();
    }

    public String toString() {
        String linea = "|";
        String str;
        str = "{" + id + linea + nomeArticolo + linea + prezzoArticolo + linea + quantitaArticolo +"}";
        return str;
    }
}
