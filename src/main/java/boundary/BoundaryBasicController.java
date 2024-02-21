package boundary;

public class BoundaryBasicController {
    private BoundaryBasicController(){
        throw new IllegalStateException("Utility class");
    }

    public static final String RETURNRESETNEGOZIOCOMMAND = "RESETNEGOZIO";

    public static final String RETURNVISUALIZZACOMAND = "VISUALIZZA";
    
    public static String returnInserisciArticoloCommand(String negozio){
        return "AGGIUNGILISTA | "+negozio;
    }
    public static String returnConfermaOrdineCommand(String negozio){
        return "CONFERMALISTA | "+negozio; 
    }

    public static final String RETURNVISUALIZZAARTICOLIDADBCOMMAND =  "VISUALIZZAARTICOLODB";

    public static final String RETURNCONFERMAORDINICOMMAND = "CONFERMAORDINI";


    public static final String RETURNCECHORDINICOMMAND = "RECUPERANORDINI";

    public static final String RETURNEXITCOMMAND = "EXIT | 0";
}
