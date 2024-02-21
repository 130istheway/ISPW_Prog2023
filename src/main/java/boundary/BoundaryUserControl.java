package boundary;

public class BoundaryUserControl {
    public static String returnRimuoviArticoloCommand(Integer posizione){
        return "RIMUOVIART | "+posizione;
    }
    public static String returnVisualizzaArticoloCommand(Integer posizione){
        return "VISUALIZZAART | "+posizione;
    }

    public static String returnAggiungiAllaListaCommand(Integer pos, Integer quant){
        return "AGGIUNGILISTA | "+pos+"|"+quant;
    }

    public static final String returnOrdiniConfermatiCommadn = "ORDINI";
}
