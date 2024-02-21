package boundary;

public class BoundaryGestioneNotifica {
    private BoundaryGestioneNotifica(){
        throw new IllegalStateException("Utility class");
    }

    public static String returnConfermaNotificaCommand(Integer pos, String scelta){
        return "CONFERMANOTIFICA | "+pos+"|"+scelta;
    }
}
