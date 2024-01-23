package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import server.com.server.exception.PersonalException;

public class BaseController {

    BufferedReader in;
    PrintWriter out;

    ControllerInfoSulThread info;

    LoginController login;

    Credential cred;

    public BaseController(BufferedReader input, PrintWriter output, ControllerInfoSulThread infoest){
        this.in = input;
        this.out = output;
        this.info = infoest;
    }

    public void execute() throws IOException, PersonalException {
        System.out.println("Sei dentro il basecontroller");
        String inputLine;
        if (this.info.isRunning()) {
            System.out.println("Stai aspettando un input");
            
            out.println("Cosa si desidera fare?");
            inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("LOGIN")) {
                    login = new LoginController(in, out, info);
                    try{cred = login.execute();
                    }catch (PersonalException e){
                        out.println("STOPTHAT");
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
            
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Server " + this.info.getThreadId()  + ": " + inputLine);
                if (!this.info.isRunning()) {
                    out.println("STOPTHAT");
                    System.out.println("Server " + this.info.getThreadId()  + ": Non rispondo poichè sto chiudendo la connessione");
                    break;
                }    
                out.println(inputLine);
            }
            System.out.println("Sto uscendo da vbuhfvedbgesdzbvuyedfvbguedfbvd ");
        }
        System.out.println("erfbyrefyuberbyuerbyreeyr recdxasiugvxrdwesci ueai vuebieurf ivgue ei iugveeugv iqa uiea bive bie");
    }
}
