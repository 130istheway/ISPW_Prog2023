package controller.user;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import model.domain.LivelloInformazione;
import util.MessageToCommand;

import java.io.IOException;

import carrello.Carrello;

public class VisualizzaUserController {

    boolean cambiaAttivita = false;

    public void viusalizzaController(Credential credentials, ControllerInfoSulThread info, Carrello carrello){
        MessageToCommand messageToCommand = new MessageToCommand();
        messageToCommand.setCommand("OK");
        messageToCommand.setPayload(null);
        info.sendMessage(messageToCommand.toMessage());
        String inputLine;
        try {
        if (info.isRunning()) {
            cambiaAttivita = false;
            while (((inputLine = info.getMessage()) != null) && (!cambiaAttivita)) {
                controll(inputLine, credentials, info, carrello);
                    System.out.println(inputLine);
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
        int number = Integer.parseInt(messageToCommand.getPayload());

        switch (command) {
            case "VISUALIZZAART":
                String articolo = carrello.ritornaArticoloString(number);
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

            case "RIMUOVIART":
                boolean delete = carrello.elimina(number);
                if (!delete) {
                    info.sendlog(LivelloInformazione.INFO, "non è stato possibile cancellare l'articolo" + number + credentials.getUsername());
                    messageToCommand.setCommand("NO");
                    messageToCommand.setPayload(null);
                    info.sendMessage(messageToCommand.toMessage());
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
