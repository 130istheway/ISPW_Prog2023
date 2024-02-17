package controller;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dao.exception.DAOException;
import model.dao.negozio.DAOIdNegozio;
import model.dao.notifica.DAORecuperaIdOrdini;
import model.dao.user.DAOUser;
import model.domain.ControllerInfoSulThread;
import model.domain.Credential;

import server.com.server.exception.PersonalException;

import util.MessageToCommand;

import carrello.Carrello;

import controller.negozio.*;
import controller.user.*;
import controller.notifica.ConfermaOrdiniNegozioController;

/**
 * Classe per la gestione principale del menu, un controller principale che smista richieste agli altri controller in caso non sia in grado di gestirle d'asolo, ciò avviene quando sono commandi innestati
 * @author Stefano
 */
public class BaseController {
    
    Logger logger = LogManager.getLogger(BaseController.class);

    ControllerInfoSulThread info;
    LoginController login;
    Credential cred;

    Carrello carrellino = new Carrello();

    MessageToCommand messageToCommand = new MessageToCommand();

    VisualizzaUserController userVisualizza = new VisualizzaUserController();
    NegozioBDController negozioInserisci = new NegozioBDController();
    VisualizzaNegozioController negozioVisualizza = new VisualizzaNegozioController();

    private static final String STOPTHAT = "STOPIT";
    private static final String NAUTORIZZATO = "NON AUTORIZATO";

    private void controll(String message) throws PersonalException, IOException {

        messageToCommand.fromMessage(message);

        switch (messageToCommand.getCommand()) {

            case "LOGIN":
                tryLogin();
                break;

            case "EXIT":
                exit();
            break;

            case "WRITEBACK":
                rispondereACioCheMandaComeUnPappagallo(info);
            break;


            case "VISUALIZZA":
                visualizza();
            break;


            case "AGGIUNGILISTA":
                aggiungiLista(messageToCommand.getPayload());
            break;


            case "CONFERMALISTA":
                confermaLista(messageToCommand.getPayload());
            break;


            case "ORDINI":
                ordiniConfermati();
            break;

            
            case "AGGIUNGIARTICOLODB":
                aggiungiArticoloDB();
            break;

            case "VISUALIZZAARTICOLODB":
                visualizzaDaDB();
            break;


            case "CONFERMAORDINI":
                confermaOrdini();
            break;
            
            case "RECUPERANORDINI":
                recuperaNOrdini();
            break;


            case "RESETNEGOZIO":
                resetNegozio();
            break;

            default:
                messageToCommand.setCommand("NOTAVALIDCOMAND");
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
            break;
        }
    }

    /**
     * Recupera il numero di ordini che devono essere accettati o rifiutati asseconda di quello che viene deciso
     */
    private void recuperaNOrdini() {
        if (cred!=null && (cred.getRole().ordinal()<3)){
            if (cred.getRole().ordinal()<2) {
                List<Integer> listaID;
                DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
                DAORecuperaIdOrdini idOrdini = new DAORecuperaIdOrdini();
                try {
                    int id = daoIdNegozio.execute(cred.getUsername());
                    listaID = idOrdini.execute(id);
                    if (listaID.isEmpty()){
                        messageToCommand.setCommand("NO");
                        messageToCommand.setPayload(null);
                        info.sendMessage(messageToCommand.toMessage());
                        return;
                    }
                    messageToCommand.setCommand("SI");
                    messageToCommand.setPayload(String.valueOf(listaID.size()));
                    info.sendMessage(messageToCommand.toMessage());
                    return;
                } catch (DAOException e) {
                    logger.error("Problema rilevato nelle DAO per recuperare l'id del negozio %s", e.getMessage());
                }    
            } else{
                //implementare le notifiche per l'utente
            }
        }
        throw new UnsupportedOperationException("Unimplemented method 'recuperaInfo'");
    }

