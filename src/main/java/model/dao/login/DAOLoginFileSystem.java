package model.dao.login;

import model.domain.Credential;
import model.domain.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DAOLoginFileSystem {

    static Logger logger = LogManager.getLogger(DAOLoginFileSystem.class);

    public Credential execute(Object... params){
        String username = (String) params[0];
        String password = (String) params[1];

        List<String> lista = new ArrayList<>();
        
        try (BufferedReader input = new BufferedReader(new FileReader("C:\\Users\\stefa\\Desktop\\ISPW_Prog2023\\src\\main\\java\\util\\Sensitive\\login.txt"))){

            String line;
            while ((line = input.readLine()) != null) {
                lista.add(line); // Process each line here
            }

        }catch(IOException e) {
            logger.fatal("Non è stato possibile recuperare il file login.txt");
        }

        for (String string : lista) {
            String secret = string.substring(0, string.length()-1);
            if (secret.equals(username+password)){
                int ruolo = Integer.parseInt(string.substring(string.length()-1));
                return new Credential(username, password, Role.values()[ruolo]);
            }
        }
        return new Credential(null, null, Role.NONE);
    }
}
