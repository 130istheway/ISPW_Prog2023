package model.dao.login;

import model.dao.ConnectionFactory;
import model.domain.Credential;
import model.domain.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class DAOLoginFileSystem {

    static Logger logger = LogManager.getLogger(ConnectionFactory.class);

    public Credential execute(Object... params){
        String username = (String) params[0];
        String password = (String) params[1];

        List<String> lista = new ArrayList<>();
        
        try (InputStream input = new FileInputStream("C:\\Users\\stefa\\Desktop\\ISPW_Prog2023\\src\\main\\java\\util\\Sensitive\\login.properties")){
            
            OrderedProperties properties = new OrderedProperties();
            properties.load(input);
            Enumeration<?> keys = properties.propertyNames();
            
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                String value = properties.getProperty(key);
                String key2 = (String) keys.nextElement();
                String value2 = properties.getProperty(key2);
                String key3 = (String) keys.nextElement();
                String value3 = properties.getProperty(key3);
                lista.add(value+value2+value3);
            }
        }catch(IOException e) {
            logger.fatal("Non è stato possibile recuperare il file login.properties");
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
