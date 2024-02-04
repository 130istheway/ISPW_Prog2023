package controller.negozio;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;

import util.MessageToCommand;

import model.dao.exception.DAOException;
import model.dao.negozio.*;

public class NegozioBDController {

    MessageToCommand messageToCommand = new MessageToCommand();
    
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
                messageToCommand.setCommand("NO");
                messageToCommand.setPayload("articolo non aggiunto, troppi Articoli");
                info.sendMessage(messageToCommand.toMessage());
                return false;
            }


            DAOAggiungiNegozio daoAggiungiNegozio = new DAOAggiungiNegozio();

            boolean result = daoAggiungiNegozio.execute(string, negozioId);
            if (!result) {
                messageToCommand.setCommand("NO");
                messageToCommand.setPayload("articolo non aggiunto");
                info.sendMessage(messageToCommand.toMessage());
                return false;
            }
            return true;

        } catch (DAOException e) {
            messageToCommand.setCommand("NO");
            messageToCommand.setPayload("articolo non aggiunto");
            info.sendMessage(messageToCommand.toMessage());
            info.sendlog(LivelloInformazione.ERROR, e.getMessage());
            return false;
        }
    }

    

    public boolean rimuoviDBArticolo(Credential credential, ControllerInfoSulThread info, Integer number){
        int idNegozio;

        try {
            DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
            if ((idNegozio = daoIdNegozio.execute(credential.getUsername())) == 0) {
                messageToCommand.setCommand("NO");
                messageToCommand.setPayload("ID negozio non recuperato");
    
                info.sendMessage(messageToCommand.toMessage());
                return false;
            }

            DAOEliminaArticolo daoEliminaArticolo = new DAOEliminaArticolo();

            boolean result = daoEliminaArticolo.execute(number, idNegozio);
            if (!result) {
                messageToCommand.setCommand("NO");
                messageToCommand.setPayload("articolo non eliminato");
                
                info.sendMessage(messageToCommand.toMessage());
                return false;
            }
            return true;
        
        } catch (DAOException e) {
            messageToCommand.setCommand("NO");
            messageToCommand.setPayload("articolo non eliminato per errore");

            info.sendMessage(messageToCommand.toMessage());
            info.sendlog(LivelloInformazione.ERROR, e.getMessage());
            return false;
        }
    }
}