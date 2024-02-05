package controller;

import java.io.IOException;
import java.util.List;

import model.dao.exception.DAOException;
import model.dao.negozio.DAOIdNegozio;
import model.dao.notifica.DAORecuperaIdOrdini;
import model.dao.user.DAOUser;
import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;

import server.com.server.exception.PersonalException;

import util.MessageToCommand;

import carrello.Carrello;

import controller.negozio.*;
import controller.user.*;
import javafx.application.Platform;
import controller.notifica.NotificaNegozioController;

public class BaseController {

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


            case "NOTIFICA":
                notifica();
            break;

            case "RESETNEGOZIO":
                resetNegozio();
            break;

            case "RECUPERA":
                recuperaInfo();
            break;


            default:
                messageToCommand.setCommand("NOTAVALIDCOMAND");
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
            break;
        }
    }

    private void recuperaInfo() {
        if (cred!=null && (cred.getRole().ordinal()<3)){
            if (cred.getRole().ordinal()<2) {
                List<Integer> listaID;
                DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
                DAORecuperaIdOrdini idOrdini = new DAORecuperaIdOrdini();
                try {
                    int id = daoIdNegozio.execute(cred.getUsername());
                    listaID = idOrdini.execute(id);
                    if (listaID == null){
                        messageToCommand.setCommand("NO");
                        messageToCommand.setPayload(null);
                        info.sendMessage(messageToCommand.toMessage());
                        return;
                    }
                    messageToCommand.setCommand("SI");
                    messageToCommand.setPayload(null);
                    info.sendMessage(messageToCommand.toMessage());
                    return;
                } catch (DAOException e) {
                    info.sendlog(LivelloInformazione.ERROR, "Problema rilevato nelle DAO per visualizare le notifiche " + e.getMessage());
                }    
            } else{
                //implementare le notifiche per l'utente
            }
        }
        throw new UnsupportedOperationException("Unimplemented method 'recuperaInfo'");
    }

    private void ordiniConfermati() {
        if (cred!=null && (cred.getRole().ordinal()<3)){
            info.sendlog(LivelloInformazione.TRACE, "Recupero degli ORDINI del carrello");
            DAOUser daoUser = new DAOUser();
            DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
            int id = 0;
            try {
                id = daoIdNegozio.execute(cred.getUsername());
            } catch (DAOException e) {
                info.sendlog(LivelloInformazione.ERROR, "OrdiniConfermati" + e.getMessage());
                messageToCommand.setCommand("NO");
                messageToCommand.setPayload("Problema con l'id contatta l'amministratore con messaggio 0x321123654");
                info.sendMessage(messageToCommand.toMessage());
                return;
            }
            String ordini = null;
            try {
                ordini = daoUser.execute(id);
            } catch (DAOException e) {
                info.sendlog(LivelloInformazione.ERROR, "Errore nel recupero degli ORDINI da confermare" + e.getMessage());
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
        info.sendlog(LivelloInformazione.TRACE, "Carrello resettato");
    }


    private void tryLogin() throws PersonalException {
        info.sendlog(LivelloInformazione.TRACE, "Entering LOGIN");
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
                    info.sendlog(LivelloInformazione.TRACE, "Login failed");
                }catch (Exception e){
                    if (e instanceof PersonalException) {
                        throw e;
                    }
                    messageToCommand.setCommand(STOPTHAT);
                    messageToCommand.setPayload(null);
                    info.sendMessage(messageToCommand.toMessage());
                    info.sendlog(LivelloInformazione.TRACE, "Login failed");
                }
    }


    private void exit() throws PersonalException{
        messageToCommand.setCommand(STOPTHAT);
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
        info.sendlog(LivelloInformazione.TRACE, "Entering EXIT");
        if (cred!=null) throw new PersonalException("Esco, " + cred.getUsername() + " si era autenticato ma è voluto uscire");
        throw new PersonalException("NON si è voluto autenticare");
    }


    private void visualizza(){
        if (cred!=null && (cred.getRole().ordinal()<3)){
            info.sendlog(LivelloInformazione.TRACE, "Entering Visualizza per l'utente : " + cred.getUsername());
            userVisualizza.viusalizzaController(cred, info, carrellino);
            info.sendlog(LivelloInformazione.TRACE, "Exiting visualizza per l'utente : " + cred.getUsername());
            return;
        }
        info.sendlog(LivelloInformazione.TRACE, "Ha provato a VISUALIZARE senza essere Loggato");
        messageToCommand.setCommand(NAUTORIZZATO);
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
    }


    private void rispondereACioCheMandaComeUnPappagallo(ControllerInfoSulThread info) throws IOException {
        info.sendlog(LivelloInformazione.TRACE, "Entering Writeback");
        messageToCommand.setCommand("WRITEBACK MODE");
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
        this.info.sendlog( LivelloInformazione.TRACE ,"WRITEBACK MODE");
        String inputLine;
        while ((inputLine = info.getMessage())!= null){
            this.info.sendlog( LivelloInformazione.INFO ,"Server " + this.info.getThreadId()  + ": " + inputLine);
            if (inputLine.equals("STOPWRITEBACK")){
                messageToCommand.setCommand("WRITEBACKENDED");
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
                info.sendlog(LivelloInformazione.FATAL, "Exiting WRITEBACK : xxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                return;
            }
            if (!this.info.isRunning()) {
                messageToCommand.setCommand(STOPTHAT);
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
                this.info.sendlog( LivelloInformazione.DEBUG ,"Server " + this.info.getThreadId()  + ": Non rispondo poichè sto chiudendo la connessione");
                return;
            }
            messageToCommand.setCommand(inputLine);
            messageToCommand.setPayload(null);
            info.sendMessage(messageToCommand.toMessage());
        }
    }


    private void aggiungiLista(String negozio){
        if (cred!=null && (cred.getRole().ordinal()<3)){
            AggiungiUserController aggiungi = new AggiungiUserController(negozio);
            info.sendlog(LivelloInformazione.TRACE, "Entering AggiungiLista per l'utente : " + cred.getUsername());
            aggiungi.aggiungiUserController(cred, info, carrellino);
            info.sendlog(LivelloInformazione.TRACE, "Exiting AggiungiLista per l'utente : " + cred.getUsername());
            return;
        }
        info.sendlog(LivelloInformazione.TRACE, "Ha provato ad AGGIUNGERE alla Lista senza essere Loggato");
        messageToCommand.setCommand(NAUTORIZZATO);
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
    }


    private void confermaLista(String negozio){
        if (cred!=null && (cred.getRole().ordinal()<3)){
            ConfermaListaController conferma = new ConfermaListaController(Integer.parseInt(negozio));
            info.sendlog(LivelloInformazione.TRACE, "Entering Confermalista per l'utente : " + cred.getUsername());
            conferma.confermaLista(cred, info, carrellino);
            info.sendlog(LivelloInformazione.TRACE, "Exiting Confermalista per l'utente : " + cred.getUsername());
            return;
        }
        info.sendlog(LivelloInformazione.TRACE, "Ha provato ad CONFERMALISTA senza essere Loggato");
        messageToCommand.setCommand(NAUTORIZZATO);
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
    }


    private void aggiungiArticoloDB(){
        if (cred!=null && (cred.getRole().ordinal()<2)){
            cred.getUsername();
            info.sendlog(LivelloInformazione.TRACE, "Entering AggiungiArticoloDB per il negozio : " + cred.getUsername());
            boolean aggiunto = negozioInserisci.aggiungiDBArticolo(cred, info, messageToCommand.getPayload());
            if (aggiunto) {
                messageToCommand.setCommand("SI");
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
            }
            info.sendlog(LivelloInformazione.TRACE, "Exiting AggiungiArticoloDB per il negozio : " + cred.getUsername());
            return;
        }
        info.sendlog(LivelloInformazione.TRACE, "Ha provato ad AGGIUNGERE un Articolo al DB senza essere Loggato");
        messageToCommand.setCommand(NAUTORIZZATO);
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
    }

    private void visualizzaDaDB(){
        if (cred!=null && (cred.getRole().ordinal()<2)){
            cred.getUsername();
            info.sendlog(LivelloInformazione.TRACE, "Entering visualizza Da DB per il negozio : " + cred.getUsername());
            negozioVisualizza.viusalizzaNegozioController(cred, info);
            info.sendlog(LivelloInformazione.TRACE, "Exiting visualizza Da DB per il negozio : " + cred.getUsername());
            return;
        }
        info.sendlog(LivelloInformazione.TRACE, "ha provato a Articolo dal DB senza essere Loggato");
        messageToCommand.setCommand(NAUTORIZZATO);
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
    }

    
    private void notifica() {
        if (cred!=null && (cred.getRole().ordinal()<2)){
            cred.getUsername();
            info.sendlog(LivelloInformazione.TRACE, "Entering NOTIFICA per il negozio : " + cred.getUsername());
            NotificaNegozioController notifica = new NotificaNegozioController();
            notifica.notificaController(cred, info);
            info.sendlog(LivelloInformazione.TRACE, "Exiting NOTIFICA per il negozio : " + cred.getUsername());
            return;
        }
        info.sendlog(LivelloInformazione.TRACE, "Ha provato a vedere notifica senza essere Loggato");
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
