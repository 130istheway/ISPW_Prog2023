package boundary;

public class BoundaryBasicController {
    private BoundaryBasicController(){
        throw new IllegalStateException("Utility class");
    }

    public static final String RETURNWRITEBACKCOMMAND = "WRITEBACK";

    public static final String RETURNRESETNEGOZIOCOMMAND = "RESETNEGOZIO";
    public static final String RETURNVISUALIZZACOMAND = "VISUALIZZA";
    public static final String RETURNINSERISCIARTICOLOCOMMAND = "AGGIUNGILISTA";
    public static final String RETURNCONFERMAORDINECOMMAND = "CONFERMALISTA";
    public static String returnInserisciArticoloCommandVariable(String negozio){
        return "AGGIUNGILISTA | "+negozio;
    }
    public static String returnConfermaOrdineCommandVariable(String negozio){
        return "CONFERMALISTA | "+negozio; 
    }

    public static final String RETURNVISUALIZZAARTICOLIDADBCOMMAND =  "VISUALIZZAARTICOLODB";
    public static final String RETURNCONFERMAORDINICOMMAND = "CONFERMAORDINI";

    public static final String RETURNCECHORDINICOMMAND = "RECUPERANORDINI";
    public static final String RETURNEXITCOMMAND = "EXIT | 0";

    public static final String RETURNWRITEBACKMODECOMMAND = "WRITEBACK MODE";
    public static final String RETURNSTOPWRITEBACKCOMMAND = "STOPWRITEBACK";
}
