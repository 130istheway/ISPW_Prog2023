package controller;

import java.io.IOException;
import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import server.com.server.exception.PersonalException;

public class BaseController {

    ControllerInfoSulThread info;

    LoginController login;

    Credential cred;

    public BaseController(ControllerInfoSulThread infoest){
        this.info = infoest;
    }

    public void execute() throws IOException, PersonalException {
        String inputLine;
        if (this.info.isRunning()) {
            
            info.sendmessage("Cosa si desidera fare?");
            inputLine = null;
            while ((inputLine = info.getMessage()) != null) {
                if (inputLine.equals("LOGIN")) {
                    login = new LoginController(info);
                    try{cred = login.execute();
                    }catch (PersonalException e){
                        if (e.getMessage().equals("Non rispondo che il server sta chiudendo")){
                            info.sendmessage("STOPTHAT");
                            throw new PersonalException("Sono uscito dal login perchè il server ha chiuso");
                        }
                        info.sendmessage("STOPTHAT");
                        throw new PersonalException ("Ha sbagliato ad autenticarsi");
                    }
                    break;
                } else if (inputLine.equals("EXIT")) {
                    throw new PersonalException("NON si è voluto autenticare");
                }
            }

            if ((cred.getRole()).ordinal()>3) {
                throw new PersonalException("Non si è autenticato");
            }


            
            while ((inputLine = info.getMessage()) != null) {
                System.out.println("Server " + this.info.getThreadId()  + ": " + inputLine);
                if (!this.info.isRunning()) {
                    info.sendmessage("STOPTHAT");
                    System.out.println("Server " + this.info.getThreadId()  + ": Non rispondo poichè sto chiudendo la connessione");
                    break;
                }    
                info.sendmessage(inputLine);
            }
            System.out.println("Sto uscendo da vbuhfvedbgesdzbvuyedfvbguedfbvd ");
        }
        System.out.println("erfbyrefyuberbyuerbyreeyr recdxasiugvxrdwesci ueai vuebieurf ivgue ei iugveeugv iqa uiea bive bie");
    }
}
