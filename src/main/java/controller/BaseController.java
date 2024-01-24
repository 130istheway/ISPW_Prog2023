package controller;

import java.io.IOException;
import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;
import server.com.server.exception.PersonalException;

public class BaseController {

    ControllerInfoSulThread info;
    LoginController login;
    Credential cred;

    private static final String STOPTHAT = "STOPIT";


    private void controll(String message) throws PersonalException, IOException{
        if (message.equals("LOGIN")) {
            login = new LoginController(info);
            try{
                cred = login.execute();
            }catch (PersonalException e){
                if (e.getMessage().equals("Non rispondo che il server sta chiudendo")){
                    info.sendMessage(STOPTHAT);
                    throw new PersonalException("Sono uscito dal login perchè il server ha chiuso");
                }
                info.sendMessage(STOPTHAT);
                throw new PersonalException ("Ha sbagliato ad autenticarsi");
            }
        } else if (message.equals("EXIT")) {
            info.sendMessage(STOPTHAT);
            if (cred!=null) throw new PersonalException("Esco, " + cred.getUsername() + " si era autenticato ma è voluto uscire");
            throw new PersonalException("NON si è voluto autenticare");
        } else if (message.equals("WRITEBACK")){
            info.sendMessage("WRITEBACK MODE");
            this.info.sendlog( LivelloInformazione.trace ,"WRITEBACK MODE");
            String inputLine;
            while ((inputLine = info.getMessage()) != null) {
                this.info.sendlog( LivelloInformazione.info ,"Server " + this.info.getThreadId()  + ": " + inputLine);
                if (inputLine.equals("STOPWRITEBACK")){
                    info.sendMessage("WRITEBACKENDED");
                    break;
                }
                if (!this.info.isRunning()) {
                    info.sendMessage(STOPTHAT);
                    this.info.sendlog( LivelloInformazione.debug ,"Server " + this.info.getThreadId()  + ": Non rispondo poichè sto chiudendo la connessione");
                    break;
                }
                info.sendMessage(inputLine);
            }
        }
    }


    public BaseController(ControllerInfoSulThread infoest){
        this.info = infoest;
    }


    public void execute() throws IOException, PersonalException {
        String inputLine;
        if (this.info.isRunning()) {
            info.sendMessage("LOGIN");
            while ((inputLine = info.getMessage()) != null) {
                    controll(inputLine);
                }
            }

            if ((cred.getRole()).ordinal()>3) {
                throw new PersonalException("Non si è autenticato");
            }
    }
}
