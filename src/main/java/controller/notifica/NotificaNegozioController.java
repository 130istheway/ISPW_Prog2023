package controller.notifica;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dao.exception.DAOException;
import model.dao.negozio.DAOIdNegozio;
import model.dao.notifica.DAOConfermaOrdineDalID;
import model.dao.notifica.DAORecuperaIdOrdini;
import model.dao.notifica.DAORecuperaOrdiniDaID;
import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import util.MessageToCommand;


/**
 * Il controler che permette ad un negozio di vedere gli ordini che ha ricevuto e che permette di accettarli o rifiutarli
 * @author Stefano
 */
public class NotificaNegozioController {
    
    Logger logger = LogManager.getLogger(NotificaNegozioController.class);

    boolean cambiaAttivita = false;
    List<Integer> listaID;
    List<String> listaDati;


    /**
     * Metodo di ricezzione della socket e si occupa di instanziare alcuni inmporttanti oggetti di UTIL
     * @param credentials
     * @param info
     */
    public void notificaController(Credential credentials, ControllerInfoSulThread info){
        MessageToCommand messageToCommand = new MessageToCommand();
        String inputLine;

        setcache(credentials);

        if (listaID == null) {
            messageToCommand.setCommand("NULL");
            messageToCommand.setPayload(null);
            info.sendMessage(messageToCommand.toMessage());
            return;
        }
        
        messageToCommand.setCommand("OK");
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
        try {
        if (info.isRunning()) {
            while (((inputLine = info.getMessage()) != null) && (!cambiaAttivita)) {
                    controll(inputLine, credentials, info);
                }
            }
        } catch (IOException e) {
           logger.error(e.getMessage());
        }
    }


    /**
     * La vera e propria logica applicativa di questa classe che si occupa di visualizare una per una le ordinazioni al negozio e rispettivamente permette di confermarle o rifiutarle attraverso il comando "CONFERMANOTIFICA"
     * @param inputLine
     * @param credentials
     * @param info
     */
    private void controll(String inputLine, Credential credentials, ControllerInfoSulThread info){
        DAORecuperaOrdiniDaID ordini = new DAORecuperaOrdiniDaID();
        MessageToCommand messageToCommand = new MessageToCommand();
        messageToCommand.fromMessage(inputLine);
        String command = messageToCommand.getCommand();


        switch (command) {
            case "VISUALIZZAART":
                try {
                    StringBuilder appoggio = new StringBuilder();
                    int number = Integer.parseInt(messageToCommand.getPayload());
                    listaDati = ordini.execute(listaID.get(number));
                    for (String string : listaDati) {
                        appoggio = appoggio.append(string + "_");
                    }
                    messageToCommand.setCommand("SINOTI");
                    messageToCommand.setPayload(appoggio.toString());
                } catch (IndexOutOfBoundsException | DAOException e) {
                    messageToCommand.setCommand("NO");
                    messageToCommand.setPayload("Elemento non esistente");
                }
                info.sendMessage(messageToCommand.toMessage());

                break;
                

            case "CONFERMANOTIFICA":
                DAOConfermaOrdineDalID confermaOrdine = new DAOConfermaOrdineDalID();
                String[] cose = messageToCommand.getPayload().split("\\|");
                try {
                    boolean noti = confermaOrdine.execute(listaID.get(Integer.parseInt(cose[0])), cose[1]);
                    if (noti) {
                        messageToCommand.setCommand("SI");
                        messageToCommand.setPayload(null);
                    }else{
                        messageToCommand.setCommand("NO");
                        messageToCommand.setPayload(null);
                    }
                    info.sendMessage(messageToCommand.toMessage());
                } catch (DAOException e) {
                    logger.error("Problema rilevato nelle DAO per confermare le notifiche");
                    messageToCommand.setCommand("ERROR");
                    messageToCommand.setPayload(null);
                    info.sendMessage(messageToCommand.toMessage());
                } catch (NumberFormatException e1) {
                    logger.error("Problema rilevato nella conversione della stringa ad intero");
                    messageToCommand.setCommand("ERRORConversione");
                    messageToCommand.setPayload(null);
                    info.sendMessage(messageToCommand.toMessage());
                }
                setcache(credentials);
                break;

            case "EXIT":
                cambiaAttivita = true;
            break;
        
            default:
                messageToCommand.setCommand("NONRICONOSCIUTO");
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
                cambiaAttivita = true;
                break;
        }
    }


    /**
     * metodo per settare la cache
     * @param credentials
     */
    private void setcache(Credential credentials){

        DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
        DAORecuperaIdOrdini idOrdini = new DAORecuperaIdOrdini();
        try {
            int id = daoIdNegozio.execute(credentials.getUsername());
            listaID = idOrdini.execute(id);
            
        } catch (DAOException e) {
            logger.error("Problema rilevato nelle DAO per visualizare le notifiche %s", e.getMessage());
        }

    }
}

