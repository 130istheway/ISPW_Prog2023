package model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.*;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.domain.Credential;

public class ConnectionFactory {
    
    static Logger logger = LogManager.getLogger(ConnectionFactory.class);

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
            logger.error(e.getMessage());
        } catch (SQLException e1) {
            logger.error(e1.getMessage());
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
            logger.error(e.getMessage());
        } catch (SQLException e1) {
            logger.error(e1.getMessage());
        }
    }

}
