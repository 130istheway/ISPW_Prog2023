package controller;

import java.io.IOException;

import model.dao.login.DAOLogin;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;
import model.domain.Role;

import server.com.server.exception.PersonalException;

import util.PayloadToCredential;

public class LoginController {

    ControllerInfoSulThread info;
    
    public LoginController(ControllerInfoSulThread infoest){
        this.info = infoest;
    }

    public Credential execute() throws IOException, PersonalException {
        Credential cred = null;
        String inputLine;
        final String accettata = "Accettata";
        final String rifiutata = "Rifiutato";
        int retryCount = 0;

        if (!info.isRunning()) {
            info.sendMessage("STOPIT Non rispondo che il server sta chiudendo");
            throw new PersonalException("Non rispondo che il server sta chiudendo");
        }
        info.sendMessage("Autenticarsi: ");
        while ((inputLine = info.getMessage()) != null) {
            if (!this.info.isRunning()) {
                info.sendMessage("STOPTHAT");
                this.info.sendlog( LivelloInformazione.DEBUG ,"Server " + this.info.getThreadId()  + ": Non rispondo poichè sto chiudendo la connessione");
                cred = new Credential(null,null, Role.NONE);
                this.info.sendlog( LivelloInformazione.DEBUG ,"STOPTHAT " + (cred.getRole()).ordinal());
                return cred;
            }/* qui cambiare per autenticarsi attraverso una dao che restituisce il ROLE da una tabella del BD */
            DAOLogin dao = new DAOLogin();
            PayloadToCredential p = new PayloadToCredential();
            boolean autenticato;
            try {
                cred = dao.execute(p.getCredentials(inputLine));
                autenticato = true;
            } catch (Exception e) {
                autenticato = false;
            }
            if (autenticato) {
            info.sendMessage(accettata);
            this.info.sendlog( LivelloInformazione.TRACE ,accettata + " " + " " + cred.getUsername() + " " +(cred.getRole()).ordinal());
            return cred;       
            }
            info.sendMessage("Riprova");
            retryCount++;
            if (retryCount > 3) {
                info.sendMessage(rifiutata);
                throw new PersonalException("Ha sbagliato ad autenticarsi");
            }
        }
        cred = new Credential(null,null, Role.NONE);
        this.info.sendlog( LivelloInformazione.TRACE ,rifiutata + (cred.getRole()).ordinal());
        return cred;
    }
}
