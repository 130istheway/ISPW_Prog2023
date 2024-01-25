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

        switch (message) {
            case "LOGIN":
                info.sendlog(LivelloInformazione.TRACE, "Entering LOGIN");
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
                break;

            case "EXIT":
                info.sendlog(LivelloInformazione.TRACE, "Entering EXIT");
                info.sendMessage(STOPTHAT);
                if (cred!=null) throw new PersonalException("Esco, " + cred.getUsername() + " si era autenticato ma è voluto uscire");
                throw new PersonalException("NON si è voluto autenticare");

            case "WRITEBACK":
                info.sendlog(LivelloInformazione.TRACE, "Entering WRITEBACK");
                rispondereACioCheMandaComeUnPappagallo(info);
                info.sendlog(LivelloInformazione.FATAL, "Sono arrivato qui : xxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            break;

            default:
                info.sendMessage("NOT A VALID COMAND");
            break;
        }
    }

    private void rispondereACioCheMandaComeUnPappagallo(ControllerInfoSulThread info) throws IOException {
        info.sendMessage("WRITEBACK MODE");
        this.info.sendlog( LivelloInformazione.TRACE ,"WRITEBACK MODE");
        String inputLine;
        while ((inputLine = info.getMessage())!= null){
            this.info.sendlog( LivelloInformazione.INFO ,"Server " + this.info.getThreadId()  + ": " + inputLine);
            if (inputLine.equals("STOPWRITEBACK")){
                info.sendMessage("WRITEBACKENDED");
                info.sendlog(LivelloInformazione.FATAL, "Exiting WRITEBACK : xxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                return;
            }
            if (!this.info.isRunning()) {
                info.sendMessage(STOPTHAT);
                this.info.sendlog( LivelloInformazione.DEBUG ,"Server " + this.info.getThreadId()  + ": Non rispondo poichè sto chiudendo la connessione");
                return;
            }
            info.sendMessage(inputLine);
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
