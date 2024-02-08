package controller.negozio;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.dao.DAORecoverArticoliDB;
import model.dao.DAORecuperaIdArticolo;
import model.dao.exception.DAOException;
import model.dao.negozio.DAOIdNegozio;
import util.ConvertiStringToArticolo;
import util.MessageToCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import carrello.Carrello;
import carrello.CarrelloCache;
/**
 * Il controller della visualizazzione del DB del Negozio
 * @author Stefano
 */
public class VisualizzaNegozioController {
    
    Logger logger = LogManager.getLogger(VisualizzaNegozioController.class);
    
    List<Integer> numero = new ArrayList<>();

    NegozioBDController negozioElimina = new NegozioBDController();
    boolean cambiaAttivita = false;
    CarrelloCache cache;
    Carrello appoggio;


    /**
     * Visualizazzione dal DB da parte del Negozio
     * Il Metodo che si occupa di ricevere i messaggi dalla socket e distribuirli dove sono necessa, inoltre permette anche di inizializare la cache {un istanza di CarreloloCache che permette una reattività migliore ed un utilizzo del DB inferiore poichè fa la cache della lista dei possibili elementi gia dal DB}
     * Per la cancellazione basta prendere l'id del Articolo che si sta visionando e eliminarlo dal DB
     * @param credentials
     * @param info
     */
    public void viusalizzaNegozioController(Credential credentials, ControllerInfoSulThread info){appoggio = new Carrello();

        setcache(credentials);

        MessageToCommand messageToCommand = new MessageToCommand();
        messageToCommand.setCommand("OK");
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
        String inputLine;
        try {
        if (info.isRunning()) {
            cambiaAttivita = false;
            while (((inputLine = info.getMessage()) != null) && (!cambiaAttivita)) {
                    controll(inputLine, credentials, info, cache);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * La parte di controllo vera e propri, parte della logica applicativa
     * @param inputLine
     * @param credentials
     * @param info
     * @param carrello
     */
    private void controll(String inputLine, Credential credentials, ControllerInfoSulThread info, CarrelloCache carrello){
        MessageToCommand messageToCommand = new MessageToCommand();
        messageToCommand.fromMessage(inputLine);
        String command = messageToCommand.getCommand();
        int number = Integer.parseInt(messageToCommand.getPayload());

        switch (command) {
            case "VISUALIZZAART":
                String articolo = carrello.ritornaArticoloString(number);
                if (articolo == null) {
                    messageToCommand.setCommand("NO");
                    messageToCommand.setPayload("Elemento non esistente");
                    info.sendMessage(messageToCommand.toMessage());
                    return;
                }
                messageToCommand.setCommand("SI");
                messageToCommand.setPayload(articolo);
                info.sendMessage(messageToCommand.toMessage());

                break;

            case "RIMUOVIART":
                boolean delete = negozioElimina.rimuoviDBArticolo(credentials, info, numero.get(number));
                    if (delete) {
                    messageToCommand.setCommand("SI");
                    messageToCommand.setPayload(null);
                    info.sendMessage(messageToCommand.toMessage());
                    setcache(credentials);
                }
                break;

            case "EXIT":
                cambiaAttivita = true;
            break;
        
            default:
                cambiaAttivita = true;
                break;
        }
    }
    
    /**
     * Metodo per settare la cache, se è gia settata ne l risetta
     * @param credentials
     */
    private void setcache(Credential credentials){

        appoggio = new Carrello();
        
        DAORecuperaIdArticolo daoRecuperaIdArticoli = new DAORecuperaIdArticolo();
        DAORecoverArticoliDB daoRecoverArticoliDB = new DAORecoverArticoliDB();
        DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
        try {

            int id = daoIdNegozio.execute(credentials.getUsername());
            numero = daoRecuperaIdArticoli.execute(id);

            for (Integer integer : numero) {
                String yatta = daoRecoverArticoliDB.execute(integer);
                List<Object> yatta3 = ConvertiStringToArticolo.convertToArticoloList(yatta);
                appoggio.aggiungiProdotto(yatta3);
            }
        } catch (DAOException e){
            logger.error("Errore nel recuperare il carrello per la cache");
        }
        cache = appoggio;

    }



}