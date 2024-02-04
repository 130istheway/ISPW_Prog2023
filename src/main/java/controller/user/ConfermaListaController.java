package controller.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import carrello.Carrello;
import model.dao.exception.DAOException;
import model.dao.negozio.DAOIdNegozio;
import model.dao.notifica.DAOInserisciOrdine;
import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;
import util.MessageToCommand;

public class ConfermaListaController {

    int negozio;
    boolean cambiaAttivita = false;
    List<Object> params = new ArrayList<>();

    public ConfermaListaController(int negozio){
        this.negozio = negozio;
    }

    public void confermaLista(Credential credentials, ControllerInfoSulThread info, Carrello carrello){
        MessageToCommand messageToCommand = new MessageToCommand();

        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        date = new java.sql.Date(calendar.getTimeInMillis());

        if (carrello.getLista() == null || carrello.getLista() == ""){
            messageToCommand.setCommand("NO");
            messageToCommand.setPayload(null);
            info.sendMessage(messageToCommand.toMessage());
            return;
        }

        DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
        int username = 0;
        try {
            username = daoIdNegozio.execute(credentials.getUsername());
        } catch (DAOException e) {
            messageToCommand.setCommand("NO");
            messageToCommand.setPayload(null);
            info.sendMessage(messageToCommand.toMessage());
            return;
        }

        try {
            DAOInserisciOrdine inserisciOrdine = new DAOInserisciOrdine();
            params.add(negozio);
            params.add(username);
            params.add(carrello.getLista());
            params.add(date);

            boolean conferma = inserisciOrdine.execute(negozio, username, carrello.getLista(), date);

            if (conferma){ 
                messageToCommand.setCommand("OK");
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
                return;
            }
            messageToCommand.setCommand("NO");
            messageToCommand.setPayload(null);
            info.sendMessage(messageToCommand.toMessage());
            return;
        } catch ( DAOException e ) {
            info.sendlog(LivelloInformazione.ERROR, e.getMessage());
        }
    }

}
