package boundary;

public class BoundaryBasicController {
    public static String returnResetNegozioCommand(){
        return "RESETNEGOZIO";
    }

    public static String returnVisualizzaComand(){
        return "VISUALIZZA";
    }
    public static String returnInserisciArticoloCommand(String negozio){
        return "AGGIUNGILISTA | "+negozio;
    }
    public static String returnConfermaOrdineCommand(String negozio){
        return "CONFERMALISTA | "+negozio; 
    }


    public static String returnVisualizzaArticoliDaDBCommand(){
        return "VISUALIZZAARTICOLODB";
    }
    public static String returnConfermaOrdiniCommand(){
        return "CONFERMAORDINI";
    }


    public static String returnCechOrdiniCommand(){
        return "RECUPERANORDINI";
    }

    public static String returnExitCommand(){
        return "EXIT | 0";
    }
}
