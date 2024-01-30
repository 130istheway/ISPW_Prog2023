package controller.user;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;

import util.MessageToCommand;

import java.io.IOException;
import carrello.Carrello;
import carrello.CarrelloCache;

public class AggiungiUserController {

    CarrelloCache cache;
    
    public AggiungiUserController(String username, ControllerInfoSulThread info){
        //aggiungere la DAO per recuperare la lista da inserire nel carrello, si recupera tramite lo username
        cache = new CarrelloCache();
    }
    
    boolean cambiaAttivita = false;

    public void aggiungiUserController(Credential credentials, ControllerInfoSulThread info, Carrello carrello){
        info.sendMessage("OK");
        String inputLine;
        try {
        if (info.isRunning()) {
            while (((inputLine = info.getMessage()) != null) && (!cambiaAttivita)) {
                    controll(inputLine, credentials, info, carrello);
                }
            }
        } catch (IOException e) {
            info.sendlog(LivelloInformazione.ERROR, e.getMessage());
        }
    }
    


    private void controll(String inputLine, Credential credentials, ControllerInfoSulThread info, Carrello carrello){
        MessageToCommand messageToCommand = new MessageToCommand();
        messageToCommand.fromMessage(inputLine);
        String command = messageToCommand.getCommand();
        String number = messageToCommand.getPayload();

        switch (command) {
            case "VISUALIZZAART":
                cache.ritornaArticolo(info, Integer.parseInt(number));
                break;

            case "AGGIUNGILISTA":

                int numberPezzi;

                String[] parti = number.split("\\|");
                int numberId = Integer.parseInt(parti[0].trim());
                if (parti.length > 1) {
                    numberPezzi = Integer.parseInt(parti[0].trim());
                }else{
                    numberPezzi = 1;
                }

                boolean aggiunto = carrello.aggiungi(cache.ritornaArticolo(numberId), numberPezzi);

                if (!aggiunto) {
                    info.sendlog(LivelloInformazione.INFO, "non è stato possibile inserire l'articolo :" + number + " " +credentials.getUsername());
                    messageToCommand.setCommand("NO");
                    messageToCommand.setPayload(null);
                    info.sendMessage(messageToCommand.toString());
                    break;
                }
                messageToCommand.setCommand("SI");
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toString());
                break;
        
            default:
                cambiaAttivita = true;
                break;
        }
    }
}
