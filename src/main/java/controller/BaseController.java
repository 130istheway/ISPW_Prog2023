package controller;

import boundary.BoundaryBasicController;
import boundary.BoundaryBasicResponse;
import boundary.BoundaryLogin;
import boundary.BoundaryNegozioControl;
import boundary.BoundaryUserControl;
import carrello.Carrello;
import controller.negozio.NegozioBDController;
import controller.negozio.VisualizzaNegozioController;
import controller.notifica.ConfermaOrdiniNegozioController;
import controller.user.AggiungiUserController;
import controller.user.ConfermaListaController;
import controller.user.VisualizzaUserController;
import model.dao.exception.DAOException;
import model.dao.negozio.DAOIdNegozio;
import model.dao.notifica.DAORecuperaIdOrdini;
import model.dao.user.DAOUser;
import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.com.server.exception.PersonalException;
import util.MessageToCommand;

import java.io.IOException;
import java.util.List;

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

    private void controll(String message) throws PersonalException, IOException {

        messageToCommand.fromMessage(message);

        switch (messageToCommand.getCommand()) {

            case BoundaryLogin.RETURNLOGIN:
                tryLogin();
                break;

            case BoundaryBasicController.RETURNEXITCOMMAND:
                exit();
            break;

            case BoundaryBasicController.RETURNWRITEBACKCOMMAND:
                rispondereACioCheMandaComeUnPappagallo(info);
            break;


            case BoundaryBasicController.RETURNVISUALIZZACOMAND:
                visualizza();
            break;


            case BoundaryBasicController.RETURNINSERISCIARTICOLOCOMMAND:
                aggiungiLista(messageToCommand.getPayload());
            break;


            case BoundaryBasicController.RETURNCONFERMAORDINECOMMAND:
                confermaLista(messageToCommand.getPayload());
            break;


            case BoundaryUserControl.RETURNORDINICONFERMATICOMMADN:
                ordiniConfermati();
            break;

            
            case BoundaryNegozioControl.RETURNAGGIUNGIARTICOLODBCOMMAND:
                aggiungiArticoloDB();
            break;

            case BoundaryBasicController.RETURNVISUALIZZAARTICOLIDADBCOMMAND:
                visualizzaDaDB();
            break;


            case BoundaryBasicController.RETURNCONFERMAORDINICOMMAND:
                confermaOrdini();
            break;
            
            case BoundaryBasicController.RETURNCECHORDINICOMMAND:
                recuperaNOrdini();
            break;


            case BoundaryBasicController.RETURNRESETNEGOZIOCOMMAND:
                resetNegozio();
            break;

            default:
                info.sendMessage(BoundaryBasicResponse.RETURNNOTAVALIDCOMAND);
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
                        info.sendMessage(BoundaryBasicResponse.RETURNNO);
                        return;
                    }
                    info.sendMessage(BoundaryBasicResponse.returnSi(listaID.size()));
                    return;
                } catch (DAOException e) {
                    logger.error("Problema rilevato nelle DAO per recuperare l'id del negozio %s", e.getMessage());
                }    
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
            int id;
            try {
                id = daoIdNegozio.execute(cred.getUsername());
            } catch (DAOException e) {
                logger.error("OrdiniConfermati %s",e.getMessage());
                info.sendMessage(BoundaryBasicResponse.returnNo("Problema con l'id contatta l'amministratore con messaggio 0x321123654"));
                return;
            }
            String ordini;
            try {
                ordini = daoUser.execute(id);
            } catch (DAOException e) {
                logger.error("Errore nel recupero degli ORDINI da confermare %s",e.getMessage());
                info.sendMessage(BoundaryBasicResponse.returnNo("Problema con l'id contatta l'amministratore con messaggio 0x321123662"));
                return;
            }
            info.sendMessage(BoundaryBasicResponse.RETURNPREGO);
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
                        info.sendMessage(BoundaryBasicResponse.RETURNSTOPIT);
                        throw new PersonalException("Sono uscito dal login perchè il server ha chiuso");
                    }
                    info.sendMessage(BoundaryBasicResponse.RETURNSTOPIT);
                    throw new PersonalException ("Ha sbagliato ad autenticarsi");
                }catch (IOException e1){
                    info.sendMessage(BoundaryBasicResponse.RETURNSTOPIT);
                    logger.trace("Login failed");
                }catch (Exception e){
                    if (e instanceof PersonalException) {
                        throw e;
                    }
                    info.sendMessage(BoundaryBasicResponse.RETURNSTOPIT);
                    logger.trace("Login failed");
                }
    }

    /**
     * Metodo che si occupa dell'uscita dal controller
     * @throws PersonalException
     */
    private void exit() throws PersonalException{
        info.sendMessage(BoundaryBasicResponse.RETURNSTOPIT);
        logger.trace("Exit");
        if (cred!=null) throw new PersonalException("Esco, " + cred.getUsername() + " è autenticato ed voluto uscire");
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
        info.sendMessage(BoundaryBasicResponse.RETURNNAUTORIZZATO);
    }

    /**
     * Metodo ceh risponde a tutti senza controllare se siano loggati
     * @param info
     * @throws IOException
     */
    private void rispondereACioCheMandaComeUnPappagallo(ControllerInfoSulThread info) throws IOException {
        logger.trace("Entering Writeback");
        info.sendMessage(BoundaryBasicController.RETURNWRITEBACKMODECOMMAND);
        logger.trace("WRITEBACK MODE");
        String inputLine;
        while ((inputLine = info.getMessage())!= null){
            logger.trace("Server %d : %s",this.info.getThreadId(),inputLine);
            if (inputLine.equals(BoundaryBasicController.RETURNSTOPWRITEBACKCOMMAND)){
                info.sendMessage(BoundaryBasicController.RETURNSTOPWRITEBACKCOMMAND);
                logger.trace("Exiting WRITEBACK : xxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                return;
            }
            if (!this.info.isRunning()) {
                info.sendMessage(BoundaryBasicResponse.RETURNSTOPIT);
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
        info.sendMessage(BoundaryBasicResponse.RETURNNAUTORIZZATO);
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
        info.sendMessage(BoundaryBasicResponse.RETURNNAUTORIZZATO);
    }


    /**
     * Metodo per aggiungere nel DB
     */
    private void aggiungiArticoloDB(){
        if (cred!=null && (cred.getRole().ordinal()<2)){
            logger.trace("Entering AggiungiArticoloDB per il negozio : %s", cred.getUsername());
            boolean aggiunto = negozioInserisci.aggiungiDBArticolo(cred, info, messageToCommand.getPayload());
            if (aggiunto) {
                info.sendMessage(BoundaryBasicResponse.RETURNSI);
            }
            logger.trace("Exiting AggiungiArticoloDB per il negozio : %s", cred.getUsername());
            return;
        }
        logger.trace("Ha provato ad AGGIUNGERE un Articolo al DB senza essere Loggato");
        info.sendMessage(BoundaryBasicResponse.RETURNNAUTORIZZATO);
    }


    /**
     * Metdo per vedere dal DB
     */
    private void visualizzaDaDB(){
        if (cred!=null && (cred.getRole().ordinal()<2)){
            logger.trace("Entering visualizza Da DB per il negozio : %s", cred.getUsername());
            negozioVisualizza.viusalizzaNegozioController(cred, info);
            logger.trace("Exiting visualizza Da DB per il negozio : %s", cred.getUsername());
            return;
        }
        logger.trace("ha provato a Articolo dal DB senza essere Loggato");
        info.sendMessage(BoundaryBasicResponse.RETURNNAUTORIZZATO);
    }

    
    /**
     * Metodo per gli ordini del negozio
     */
    private void confermaOrdini() {
        if (cred!=null && (cred.getRole().ordinal()<2)){
            logger.trace("Entering Conferma Ordini per il negozio : %s", cred.getUsername());
            ConfermaOrdiniNegozioController confermaOrdini = new ConfermaOrdiniNegozioController();
            confermaOrdini.confermaOrdiniController(cred, info);
            logger.trace("Exiting Conferma Ordini per il negozio : %s", cred.getUsername());
            return;
        }
        logger.trace("Ha provato a vedere notifica senza essere Loggato");
        info.sendMessage(BoundaryBasicResponse.RETURNNAUTORIZZATO);
    }


    public BaseController(ControllerInfoSulThread infoest){
        this.info = infoest;
    }

    public void execute() throws IOException, PersonalException {
        String inputLine;
        if (this.info.isRunning()) {
            info.sendMessage(BoundaryBasicResponse.RETURNDECIDI);
            while ((inputLine = info.getMessage()) != null) {
                    controll(inputLine);
                }
            }
            if ((cred.getRole()).ordinal()>3) {
                throw new PersonalException("Non si è autenticato");
            }
    }
}
