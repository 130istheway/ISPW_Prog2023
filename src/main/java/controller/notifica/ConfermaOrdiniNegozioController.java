package controller.notifica;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import boundary.BoundaryBasicController;
import boundary.BoundaryBasicResponse;
import boundary.BoundaryErrori;
import boundary.BoundaryGestioneNotifica;
import boundary.BoundaryUserControl;
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
public class ConfermaOrdiniNegozioController {
    
    Logger logger = LogManager.getLogger(ConfermaOrdiniNegozioController.class);

    boolean cambiaAttivita = false;
    List<Integer> listaID;
    List<String> listaDati;


    /**
     * Metodo di ricezzione della socket e si occupa di instanziare alcuni inmporttanti oggetti di UTIL
     * @param credentials
     * @param info
     */
    public void confermaOrdiniController(Credential credentials, ControllerInfoSulThread info){
        String inputLine;

        setcache(credentials);

        if (listaID == null) {
            info.sendMessage(BoundaryBasicResponse.RETURNNULL);
            return;
        }
        
        info.sendMessage(BoundaryBasicResponse.RETURNOK);
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
            case BoundaryUserControl.RETURNVISUALIZZAARTICOLOCOMMAND:
                String messaggio;
                try {
                    StringBuilder appoggio = new StringBuilder();
                    int number = Integer.parseInt(messageToCommand.getPayload());
                    listaDati = ordini.execute(listaID.get(number));
                    for (String string : listaDati) {
                        appoggio = appoggio.append(string + "_");
                    }
                    messaggio = BoundaryGestioneNotifica.returnVisualizzaArticoloCommand(appoggio.toString());

                } catch (IndexOutOfBoundsException | DAOException e) {
                    messaggio = BoundaryGestioneNotifica.RETURNVISUALIZZAARTICOLOCOMMAND;
                }
                info.sendMessage(messaggio);

                break;
            

            case BoundaryGestioneNotifica.RETURNCONFERMANOTIFICACOMMAND:
                DAOConfermaOrdineDalID confermaOrdine = new DAOConfermaOrdineDalID();
                String[] cose = messageToCommand.getPayload().split("\\|");
                String messaggio2;
                try {
                    boolean noti = confermaOrdine.execute(listaID.get(Integer.parseInt(cose[0])), cose[1]);
                    if (noti) {
                        messaggio2 = BoundaryBasicResponse.RETURNSI;
                    }else{
                        messaggio2 = BoundaryBasicResponse.RETURNNO;
                    }
                    info.sendMessage(messaggio2);
                } catch (DAOException e) {
                    logger.error("Problema rilevato nelle DAO per confermare le notifiche");
                    info.sendMessage(BoundaryErrori.ERROR);
                } catch (NumberFormatException e1) {
                    logger.error("Problema rilevato nella conversione della stringa ad intero");
                    info.sendMessage(BoundaryErrori.ERRORCONVERSIONE);
                }
                setcache(credentials);
                break;

            case BoundaryBasicController.RETURNEXITCOMMAND:
                cambiaAttivita = true;
            break;
        
            default:
                info.sendMessage(BoundaryBasicResponse.RETURNNONRICONOSCIUTO);
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

