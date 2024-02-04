package com.app.progettoispw202324.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringToOrdini {
    static public String coverti(String string){
        String output = "";
        String[] lollo = string.split("_");

        output = "ID : " + lollo[0] + "\t" +  "ID negozio : " + lollo[1] + "\t" + "ID utente : " + lollo[2] + "\t" + "DATA : " + lollo[4] + "\n" + "LISTA : " + lollo[3];
        return output;
    }
}
