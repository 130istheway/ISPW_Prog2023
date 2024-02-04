package controller.notifica;

import java.io.IOException;
import java.util.List;

import model.dao.exception.DAOException;
import model.dao.negozio.DAOIdNegozio;
import model.dao.notifica.DAOConfermaOrdineDalID;
import model.dao.notifica.DAORecuperaIdOrdini;
import model.dao.notifica.DAORecuperaOrdiniDaID;
import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;
import util.MessageToCommand;

public class NotificaNegozioController {

    boolean cambiaAttivita = false;
    List<Integer> listaID;
    List<String> listaDati;
    String appoggio;

    public void notificaController(Credential credentials, ControllerInfoSulThread info){
        MessageToCommand messageToCommand = new MessageToCommand();
        String inputLine;

        setcache(credentials, info);

        if (listaID == null) {
            messageToCommand.setCommand("NULL");
            messageToCommand.setPayload(null);
            info.sendMessage(messageToCommand.toMessage());
            return;
        }
        
        messageToCommand.setCommand("OK");
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
        try {
        if (info.isRunning()) {
            while (((inputLine = info.getMessage()) != null) && (!cambiaAttivita)) {
                    controll(inputLine, credentials, info);
                }
            }
        } catch (IOException e) {
            info.sendlog(LivelloInformazione.ERROR, e.getMessage());
        }
    }



    private void controll(String inputLine, Credential credentials, ControllerInfoSulThread info){
        DAORecuperaOrdiniDaID ordini = new DAORecuperaOrdiniDaID();
        MessageToCommand messageToCommand = new MessageToCommand();
        messageToCommand.fromMessage(inputLine);
        String command = messageToCommand.getCommand();


        switch (command) {
            case "VISUALIZZANOTI":
                try {
                    appoggio = "";
                    int number = Integer.parseInt(messageToCommand.getPayload());
                    listaDati = ordini.execute(listaID.get(number));
                    for (String string : listaDati) {
                        appoggio = appoggio + string + "_";
                    }
                    messageToCommand.setCommand("SI");
                    messageToCommand.setPayload(appoggio);
                } catch (IndexOutOfBoundsException | DAOException e) {
                    messageToCommand.setCommand("NO");
                    messageToCommand.setPayload("Elemento non esistente");
                }
                info.sendMessage(messageToCommand.toMessage());

                break;
                

            case "CONFERMANOTIFICA":
                DAOConfermaOrdineDalID confermaOrdine = new DAOConfermaOrdineDalID();
                String[] cose = messageToCommand.getPayload().split("\\|");
                try {
                    boolean noti = confermaOrdine.execute(listaID.get(Integer.parseInt(cose[0])), cose[1]);
                    if (noti) {
                        messageToCommand.setCommand("SI");
                        messageToCommand.setPayload(null);
                    }else{
                        messageToCommand.setCommand("NO");
                        messageToCommand.setPayload(null);
                    }
                    info.sendMessage(messageToCommand.toMessage());
                } catch (DAOException e) {
                    info.sendlog(LivelloInformazione.ERROR, "Problema rilevato nelle DAO per confermare le notifiche");
                    messageToCommand.setCommand("ERROR");
                    messageToCommand.setPayload(null);
                    info.sendMessage(messageToCommand.toMessage());
                } catch (NumberFormatException e1) {
                    info.sendlog(LivelloInformazione.ERROR, "Problema rilevato nella conversione della stringa ad intero");
                    messageToCommand.setCommand("ERRORConversione");
                    messageToCommand.setPayload(null);
                    info.sendMessage(messageToCommand.toMessage());
                }
                setcache(credentials, info);
                break;

            case "EXIT":
                cambiaAttivita = true;
            break;
        
            default:
                messageToCommand.setCommand("NONRICONOSCIUTO");
                messageToCommand.setPayload(null);
                info.sendMessage(messageToCommand.toMessage());
                cambiaAttivita = true;
                break;
        }
    }

    private void setcache(Credential credentials, ControllerInfoSulThread info){

        DAOIdNegozio daoIdNegozio = new DAOIdNegozio();
        DAORecuperaIdOrdini idOrdini = new DAORecuperaIdOrdini();
        try {
            int id = daoIdNegozio.execute(credentials.getUsername());
            listaID = idOrdini.execute(id);
            if (listaID == null) return;
            
        } catch (DAOException e) {
            info.sendlog(LivelloInformazione.ERROR, "Problema rilevato nelle DAO per visualizare le notifiche " + e.getMessage());
        }

    }
}

