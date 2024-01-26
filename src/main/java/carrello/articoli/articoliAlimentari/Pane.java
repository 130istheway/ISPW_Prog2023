package carrello.articoli.articoliAlimentari;

import java.util.List;

/**
 * La classe {@code pane} rappresenta un tipo specifico di articolo alimentare,
 * con attributi specifici come tempo di cottura, tempo di lievitatura, lievitatura naturale e descrizione.
 * Estende la classe {@code articoliAlimentari}.
 * @author Stefano
 */
public class Pane extends ArticoliAlimentari{

    private int tempoCottura;
    private int tempoLievitatura;
    private boolean lievitatura;
    private String descrizione;

    /**
     * Costruttore di default. Inizializza gli attributi con valori predefiniti.
     */
    public Pane() {
        super();
        this.tempoCottura = 0;
        this.tempoLievitatura = 0;
        this.descrizione = "NULLPANE";
        this.lievitatura = false;
    }

    /**
     * Costruttore che consente di specificare tutti gli attributi del pane.
     *
     * @param nome_articolo    Nome dell'articolo
     * @param prezzo_articolo  Prezzo dell'articolo
     * @param quantita_articolo Quantità dell'articolo
     * @param ingredienti      Lista degli ingredienti
     * @param peso             Peso dell'articolo
     * @param tempoCottura     Tempo di cottura del pane
     * @param tempoLievitatura Tempo di lievitatura del pane
     * @param lievitatura       Flag per indicare se la lievitatura è naturale o meno
     * @param descrizione      Descrizione dell'articolo
     */
    public Pane(String nomeArticolo, double prezzoArticolo, float quantitaArticolo, List<String> ingredienti, double peso, int tempoCottura, int tempoLievitatura, boolean lievitatura, String descrizione) {
        super(nomeArticolo, prezzoArticolo, quantitaArticolo, ingredienti, peso);
        this.tempoCottura = tempoCottura;
        this.tempoLievitatura = tempoLievitatura;
        this.lievitatura = lievitatura;
        this.descrizione = descrizione;
    }

    /**
     * Imposta il tempo di cottura del pane.
     *
     * @param tempoCottura Tempo di cottura del pane
     */
    public void setTempoCottura(int tempoCottura) {
        this.tempoCottura = tempoCottura;
    }

    /**
     * Imposta il tempo di lievitatura del pane.
     *
     * @param tempoLievitatura Tempo di lievitatura del pane
     */
    public void setTempoLievitatura(int tempoLievitatura) {
        this.tempoLievitatura = tempoLievitatura;
    }

    /**
     * Imposta la lievitatura del pane.
     *
     * @param lievitatura Flag per indicare se la lievitatura è naturale o meno
     */
    public void setLievitatura(boolean lievitatura) {
        this.lievitatura = lievitatura;
    }

    /**
     * Imposta la descrizione dell'articolo.
     *
     * @param descrizione Descrizione dell'articolo
     */
    public void setDescrizione(String descrizione) {
        if (descrizione == null) {
            this.descrizione = "La descrizione per il pane deve essere ancora aggiornata dal fornaio";
        }
        this.descrizione = descrizione;
    }

    /**
     * Restituisce il tempo di cottura del pane.
     *
     * @return Tempo di cottura del pane
     */
    public int getTempoCottura() {
        return tempoCottura;
    }

    /**
     * Restituisce il tempo di lievitatura del pane.
     *
     * @return Tempo di lievitatura del pane
     */
    public int getTempoLievitatura() {
        return tempoLievitatura;
    }

    /**
     * Restituisce la lievitatura del pane.
     *
     * @return True se la lievitatura è naturale, altrimenti false
     */
    public boolean getLievitatura() {
        return lievitatura;
    }

    /**
     * Restituisce la descrizione dell'articolo.
     *
     * @return Descrizione dell'articolo
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Modifica la quantità dell'articolo con validazione.
     *
     * @param quantita Nuova quantità dell'articolo
     * @return True se la quantità è stata modificata con successo, altrimenti false
     */
    public boolean Cambia_Quantita_articolo(float quantita) {
        if (getQuantitaArticolo() > quantita && quantita > ((1 / 4))) {
            setQuantitaArticolo(quantita);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Override del metodo setIngredienti per gestire il caso in cui la lista degli ingredienti è vuota.
     *
     * @param ingredienti Lista degli ingredienti
     */
    @Override
    public void setIngredienti(List<String> ingredienti) {
        if (ingredienti.isEmpty()) {
            ingredienti.add("Ingredienti non disponibili");
            setIngredienti(ingredienti);
        }
        super.setIngredienti(ingredienti);
    }

    
    @SuppressWarnings (value="unchecked")

    public void inserisciDati(List<Object> ins){

        setId((int)ins.get(0));

        setNomeArticolo((String)ins.get(1));
        
        setPrezzoArticolo((double)ins.get(2));
        
        setQuantitaArticolo((float)ins.get(3));
        
        setIngredienti((List<String>)ins.get(4));
        
        setPeso((double)ins.get(5));
                
        setTempoCottura((int)ins.get(6));

        setTempoLievitatura((int)ins.get(7));

        setLievitatura((boolean)ins.get(8));

        setDescrizione((String)ins.get(9));

    }

    public String toString() {
        String linea = "|";
        String str;
        str = "{pane} | " +  super.toString() + "{" + tempoCottura + linea + tempoLievitatura + linea + lievitatura + linea + descrizione + "}";
        return str;
    }

}

