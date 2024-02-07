package controller.user;

import model.dao.DAORecoverArticoliDB;
import model.dao.DAORecuperaIdArticolo;
import model.dao.exception.DAOException;
import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import util.ConvertiStringToArticolo;
import util.MessageToCommand;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import carrello.Carrello;
import carrello.CarrelloCache;

public class AggiungiUserController {
    
    Logger logger = LogManager.getLogger(AggiungiUserController.class);

    CarrelloCache cache;
    Carrello appoggio;
    
    public AggiungiUserController(String negozio){
        appoggio = new Carrello();

        DAORecuperaIdArticolo daoRecuperaIdArticoli = new DAORecuperaIdArticolo();
        DAORecoverArticoliDB daoRecoverArticoliDB = new DAORecoverArticoliDB();
        try {
            List<Integer> numero = daoRecuperaIdArticoli.execute(Integer.valueOf(negozio));

            for (Integer integer : numero) {
                String yatta = daoRecoverArticoliDB.execute(integer);
                List<Object> yatta3 = ConvertiStringToArticolo.convertToArticoloList(yatta);
                appoggio.aggiungiProdotto(yatta3);
            }
        } catch (DAOException e){
            logger.error("Non è stato possibile aggiungere");
        }
        cache = appoggio;
    }
    
    boolean cambiaAttivita = false;

    public void aggiungiUserController(Credential credentials, ControllerInfoSulThread info, Carrello carrello){
        MessageToCommand messageToCommand = new MessageToCommand();
        messageToCommand.setCommand("OK");
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
        String inputLine;
        try {
        if (info.isRunning()) {
            while (((inputLine = info.getMessage()) != null) && (!cambiaAttivita)) {
                    controll(inputLine, credentials, info, carrello);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    


    private void controll(String inputLine, Credential credentials, ControllerInfoSulThread info, Carrello carrello){
        MessageToCommand messageToCommand = new MessageToCommand();
        messageToCommand.fromMessage(inputLine);
        String command = messageToCommand.getCommand();
        String number = messageToCommand.getPayload();

        switch (command) {
            case "VISUALIZZAART":
                String articolo = cache.ritornaArticoloString(Integer.parseInt(number));
                MessageToCommand message = new MessageToCommand();
                if (articolo == null) {
                    message.setCommand("NO");
                    message.setPayload("Elemento non esistente");
                    info.sendMessage(message.toMessage());
                    return;
                }
                message.setCommand("SI");
                message.setPayload(articolo);
                info.sendMessage(message.toMessage());
                break;


            case "AGGIUNGILISTA":

                int numberPezzi;

                String[] parti = number.split("\\|");
                int numberId = Integer.parseInt(parti[0].trim());
                if (parti.length > 1) {
                    numberPezzi = Integer.parseInt(parti[1].trim());
                }else{
                    numberPezzi = 1;
                }

                boolean aggiunto = carrello.aggiungi(cache.ritornaArticolo(numberId), numberPezzi);

                if (!aggiunto) {
                    logger.info(String.format("non è stato possibile inserire l'articolo : %s : %s",number,credentials.getUsername()));
                    messageToCommand.setCommand("NO");
                    messageToCommand.setPayload(null);
                    info.sendMessage(messageToCommand.toMessage());
                    break;
                }
                messageToCommand.setCommand("SI");
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
                break;


            case "EXIT":
                cambiaAttivita = true;
                break;
    

            default:
                cambiaAttivita = true;
                break;
        }
    }
}
