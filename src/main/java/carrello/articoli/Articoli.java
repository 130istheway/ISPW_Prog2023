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
    /** ID univoco dell'articolo */
    private int id;

    /** Nome dell'articolo */
    private String nome_articolo;

    /** Prezzo dell'articolo */
    private double prezzo_articolo;

    /** Quantità disponibile dell'articolo */
    private float quantita_articolo;

    /**
     * Costruttore di default.
     * Inizializza gli attributi con valori predefiniti.
     */
    public Articoli() {
        setId(get_Random());
        this.nome_articolo = "HUBBUHB";
        this.prezzo_articolo = 0.0;
        this.quantita_articolo = 0;
    }

    /**
     * Costruttore che consente di specificare nome, prezzo e quantità dell'articolo.
     * L'ID viene generato casualmente utilizzando {@code get_Random()}.
     *
     * @param nome_articolo     Nome dell'articolo
     * @param prezzo_articolo   Prezzo dell'articolo
     * @param quantita_articolo Quantità dell'articolo
     */
    public Articoli(String nome_articolo, double prezzo_articolo, float quantita_articolo) {
        setId(get_Random());
        this.nome_articolo = nome_articolo;
        this.prezzo_articolo = prezzo_articolo;
        this.quantita_articolo = quantita_articolo;
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
    public void setNome_articolo(String nome_articolo) {
        this.nome_articolo = nome_articolo;
    }

    /**
     * Imposta il prezzo dell'articolo.
     *
     * @param prezzo_articolo Prezzo dell'articolo
     */
    public void setPrezzo_articolo(double prezzo_articolo) {
        this.prezzo_articolo = prezzo_articolo;
    }

    /**
     * Imposta la quantità dell'articolo disponibile.
     *
     * @param quantita_articolo Quantità dell'articolo
     */
    public void setQuantita_articolo(float quantita_articolo) {
        this.quantita_articolo = quantita_articolo;
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
    public String getNome_articolo() {
        return nome_articolo;
    }

    /**
     * Restituisce il prezzo dell'articolo.
     *
     * @return Prezzo dell'articolo
     */
    public double getPrezzo_articolo() {
        return prezzo_articolo;
    }

    /**
     * Restituisce la quantità dell'articolo disponibile.
     *
     * @return Quantità dell'articolo
     */
    public float getQuantita_articolo() {
        return quantita_articolo;
    }

    /**
     * Genera un numero casuale e restituisce il suo valore assoluto come ID.
     *
     * @return ID generato casualmente
     */
    private int get_Random() {
        return (int) Math.abs(Math.random());
    }

    public String toString() {
        String linea = "|";
        String str;
        str = "{" + id + linea + nome_articolo + linea + prezzo_articolo + linea + quantita_articolo +"}";
        return str;
    }
}
