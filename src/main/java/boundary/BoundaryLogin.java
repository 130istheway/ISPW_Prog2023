package boundary;

public class BoundaryLogin {
    public static String returnLogin() {
        return "LOGIN";
    }
    public static String returnAutentication(String user, String pass){
        return  "user:" + user + ",pass:" + pass;
    }
}
