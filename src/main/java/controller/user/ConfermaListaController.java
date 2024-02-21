package controller.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import boundary.BoundaryBasicResponse;
import carrello.Carrello;
import model.dao.exception.DAOException;
import model.dao.negozio.DAOIdNegozio;
import model.dao.notifica.DAOInserisciOrdine;
import model.domain.ControllerInfoSulThread;
import model.domain.Credential;

/**
 * Classe che si occupa della conferma dell'Ordinazione, inserendo la lista di id sotto forma di stringa nel DB per il Negozio corrispondente
 * @author Stefano
 */
public class ConfermaListaController {
    
    
    Logger logger = LogManager.getLogger(ConfermaListaController.class);

    int negozio;
    boolean cambiaAttivita = false;
    List<Object> params = new ArrayList<>();

    public ConfermaListaController(int negozio){
        this.negozio = negozio;
    }

    /**
     * Il metodo che si occupa di confermare la lista e di fare i dovuti controllli per l'inserimento all'interno del DB oltre a controllare che l'inserimento sia avvenuto
     * @param credentials
     * @param info
     * @param carrello
     */
    public void confermaLista(Credential credentials, ControllerInfoSulThread info, Carrello carrello){

        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        date = new java.sql.Date(calendar.getTimeInMillis());

        if (carrello.getLista() == null || Objects.equals(carrello.getLista(), "")){
            info.sendMessage(BoundaryBasicResponse.RETURNNO);
            return;
        }

        DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
        int username = 0;
        try {
            username = daoIdNegozio.execute(credentials.getUsername());
        } catch (DAOException e) {
            info.sendMessage(BoundaryBasicResponse.RETURNNO);
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
                info.sendMessage(BoundaryBasicResponse.RETURNOK);
                return;
            }
            info.sendMessage(BoundaryBasicResponse.RETURNNO);
        } catch ( DAOException e ) {
            logger.error(e.getMessage());
        }
    }

}
