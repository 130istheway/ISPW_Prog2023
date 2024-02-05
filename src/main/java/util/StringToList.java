package util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringToList{

    private StringToList(){
        throw new IllegalStateException("Utility class");
    }
    
    public static List<String> getListString(String a){
        List<String> output = new ArrayList<>();
        String yatta;
        a = a.substring(1, a.length()-1);
        StringTokenizer st = new StringTokenizer(a, ",");
        while (st.hasMoreTokens()) {
            yatta = st.nextToken();
            output.add(yatta.trim());
        }
        return output;
    }
}
