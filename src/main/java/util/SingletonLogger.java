package util;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import model.domain.LivelloInformazione;

public class SingletonLogger{

    private static SingletonLogger INSTANCE;

    private final Logger logger;
    private SingletonLogger() {
        logger = LogManager.getLogger(SingletonLogger.class);
    }

    public static SingletonLogger getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonLogger();
        }
        return INSTANCE;
    }

    public void sendInformazione(LivelloInformazione livello, String message){
        switch (livello){
            case info:
                logger.info(message);
                break;
            
            case error:
                logger.error(message);
                break;
            
            case debug:
                logger.debug(message);
                break;
            
            case fatal:
                logger.fatal(message);
                break;

            case trace:
                logger.trace(message);
                break;

            default:
                logger.error("Questo non dovrebbe succedere : OX250001");
                break;

        }
    }
}