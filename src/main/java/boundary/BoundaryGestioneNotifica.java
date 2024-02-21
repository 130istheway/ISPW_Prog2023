package boundary;

public class BoundaryGestioneNotifica {
    private BoundaryGestioneNotifica(){
        throw new IllegalStateException("Utility class");
    }

    public static final String RETURNCONFERMANOTIFICACOMMAND = "CONFERMANOTIFICA";
    public static String returnConfermaNotificaCommand(Integer pos, String scelta){
        return "CONFERMANOTIFICA | "+pos+"|"+scelta;
    }

    public static String returnVisualizzaArticoloCommand(String messaggio){
        return "SINOTI | "+messaggio;
    }
    public static final String RETURNVISUALIZZAARTICOLOCOMMAND = "NO | Elemento non esistente";
}
