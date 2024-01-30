package controller.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import carrello.Carrello;
import model.dao.exception.DAOException;
import model.dao.notifica.DAOInserisciOrdine;
import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;

public class ConfermaListaController {

    String negozio;
    boolean cambiaAttivita = false;
    List<Object> params = new ArrayList<>();

    public ConfermaListaController(String negozio){
        this.negozio = negozio;
    }

    public void confermaLista(Credential credentials, ControllerInfoSulThread info, Carrello carrello){

        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        date = new java.sql.Date(calendar.getTimeInMillis());


        try {
            DAOInserisciOrdine inserisciOrdine = new DAOInserisciOrdine();
            params.add(negozio);
            params.add(credentials.getUsername());
            params.add(carrello.getLista());
            params.add(date);

            boolean conferma = inserisciOrdine.execute(params);

            if (conferma){ 
                info.sendMessage("OK"); 
                return;
            }
            info.sendMessage("NO"); return;
        } catch ( DAOException e ) {
            info.sendlog(LivelloInformazione.ERROR, e.getMessage());
        }
    }

}