    /**
     * Gestore per conoscere lo stato degli ordini dell'utente
     */
    private void ordiniConfermati() {
        if (cred!=null && (cred.getRole().ordinal()<3)){
            logger.trace("Recupero degli ORDINI del carrello");
            DAOUser daoUser = new DAOUser();
            DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
            int id = 0;
            try {
                id = daoIdNegozio.execute(cred.getUsername());
            } catch (DAOException e) {
                logger.error("OrdiniConfermati %s",e.getMessage());
                messageToCommand.setCommand("NO");
                messageToCommand.setPayload("Problema con l'id contatta l'amministratore con messaggio 0x321123654");
                info.sendMessage(messageToCommand.toMessage());
                return;
            }
            String ordini = null;
            try {
                ordini = daoUser.execute(id);
            } catch (DAOException e) {
                logger.error("Errore nel recupero degli ORDINI da confermare %s",e.getMessage());
                messageToCommand.setCommand("NO");
                messageToCommand.setPayload("Problema con l'id contatta l'amministratore con messaggio 0x321123662");
                info.sendMessage(messageToCommand.toMessage());
                return;
            }
            messageToCommand.setCommand("PREGO");
            messageToCommand.setPayload(ordini);
            info.sendMessage(messageToCommand.toMessage());
        }
    }

    private void resetNegozio(){
        carrellino = new Carrello();
        logger.trace("Carrello resettato");
    }

    /**
     * Metodo che si occupa del login
     * @throws PersonalException
     */
    private void tryLogin() throws PersonalException {
        logger.trace("Entering LOGIN");
                login = new LoginController(info);
                try{
                    cred = login.execute();
                }catch (PersonalException e){
                    if (e.getMessage().equals("Non rispondo che il server sta chiudendo")){
                        messageToCommand.setCommand(STOPTHAT);
                        messageToCommand.setPayload(null);
                        info.sendMessage(messageToCommand.toMessage());
                        throw new PersonalException("Sono uscito dal login perchè il server ha chiuso");
                    }
                    messageToCommand.setCommand(STOPTHAT);
                    messageToCommand.setPayload(null);
                    info.sendMessage(messageToCommand.toMessage());
                    throw new PersonalException ("Ha sbagliato ad autenticarsi");
                }catch (IOException e1){
                    messageToCommand.setCommand(STOPTHAT);
                    messageToCommand.setPayload(null);
                    info.sendMessage(messageToCommand.toMessage());
                    logger.trace("Login failed");
                }catch (Exception e){
                    if (e instanceof PersonalException) {
                        throw e;
                    }
                    messageToCommand.setCommand(STOPTHAT);
                    messageToCommand.setPayload(null);
                    info.sendMessage(messageToCommand.toMessage());
                    logger.trace("Login failed");
                }
    }

    /**
     * Metodo che si occupa dell'uscita dal controller
     * @throws PersonalException
     */
    private void exit() throws PersonalException{
        messageToCommand.setCommand(STOPTHAT);
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
        logger.trace("Exit");
        if (cred!=null) throw new PersonalException("Esco, " + cred.getUsername() + " si era autenticato ma è voluto uscire");
        throw new PersonalException("NON si è voluto autenticare");
    }

    /**
     * Metodo per la visualizzazione da parte dell'utente della propria lista
     */
    private void visualizza(){
        if (cred!=null && (cred.getRole().ordinal()<3)){
            logger.trace("Entering Visualizza per l'utente : %s",cred.getUsername());
            userVisualizza.viusalizzaController(cred, info, carrellino);
            logger.trace("Exiting visualizza per l'utente : %s", cred.getUsername());
            return;
        }
        logger.trace("Ha provato a VISUALIZARE senza essere Loggato");
        messageToCommand.setCommand(NAUTORIZZATO);
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
    }

