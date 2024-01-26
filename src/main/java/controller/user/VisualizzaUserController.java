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
        int number = Integer.parseInt(messageToCommand.getPayload());

        switch (command) {
            case "VISUALIZZAART":
                carrello.ritornaArticolo(info, number);
                break;

            case "RIMUOVIART":
                boolean delete = carrello.elimina(info, number);
                if (!delete) {
                    info.sendlog(LivelloInformazione.INFO, "non è stato possibile cancellare l'articolo" + number + credentials.getUsername());
                    messageToCommand.setCommand("NO");
                    messageToCommand.setPayload(null);
                    info.sendMessage(messageToCommand.toString());
                }
                break;
        
            default:
                cambiaAttivita = true;
                break;
        }
    }



}
