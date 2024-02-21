package boundary;

public class BoundaryUserControl {
    
    private BoundaryUserControl(){
        throw new IllegalStateException("Utility class");
    }

    public static final String RETURNRIMUOVIARTICOLOCOMMAND = "RIMUOVIART";
    public static String returnRimuoviArticoloCommand(Integer posizione){
        return "RIMUOVIART | "+posizione;
    }
    public static final String RETURNVISUALIZZAARTICOLOCOMMAND = "VISUALIZZAART";
    public static String returnVisualizzaArticoloCommand(Integer posizione){
        return "VISUALIZZAART | "+posizione;
    }
    public static final String RETURNAGGIUNGIALLALISTACOMMAND = "AGGIUNGILISTA";
    public static String returnAggiungiAllaListaCommand(Integer pos, Integer quant){
        return "AGGIUNGILISTA | "+pos+"|"+quant;
    }

    public static final String RETURNORDINICONFERMATICOMMADN = "ORDINI";
}
