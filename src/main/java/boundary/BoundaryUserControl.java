package boundary;

public class BoundaryUserControl {
    
    private BoundaryUserControl(){
        throw new IllegalStateException("Utility class");
    }

    public static final String RETURNRIMUOVIARTICOLOCOMMAND = "RIMUOVIART";
    public static String returnRimuoviArticoloCommandVariable(Integer posizione){
        return "RIMUOVIART | "+posizione;
    }
    public static final String RETURNVISUALIZZAARTICOLOCOMMAND = "VISUALIZZAART";
    public static String returnVisualizzaArticoloCommandVariable(Integer posizione){
        return "VISUALIZZAART | "+posizione;
    }
    public static final String RETURNAGGIUNGIALLALISTACOMMAND = "AGGIUNGILISTA";
    public static String returnAggiungiAllaListaCommandVariable(Integer pos, Integer quant){
        return "AGGIUNGILISTA | "+pos+"|"+quant;
    }

    public static final String RETURNORDINICONFERMATICOMMADN = "ORDINI";
}
