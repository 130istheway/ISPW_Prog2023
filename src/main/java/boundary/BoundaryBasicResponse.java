package boundary;

public class BoundaryBasicResponse {
    private BoundaryBasicResponse(){
        throw new IllegalStateException("Utility class");
    }

    public static final String RETURNOK = "OK";
    public static final String RETURNNULL = "NULL";
    public static final String RETURNSI = "SI";
    public static final String RETURNNO = "NO";
    public static final String RETURNNONRICONOSCIUTO = "NONRICONOSCIUTO";
    public static final String RETURNNOTAVALIDCOMAND = "NOTAVALIDCOMAND";
    public static final String RETURNPREGO = "PREGO";
    public static final String RETURNSTOPIT = "STOPIT";
    public static final String RETURNNAUTORIZZATO = "NON AUTORIZATO";
    public static final String RETURNDECIDI = "DECIDI";

    public static String returnSiVariable(Integer size){
        return RETURNSI+" | "+size;
    }
    public static String returnNoVariable(String message){
        return RETURNNO+" | "+message;
    }
    public static String returnPregoVariable(String ordini){
        return RETURNPREGO+" | "+ordini;
    }
}
