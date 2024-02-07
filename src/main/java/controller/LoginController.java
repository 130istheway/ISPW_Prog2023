package controller;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dao.ConnectionFactory;
import model.dao.exception.DAOException;
import model.dao.login.DAOLogin;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.Role;

import server.com.server.exception.PersonalException;
import util.MessageToCommand;
import util.PayloadToCredential;

public class LoginController {
    
    Logger logger = LogManager.getLogger(LoginController.class);

    ControllerInfoSulThread info;
    
    public LoginController(ControllerInfoSulThread infoest){
        this.info = infoest;
    }

    public Credential execute() throws IOException, PersonalException {
        MessageToCommand messageToCommand = new MessageToCommand();
        Credential cred = null;
        String inputLine;
        final String ACCETTATA = "ACCETTATA";
        final String RIFIUTATA = "Rifiutato";
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
                cred = new Credential(null,null, Role.NONE);
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
                messageToCommand.setCommand(ACCETTATA+cred.getRole().ordinal());
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
                logger.trace("{0} -> {1} Role: {2}",ACCETTATA,cred.getUsername(),(cred.getRole()).ordinal());
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
        logger.trace("{0} -> {1} Role: {2}",RIFIUTATA,cred.getUsername(),(cred.getRole()).ordinal());
        return cred;
    }
}
