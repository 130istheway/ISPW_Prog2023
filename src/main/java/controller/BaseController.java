package controller;

import java.io.IOException;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;

import server.com.server.exception.PersonalException;

import util.MessageToCommand;

import carrello.Carrello;
import controller.negozio.NegozioBDController;
import controller.user.AggiungiUserController;
import controller.user.VisualizzaUserController;

public class BaseController {

    ControllerInfoSulThread info;
    LoginController login;
    Credential cred;
    Carrello carrellino = new Carrello();

    VisualizzaUserController userVisualizza = new VisualizzaUserController();
    NegozioBDController negozioInserisci = new NegozioBDController();

    private static final String STOPTHAT = "STOPIT";
    private static final String NAUTORIZZATO = "NON AUTORIZATO";

    private void controll(String message) throws PersonalException, IOException{

        MessageToCommand messageToCommand = new MessageToCommand();
        messageToCommand.fromMessage(message);

        switch (messageToCommand.getCommand()) {

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
                info.sendlog(LivelloInformazione.TRACE, "Entering Writeback");
                rispondereACioCheMandaComeUnPappagallo(info);
            break;


            case "VISUALIZZA":
                if (cred!=null && (cred.getRole().ordinal()<4)){
                    info.sendlog(LivelloInformazione.TRACE, "Entering Visualizza per l'utente : " + cred.getUsername());
                    userVisualizza.viusalizzaController(cred, info, carrellino);
                    break;
                };
                info.sendlog(LivelloInformazione.TRACE, "Ha provato a VISUALIZARE senza essere Loggato");
                info.sendMessage(NAUTORIZZATO);
            break;


            case "AGGIUNGILISTA":
            if (cred!=null && (cred.getRole().ordinal()<4)){
                AggiungiUserController aggiungi = new AggiungiUserController(cred.getUsername());
                info.sendlog(LivelloInformazione.TRACE, "Entering AggiungiLista per l'utente : " + cred.getUsername());
                aggiungi.aggiungiUserController(cred, info, carrellino);
                break;
            };
            info.sendlog(LivelloInformazione.TRACE, "Ha provato ad AGGIUNGERE alla Lista senza essere Loggato");
            info.sendMessage(NAUTORIZZATO);
            break;

            
            case "AGGIUNGIARTICOLODB":
            if (cred!=null && (cred.getRole().ordinal()<3)){
                cred.getUsername();
                info.sendlog(LivelloInformazione.TRACE, "Entering AggiungiArticoloDB per l'utente : " + cred.getUsername());
                negozioInserisci.aggiungiDBController(cred, info, messageToCommand.getPayload());
                info.sendMessage("Not Yet Implemented");
                break;
            };
            info.sendlog(LivelloInformazione.TRACE, "Ha provato ad AGGIUNGERE un Articolo al DB senza essere Loggato");
            info.sendMessage(NAUTORIZZATO);
            break;


            case "RIMUOVIARTICOLODB":
            if (cred!=null && (cred.getRole().ordinal()<3)){
                cred.getUsername();
                info.sendlog(LivelloInformazione.TRACE, "Entering RimuoviArticoloDB per l'utente : " + cred.getUsername());
                negozioInserisci.aggiungiDBController(cred, info, messageToCommand.getPayload());
                info.sendMessage("Not Yet Implemented");
                break;
            };
            info.sendlog(LivelloInformazione.TRACE, "Ha provato ad RIMUOVERE un Articolo al DB senza essere Loggato");
            info.sendMessage(NAUTORIZZATO);
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
