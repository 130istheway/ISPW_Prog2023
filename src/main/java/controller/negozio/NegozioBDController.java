package controller.negozio;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import util.MessageToCommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dao.exception.DAOException;
import model.dao.negozio.*;
/**
 * Classe per il Controller di Negozio
 * @author Stefano
*/
public class NegozioBDController {
    
    Logger logger = LogManager.getLogger(NegozioBDController.class);

    MessageToCommand messageToCommand = new MessageToCommand();
    
    /**
     * Metodo che destisce l'aggiunte di un articolo al DB
     * @param credential modello delle credenzialo
     * @param info 
     * @param string Stringa da agigungere al DB
     * @return
     */
    public boolean aggiungiDBArticolo(Credential credential, ControllerInfoSulThread info, String string){

        int negozioId;

        try {
            DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
            if ((negozioId = daoIdNegozio.execute(credential.getUsername())) == 0) {
                messageToCommand.setCommand("NO");
                messageToCommand.setPayload("ID negozio non recuperato");
                info.sendMessage(messageToCommand.toMessage());
                return false;
            }
            
            /* Ottenere il numero di articoli che ha il negozio */
            DAOCountArticoli daoCountArticoli = new DAOCountArticoli();

            int number = daoCountArticoli.execute(negozioId);

            /*Vedere se sono all'interno del range 0 - 9999 */
            if (number > 9999){
                sendMessaggioNO(info,"articolo non aggiunto, troppi Articoli");
                return false;
            }


            DAOAggiungiNegozio daoAggiungiNegozio = new DAOAggiungiNegozio();

            boolean result = daoAggiungiNegozio.execute(string, negozioId);

            return controlloResult(result, info);

        } catch (DAOException e) {
            sendMessaggioNO(info,"articolo non aggiunto");
            logger.error(e.getMessage());
            return false;
        }
    }


    /**
     * Un metodo privato per mandare messaggi in modo più semplice
     * @param info
     * @param stringa
     */
    private void sendMessaggioNO(ControllerInfoSulThread info,String stringa){
        messageToCommand.setCommand("NO");
        messageToCommand.setPayload(stringa);
        info.sendMessage(messageToCommand.toMessage());
    }

    
    /**
     * Il metodo che si occupa della cancellazione di un articolo dal DB
     * @param credential
     * @param info
     * @param number
     * @return
     */
    public boolean rimuoviDBArticolo(Credential credential, ControllerInfoSulThread info, Integer number){
        int idNegozio;

        try {
            DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
            if ((idNegozio = daoIdNegozio.execute(credential.getUsername())) == 0) {
                sendMessaggioNO(info,"ID negozio non recuperato");
                return false;
            }

            DAOEliminaArticolo daoEliminaArticolo = new DAOEliminaArticolo();

            boolean result = daoEliminaArticolo.execute(number, idNegozio);

            return controlloResult(result, info);
        
        } catch (DAOException e) {
            sendMessaggioNO(info,"articolo non eliminato per errore");
            logger.error(e.getMessage());
            return false;
        }
    }


    private boolean controlloResult(boolean result,ControllerInfoSulThread info){
        if (!result) {
            sendMessaggioNO(info,"articolo non eliminato");
            return false;
        }
        return true;
    }
}