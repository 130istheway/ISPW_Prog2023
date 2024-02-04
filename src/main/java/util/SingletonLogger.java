package util;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import model.domain.LivelloInformazione;

public class SingletonLogger{

    private static SingletonLogger istance;

    private final Logger logger;

    private SingletonLogger() {
        logger = LogManager.getLogger(SingletonLogger.class);
    }

    public static SingletonLogger getInstance() {
        if (istance == null) {
            istance = new SingletonLogger();
        }
        return istance;
    }

    public void sendInformazione(LivelloInformazione livello, String message){
        switch (livello){
            case INFO:
                logger.info(message);
                break;
            
            case ERROR:
                logger.error(message);
                break;
            
            case DEBUG:
                logger.debug(message);
                break;
            
            case FATAL:
                logger.fatal(message);
                break;

            case TRACE:
                logger.trace(message);
                break;

            default:
                logger.error("Questo non dovrebbe succedere : OX250001");
                break;

        }
    }
}