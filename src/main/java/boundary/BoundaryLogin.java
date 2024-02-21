package boundary;

public class BoundaryLogin {
    public static final String returnLogin = "LOGIN";
    public static String returnAutentication(String user, String pass){
        return  "user:" + user + ",pass:" + pass;
    }
}
