package controller;

import java.io.IOException;
import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
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
                System.out.println("Ha funzionato dfsvbadsiuvfbhearfds vuiha<ivgbesadrf ib");
            }catch (PersonalException e){
                if (e.getMessage().equals("Non rispondo che il server sta chiudendo")){
                    info.sendmessage(STOPTHAT);
                    throw new PersonalException("Sono uscito dal login perchè il server ha chiuso");
                }
                info.sendmessage(STOPTHAT);
                throw new PersonalException ("Ha sbagliato ad autenticarsi");
            }
        } else if (message.equals("EXIT")) {
            info.sendmessage(STOPTHAT);
            throw new PersonalException("NON si è voluto autenticare");
        } else if (message.equals("WRITEBACK")){
            info.sendmessage("WRITEBACK MODE");
            String inputLine;
            while ((inputLine = info.getMessage()) != null) {
                System.out.println("Server " + this.info.getThreadId()  + ": " + inputLine);
                if (inputLine.equals("STOPWRITEBACK")){
                    info.sendmessage("WRITEBACKENDED");
                    break;
                }
                if (!this.info.isRunning()) {
                    info.sendmessage(STOPTHAT);
                    System.out.println("Server " + this.info.getThreadId()  + ": Non rispondo poichè sto chiudendo la connessione");
                    break;
                }
                info.sendmessage(inputLine);
            }
        }
    }


    public BaseController(ControllerInfoSulThread infoest){
        this.info = infoest;
    }


    public void execute() throws IOException, PersonalException {
        String inputLine;
        if (this.info.isRunning()) {
            info.sendmessage("LOGIN");
            while ((inputLine = info.getMessage()) != null) {
                    controll(inputLine);
                }
            }

            if ((cred.getRole()).ordinal()>3) {
                throw new PersonalException("Non si è autenticato");
            }
    }
}
