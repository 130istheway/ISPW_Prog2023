package model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.*;

import java.util.Properties;

import model.domain.Credential;
import model.domain.LivelloInformazione;
import util.SingletonLogger;

public class ConnectionFactory {

    static SingletonLogger log = SingletonLogger.getInstance();

    private static Connection connection;

    private ConnectionFactory() {}

    static {
        try (InputStream input = new FileInputStream("C:\\Users\\stefa\\Desktop\\ISPW_Prog2023\\src\\main\\java\\util\\Sensitive\\db.properties")){

            Properties properties = new Properties();
            properties.load(input);

            String connectionUrl = properties.getProperty("CONNECTION_URL");
            String username = properties.getProperty("LOGIN_USER");
            String password = properties.getProperty("LOGIN_PASS");

            connection = DriverManager.getConnection(connectionUrl, username, password);

        } catch (IOException e) {
            log.sendInformazione(LivelloInformazione.ERROR, e.getMessage());
        } catch (SQLException e1) {
            log.sendInformazione(LivelloInformazione.ERROR, e1.getMessage());
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    public static void changeRole(Credential credentials){
        try (InputStream input = new FileInputStream("C:\\Users\\stefa\\Desktop\\ISPW_Prog2023\\src\\main\\java\\util\\Sensitive\\db.properties")){

            Properties properties = new Properties();
            properties.load(input);

            String connectionUrl = properties.getProperty("CONNECTION_URL");
            String username = properties.getProperty(credentials.getRole() +("_USER"));
            String password = properties.getProperty(credentials.getRole() +("_PASS"));

            connection = DriverManager.getConnection(connectionUrl, username, password);
            
        } catch (IOException e) {
            log.sendInformazione(LivelloInformazione.ERROR, e.getMessage());
        } catch (SQLException e1) {
            log.sendInformazione(LivelloInformazione.ERROR, e1.getMessage());
        }
    }

}