    /**
     * Metodo ceh risponde a tutti senza controllare se siano loggati
     * @param info
     * @throws IOException
     */
    private void rispondereACioCheMandaComeUnPappagallo(ControllerInfoSulThread info) throws IOException {
        logger.trace("Entering Writeback");
        messageToCommand.setCommand("WRITEBACK MODE");
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
        logger.trace("WRITEBACK MODE");
        String inputLine;
        while ((inputLine = info.getMessage())!= null){
            logger.trace("Server %d : %s",this.info.getThreadId(),inputLine);
            if (inputLine.equals("STOPWRITEBACK")){
                messageToCommand.setCommand("WRITEBACKENDED");
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
                logger.trace("Exiting WRITEBACK : xxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                return;
            }
            if (!this.info.isRunning()) {
                messageToCommand.setCommand(STOPTHAT);
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
                logger.debug("Server %d : Non rispondo poichè sto chiudendo la connessione", this.info.getThreadId());
                return;
            }
            messageToCommand.setCommand(inputLine);
            messageToCommand.setPayload(null);
            info.sendMessage(messageToCommand.toMessage());
        }
    }

    /**
     * Metodo per inserire nella lista personale
     * @param negozio
     */
    private void aggiungiLista(String negozio){
        if (cred!=null && (cred.getRole().ordinal()<3)){
            AggiungiUserController aggiungi = new AggiungiUserController(negozio);
            logger.trace("Entering AggiungiLista per l'utente : %s", cred.getUsername());
            aggiungi.aggiungiUserController(cred, info, carrellino);
            logger.trace("Exiting AggiungiLista per l'utente : %s", cred.getUsername());
            return;
        }
        logger.trace("Ha provato ad AGGIUNGERE alla Lista senza essere Loggato");
        messageToCommand.setCommand(NAUTORIZZATO);
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
    }


    /**
     * Metodo che fa confermare la lista
     * @param negozio
     */
    private void confermaLista(String negozio){
        if (cred!=null && (cred.getRole().ordinal()<3)){
            ConfermaListaController conferma = new ConfermaListaController(Integer.parseInt(negozio));
            logger.trace("Entering Confermalista per l'utente : %s", cred.getUsername());
            conferma.confermaLista(cred, info, carrellino);
            logger.trace("Exiting Confermalista per l'utente : %S", cred.getUsername());
            return;
        }
        logger.trace("Ha provato ad CONFERMALISTA senza essere Loggato");
        messageToCommand.setCommand(NAUTORIZZATO);
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
    }


    /**
     * Metodo per aggiungere nel DB
     */
    private void aggiungiArticoloDB(){
        if (cred!=null && (cred.getRole().ordinal()<2)){
            cred.getUsername();
            logger.trace("Entering AggiungiArticoloDB per il negozio : %s", cred.getUsername());
            boolean aggiunto = negozioInserisci.aggiungiDBArticolo(cred, info, messageToCommand.getPayload());
            if (aggiunto) {
                messageToCommand.setCommand("SI");
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
            }
            logger.trace("Exiting AggiungiArticoloDB per il negozio : %s", cred.getUsername());
            return;
        }
        logger.trace("Ha provato ad AGGIUNGERE un Articolo al DB senza essere Loggato");
        messageToCommand.setCommand(NAUTORIZZATO);
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
    }


    /**
     * Metdo per vedere dal DB
     */
    private void visualizzaDaDB(){
        if (cred!=null && (cred.getRole().ordinal()<2)){
            cred.getUsername();
            logger.trace("Entering visualizza Da DB per il negozio : %s", cred.getUsername());
            negozioVisualizza.viusalizzaNegozioController(cred, info);
            logger.trace("Exiting visualizza Da DB per il negozio : %s", cred.getUsername());
            return;
        }
        logger.trace("ha provato a Articolo dal DB senza essere Loggato");
        messageToCommand.setCommand(NAUTORIZZATO);
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
    }

    
    /**
     * Metodo per gli ordini del negozio
     */
    private void confermaOrdini() {
        if (cred!=null && (cred.getRole().ordinal()<2)){
            cred.getUsername();
            logger.trace("Entering Conferma Ordini per il negozio : %s", cred.getUsername());
            ConfermaOrdiniNegozioController confermaOrdini = new ConfermaOrdiniNegozioController();
            confermaOrdini.confermaOrdiniController(cred, info);
            logger.trace("Exiting Conferma Ordini per il negozio : %s", cred.getUsername());
            return;
        }
        logger.trace("Ha provato a vedere notifica senza essere Loggato");
        messageToCommand.setCommand(NAUTORIZZATO);
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
    }


    public BaseController(ControllerInfoSulThread infoest){
        this.info = infoest;
    }

    public void execute() throws IOException, PersonalException {
        String inputLine;
        if (this.info.isRunning()) {
            messageToCommand.setCommand("DECIDI");
            messageToCommand.setPayload(null);
            info.sendMessage(messageToCommand.toMessage());
            while ((inputLine = info.getMessage()) != null) {
                    controll(inputLine);
                }
            }
            if ((cred.getRole()).ordinal()>3) {
                throw new PersonalException("Non si è autenticato");
            }
    }
}
