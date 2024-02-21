package boundary;

public class BoundaryGestioneNotifica {
    public static String returnConfermaNotificaCommand(Integer pos, String scelta){
        return "CONFERMANOTIFICA | "+pos+"|"+scelta;
    }
}
