package util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ConvertiStringToArticolo {

    private ConvertiStringToArticolo() {
        throw new IllegalStateException("Utility class");
      }

      
    public static List<Object> convertToArticoloList(String string){

        List<String> input = new ArrayList<>();
        List<Object> output = new ArrayList<>();
        String yatta;
        String yatta2;

        StringTokenizer st = new StringTokenizer(string, "{");
        while (st.hasMoreTokens()) {
            yatta = st.nextToken();

            StringTokenizer st2 = new StringTokenizer(yatta.substring(0, yatta.length() -1), "|");
            while (st2.hasMoreTokens()) {
                yatta2 = st2.nextToken();
                input.add(yatta2);
            }
        }
        String token = input.get(0);
        output.add(token);
        int id =Integer.parseInt(input.get(1));
        output.add(id);
        String nome = input.get(2);
        output.add(nome);
        double prezo =Double.parseDouble(input.get(3));
        output.add(prezo);
        float qua =Float.parseFloat(input.get(4));
        output.add(qua);
        List<String> ingredienti;
        ingredienti = getList(input.get(5));
        output.add(ingredienti);
        double peso = Double.parseDouble(input.get(6));
        output.add(peso);
        int cottura = Integer.parseInt(input.get(7));
        output.add(cottura);
        int lievitaturetemp = Integer.parseInt(input.get(8));
        output.add(lievitaturetemp);
        boolean lievitatura = Boolean.parseBoolean(input.get(9));
        output.add(lievitatura);
        String descrizione = input.get(10);
        output.add(descrizione);
        

        return output;

    }

    private static List<String> getList(String a){
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