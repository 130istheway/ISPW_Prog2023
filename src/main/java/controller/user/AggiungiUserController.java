package controller.user;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;
import util.MessageToCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import carrello.Carrello;
import carrello.CarrelloCache;
import carrello.articoli.Articoli;

public class AggiungiUserController {

    CarrelloCache cache;
    
    public AggiungiUserController(String username){
        List<Articoli> list = new ArrayList<>();
        cache = new CarrelloCache( list);
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
