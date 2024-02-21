package boundary;

public class BoundaryNegozioControl {
    private BoundaryNegozioControl(){
        throw new IllegalStateException("Utility class");
    }

    public static final String RETURNAGGIUNGIARTICOLODBCOMMAND = "AGGIUNGIARTICOLODB";
    public static String returnAggiungiArticoloAlDB(String lista){
        return "AGGIUNGIARTICOLODB | "+lista;
    }
}
