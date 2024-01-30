package util;

import java.util.ArrayList;
import java.util.List;

import carrello.Carrello;
import model.dao.DAORecoverArticoliDB;
import model.dao.DAORecuperaIdArticolo;
import model.dao.exception.DAOException;
import model.dao.negozio.DAOCountArticoli;
import model.dao.negozio.DAOIdNegozio;

import server.com.server.exception.PersonalException;

public class RecuperaArticolo {

    private RecuperaArticolo(){
        throw new IllegalStateException("Utility class");
      }

    public static Carrello recuperaCarelloDaNegozio(String username) throws PersonalException{

        Carrello carrelloTemporaneo = new Carrello();
        List<Object> lista = new ArrayList<>();
        String dati;
        Integer idNegozio;

        DAOCountArticoli daoCountArticoli = new DAOCountArticoli();
        DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
        DAORecoverArticoliDB daoRecoverArticoliDB = new DAORecoverArticoliDB();
        DAORecuperaIdArticolo daoRecuperaIdArticolo = new DAORecuperaIdArticolo();

        try {
            idNegozio = daoIdNegozio.execute(username);
            Integer count = daoCountArticoli.execute(idNegozio);

            List<Integer> listaID = daoRecuperaIdArticolo.execute(username);


            for (int i = 0; i < count; i++) {
                dati = daoRecoverArticoliDB.execute(listaID);
                lista = ConvertiStringToArticolo.convertToArticoloList(dati);
                carrelloTemporaneo.aggiungiProdotto(lista);
            }

            return carrelloTemporaneo;
        } catch (DAOException e) {
            throw new PersonalException("Non è stato possibile recuperare il CacheCarrello");
        }
        
    }

}
    
