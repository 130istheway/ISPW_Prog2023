package boundary;

public class BoundaryLogin {
    private BoundaryLogin(){
        throw new IllegalStateException("Utility class");
    }

    public static final String RETURNLOGIN = "LOGIN";
    public static String returnAutentication(String user, String pass){
        return  "user:" + user + ",pass:" + pass;
    }
}
