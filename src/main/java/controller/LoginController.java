package controller;

import java.io.IOException;
import java.util.List;

import model.dao.ConnectionFactory;
import model.dao.exception.DAOException;
import model.dao.login.DAOLogin;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;
import model.domain.Role;

import server.com.server.exception.PersonalException;
import util.MessageToCommand;
import util.PayloadToCredential;

public class LoginController {

    ControllerInfoSulThread info;
    
    public LoginController(ControllerInfoSulThread infoest){
        this.info = infoest;
    }

    public Credential execute() throws IOException, PersonalException {
        MessageToCommand messageToCommand = new MessageToCommand();
        Credential cred = null;
        String inputLine;
        final String accettata = "Accettata";
        final String rifiutata = "Rifiutato";
        int retryCount = 0;

        if (!info.isRunning()) {
            messageToCommand.setCommand("STOPIT Non rispondo che il server sta chiudendo");
            messageToCommand.setPayload(null);
            info.sendMessage(messageToCommand.toMessage());
            throw new PersonalException("Non rispondo che il server sta chiudendo");
        }
        messageToCommand.setCommand("Autenticarsi: ");
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());

        while ((inputLine = info.getMessage()) != null) {
            if (!this.info.isRunning()) {
                messageToCommand.setCommand("STOPIT");
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
                this.info.sendlog( LivelloInformazione.DEBUG ,"Server " + this.info.getThreadId()  + ": Non rispondo poichè sto chiudendo la connessione");
                cred = new Credential(null,null, Role.NONE);
                this.info.sendlog( LivelloInformazione.DEBUG ,"STOPTHAT " + (cred.getRole()).ordinal());
                return cred;
            }
            DAOLogin dao = new DAOLogin();
            PayloadToCredential p = new PayloadToCredential();
            boolean autenticato;

            try {
                List<String> crede= p.getCredentials(inputLine);
                cred = dao.execute(crede.get(0), crede.get(1));
                if (cred.getRole().ordinal() < 3) {
                    ConnectionFactory.changeRole(cred);
                    autenticato = true;
                }else {
                    autenticato = false;
                }
            } catch (DAOException e) {
                autenticato = false;
            }
            if (autenticato) {        
                messageToCommand.setCommand(accettata+cred.getRole().ordinal());
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
                this.info.sendlog( LivelloInformazione.TRACE ,accettata + " " + " " + cred.getUsername() + " Role:" +(cred.getRole()).ordinal());
                return cred;       
            }
            if (retryCount > 4) {
                throw new PersonalException("Ha sbagliato ad autenticarsi");
            }
            messageToCommand.setCommand("Riprova");
            messageToCommand.setPayload(null);
            info.sendMessage(messageToCommand.toMessage());
            retryCount++;
            
        }
        cred = new Credential(null,null, Role.NONE);
        this.info.sendlog( LivelloInformazione.TRACE ,rifiutata + (cred.getRole()).ordinal());
        return cred;
    }
}
