package controller.negozio;

import java.util.ArrayList;
import java.util.List;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;

import util.MessageToCommand;

import model.dao.exception.DAOException;
import model.dao.negozio.*;

public class NegozioBDController {

    MessageToCommand message = new MessageToCommand();
    
    public boolean aggiungiDBArticolo(Credential credential, ControllerInfoSulThread info, String string){

        Integer negozioId;

        List<Object> input = new ArrayList<>();

        try {
            DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
            if ((negozioId = daoIdNegozio.execute(credential.getUsername())) == 0) {
                message.setCommand("NO");
                message.setPayload("ID negozio non recuperato");
    
                info.sendMessage(message.toMessage());
                return false;
            }
            
            /* Ottenere il numero di articoli che ha il negozio */
            DAOCountArticoli daoCountArticoli = new DAOCountArticoli();
            Integer number = daoCountArticoli.execute(negozioId);
            

            /*Vedere se sono all'interno del range 0 - 9999 */
            if (number > 9999){
                message.setCommand("NO");
                message.setPayload("articolo non aggiunto, troppi Articoli");

                info.sendMessage(message.toMessage());
                return false;
            }


            DAOAggiungiNegozio daoAggiungiNegozio = new DAOAggiungiNegozio();

            input.add(string);
            input.add(negozioId);
            
            boolean result = daoAggiungiNegozio.execute(input);
            if (!result) {
                message.setCommand("NO");
                message.setPayload("articolo non aggiunto");
    
                info.sendMessage(message.toMessage());
                return false;
            }
            
            
            info.sendMessage("SI");
            return true;

        } catch (DAOException e) {
            message.setCommand("NO");
            message.setPayload("articolo non aggiunto");

            info.sendMessage(message.toMessage());
            info.sendlog(LivelloInformazione.ERROR, e.getMessage());
            return false;
        }
    }

    

    public boolean rimuoviDBArticolo(Credential credential, ControllerInfoSulThread info, Integer number){
        int idNegozio;

        try {
            DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
            if ((idNegozio = daoIdNegozio.execute(credential.getUsername())) == 0) {
                message.setCommand("NO");
                message.setPayload("ID negozio non recuperato");
    
                info.sendMessage(message.toMessage());
                return false;
            }

            DAOEliminaArticolo daoEliminaArticolo = new DAOEliminaArticolo();

            List<Object> list = new ArrayList<>();
            list.add(number);
            list.add(idNegozio);

            boolean result = daoEliminaArticolo.execute(list);
            if (!result) {
                message.setCommand("NO");
                message.setPayload("articolo non eliminato");
    
                info.sendMessage(message.toMessage());
                return false;
    
            }
            info.sendMessage("SI");
            return true;
        
        } catch (DAOException e) {
            message.setCommand("NO");
            message.setPayload("articolo non eliminato");

            info.sendMessage(message.toMessage());
            info.sendlog(LivelloInformazione.ERROR, e.getMessage());
            return false;
        }
    }
}