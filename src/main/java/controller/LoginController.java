package controller;

import java.io.IOException;
import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.Role;
import server.com.server.exception.PersonalException;

public class LoginController {

    ControllerInfoSulThread info;
    
    public LoginController(ControllerInfoSulThread infoest){
        this.info = infoest;
    }

    public Credential execute() throws IOException, PersonalException {
        String inputLine;
        int retryCount = 0;

        if (!info.isRunning()) {
            info.sendmessage("STOPIT Non rispondo che il server sta chiudendo");
            throw new PersonalException("Non rispondo che il server sta chiudendo");
        }
        info.sendmessage("Autenticarsi: ");
        while ((inputLine = info.getMessage()) != null) {
            if (!this.info.isRunning()) {
                info.sendmessage("STOPTHAT");
                System.out.println("Server " + this.info.getThreadId()  + ": Non rispondo poichè sto chiudendo la connessione");
                Credential cred = new Credential(null,null, Role.NONE);
                System.out.println("STOPTHAT " + (cred.getRole()).ordinal());
                return cred;
            }
            if (inputLine.equals("user:gigi,pass:gigi")) {
                Credential cred = new Credential("gigi","gigi", Role.NEGOZIO);
                info.sendmessage("accettata");
                System.out.println("Accettata " + (cred.getRole()).ordinal());
                return cred;
            }if (inputLine.equals("user:lollo,pass:lollo")) {
                Credential cred = new Credential("lollo","lollo", Role.UTENTE);
                info.sendmessage("accettata");
                System.out.println("Accettata " + (cred.getRole()).ordinal());
                return cred;
            }
            info.sendmessage("Riprova");
            retryCount++;
            if (retryCount > 4) {
                info.sendmessage("Rifiutrata ");
                throw new PersonalException("Ha sbagliato ad autenticarsi");
            }
        }
        Credential cred = new Credential(null,null, Role.NONE);
        System.out.println("Accettata " + (cred.getRole()).ordinal());
        return cred;
    }
}
