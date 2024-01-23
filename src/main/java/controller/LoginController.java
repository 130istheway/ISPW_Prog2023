package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.Role;
import server.com.server.exception.PersonalException;

public class LoginController {
    BufferedReader in;
    PrintWriter out;

    ControllerInfoSulThread info;
    
    public LoginController(BufferedReader input, PrintWriter output, ControllerInfoSulThread infoest){
        this.in = input;
        this.out = output;
        this.info = infoest;
    }

    public Credential execute() throws IOException, PersonalException {
        String inputLine;
        int retryCount = 0;

        out.println("Autenticarsi: ");
        while ((inputLine = in.readLine()) != null) {
            if (!this.info.isRunning()) {
                out.println("STOPTHAT");
                System.out.println("Server " + this.info.getThreadId()  + ": Non rispondo poichè sto chiudendo la connessione");
                Credential cred = new Credential(null,null, Role.NONE);
                System.out.println("Accettata " + (cred.getRole()).ordinal());
                return cred;
            }
            if (inputLine.equals("user:gigi,pass:gigi")) {
                Credential cred = new Credential("gigi","gigi", Role.NEGOZIO);
                out.println("accettata" + (cred.getRole()).ordinal());
                System.out.println("Accettata " + (cred.getRole()).ordinal());
                return cred;
            }if (inputLine.equals("user:lollo,pass:lollo")) {
                Credential cred = new Credential("lollo","lollo", Role.UTENTE);
                out.println("accettata" + (cred.getRole()).ordinal());
                System.out.println("Accettata " + (cred.getRole()).ordinal());
                return cred;
            }
            out.println("Riprova");
            retryCount++;
            if (retryCount > 4) {
                throw new PersonalException("Ha sbagliato ad autenticarsi");
            }
        }
        Credential cred = new Credential(null,null, Role.NONE);
        System.out.println("Accettata " + (cred.getRole()).ordinal());
        return cred;
    }
}
