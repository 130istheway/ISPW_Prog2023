package util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PayloadToCredential {

    public List<String> getCredentials(String string) {
        List<String> parameters = new ArrayList<>();
        String yatta2;
        
        StringTokenizer yatta = new StringTokenizer(string, ",");
            while (yatta.hasMoreTokens()) {
                yatta2 = yatta.nextToken();
                parameters.add(yatta2.substring(5, yatta2.length()));
            }

        return parameters;
    }

}
