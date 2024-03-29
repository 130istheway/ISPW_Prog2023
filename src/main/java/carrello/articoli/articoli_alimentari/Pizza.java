package carrello.articoli.articoli_alimentari;

import java.util.List;

/**
 * La classe {@code pizza} rappresenta un tipo specifico di articolo alimentare,
 * con attributi specifici come tempo di cottura, lievitatura, dimensione e descrizione.
 * Estende la classe {@code articoliAlimentari}.
 * @author Stefano
 */
public class Pizza extends ArticoliAlimentari {

    private int tempoCottura;
    private boolean lievitatura;
    private boolean dimensione;
    private String descrizione;

    /**
     * Costruttore di default. Inizializza gli attributi con valori predefiniti.
     */
    public Pizza() {
        super();
        this.tempoCottura = 0;
        this.lievitatura = false;
        this.dimensione = false;
        this.descrizione = "NULLPIZZA";
    }
    

    /**
     * Imposta il tempo di cottura della pizza.
     *
     * @param tempoCottura Tempo di cottura della pizza
     */
    public void setTempoCottura(int tempoCottura) {
        this.tempoCottura = tempoCottura;
    }

    /**
     * Imposta la lievitatura della pizza.
     *
     * @param lievitatura Flag per indicare se la pizza è lievitata o meno
     */
    public void setLievitatura(boolean lievitatura) {
        this.lievitatura = lievitatura;
    }

    /**
     * Imposta la dimensione della pizza.
     *
     * @param dimensione Flag per indicare la dimensione della pizza
     */
    public void setDimensione(boolean dimensione) {
        this.dimensione = dimensione;
    }

    /**
     * Imposta la descrizione della pizza.
     *
     * @param descrizione Descrizione della pizza
     */
    public void setDescrizione(String descrizione) {
        if (descrizione == null) {
            this.descrizione = "La descrizione per la pizza deve essere ancora aggiornata dal fornaio";
        }
        this.descrizione = descrizione;
    }

    /** */
    public int getTempoCottura() {
        return tempoCottura;
    }

    /** */
    public boolean getLievitatura() {
        return lievitatura;
    }

    /** */
    public boolean getDimensione() {
        return dimensione;
    }

    /* */
    public String getDescrizione() {
        return descrizione;
    }


    /**
     * Modifica la quantità dell'articolo con validazione.
     *
     * @param quantita Nuova quantità dell'articolo
     */
    public void cambiaQuantitaArticolo(int quantita) throws IllegalArgumentException{
        if (getQuantitaArticolo() > quantita && getQuantitaArticolo() > 0) {
            setQuantitaArticolo(quantita);
        } else {
            throw new IllegalArgumentException("illegal quantità");
        }
    }


    /**
     * Il metodo di inserimento sull'articolo
     * 
     * @param ins lista di object per inserire nell'articolo
     */
    @SuppressWarnings (value="unchecked")
    public void inserisciDati(List<Object> ins){
        
        setId((int)ins.get(0));

        setNomeArticolo((String)ins.get(1));
                
        setPrezzoArticolo((double)ins.get(2));
                
        setQuantitaArticolo((float)ins.get(3));
        
        setIngredienti((List<String>)ins.get(4));
                
        setPeso((double)ins.get(5));
                
        setTempoCottura((int)ins.get(6));

        setLievitatura((boolean)ins.get(7));

        setDimensione((boolean)ins.get(8));

        setDescrizione((String)ins.get(9));
    
    }
    
    
    /**
     * Override del metodo che restituisce la Stringa con all'interno i componenti dell'articolo
     * 
     * @return Stringa
     */
    @Override
    public String toString() {
        String linea = "|";
        String str;
        str = "{pizza}" + super.toString() + "{" + tempoCottura + linea + dimensione + linea + lievitatura + linea + descrizione + "}";
        return str;
    }

}

