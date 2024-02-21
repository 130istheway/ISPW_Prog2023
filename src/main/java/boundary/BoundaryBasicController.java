package boundary;

public class BoundaryBasicController {

    public static final String returnResetNegozioCommand = "RESETNEGOZIO";

    public static final String returnVisualizzaComand = "VISUALIZZA";
    
    public static String returnInserisciArticoloCommand(String negozio){
        return "AGGIUNGILISTA | "+negozio;
    }
    public static String returnConfermaOrdineCommand(String negozio){
        return "CONFERMALISTA | "+negozio; 
    }

    public static final String returnVisualizzaArticoliDaDBCommand =  "VISUALIZZAARTICOLODB";

    public static final String returnConfermaOrdiniCommand = "CONFERMAORDINI";


    public static final String returnCechOrdiniCommand = "RECUPERANORDINI";

    public static final String returnExitCommand = "EXIT | 0";
}
