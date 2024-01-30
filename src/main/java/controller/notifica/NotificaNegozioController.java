package controller.notifica;

import java.io.IOException;
import java.util.List;

import model.dao.exception.DAOException;
import model.dao.notifica.DAOConfermaOrdineDalID;
import model.dao.notifica.DAORecuperaIdOrdini;
import model.dao.notifica.DAORecuperaOrdiniDaID;
import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;
import util.MessageToCommand;

public class NotificaNegozioController {

    boolean cambiaAttivita = false;

    public void notificaController(Credential credentials, ControllerInfoSulThread info){
        info.sendMessage("OK");
        String inputLine;
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
        MessageToCommand messageToCommand = new MessageToCommand();
        messageToCommand.fromMessage(inputLine);
        String command = messageToCommand.getCommand();


        switch (command) {
            case "VISUALIZZANOTI":
                messageToCommand.setCommand("VISUALIZZA");
                DAORecuperaIdOrdini idOrdini = new DAORecuperaIdOrdini();
                DAORecuperaOrdiniDaID ordini = new DAORecuperaOrdiniDaID();
                List<Integer> listaID;
                List<String> listaOrdini;
                String space = "  ";
                String element = "";

                try {
                    listaID = idOrdini.execute(credentials.getUsername());
                    for (Integer iterable_element : listaID) {
                        listaOrdini = ordini.execute(iterable_element);
                        for (String string : listaOrdini) {
                            element = element + space + string;
                        }
                        messageToCommand.setPayload(element);
                        info.sendMessage(messageToCommand.toMessage());
                    }

                    info.sendMessage("FINITIORDINI");
                } catch (DAOException e) {
                    info.sendlog(LivelloInformazione.ERROR, "Problema rilevato nelle DAO per visualizare le notifiche");
                    info.sendMessage("ERROR");
                }
                cambiaAttivita = true;
                break;
                

            case "CONFERMANOTI":
                DAOConfermaOrdineDalID confermaOrdine = new DAOConfermaOrdineDalID();
                try {
                    confermaOrdine.execute(Integer.parseInt(messageToCommand.getPayload()));
                    info.sendMessage("ACCETTATO");
                } catch (DAOException e) {
                    info.sendlog(LivelloInformazione.ERROR, "Problema rilevato nelle DAO per confermare le notifiche");
                    info.sendMessage("ERROR");
                } catch (NumberFormatException e1) {
                    info.sendlog(LivelloInformazione.ERROR, "Problema rilevato nella conversione della stringa ad intero");
                    info.sendMessage("ERRORConversione");
                }
                cambiaAttivita = true;
                break;
        
            default:
                info.sendMessage("NONRICONOSCIUTO");
                cambiaAttivita = true;
                break;
        }
    }
}

