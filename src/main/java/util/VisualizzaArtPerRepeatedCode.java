package util;

import carrello.Carrello;
import carrello.CarrelloCache;
import model.domain.ControllerInfoSulThread;

public class VisualizzaArtPerRepeatedCode {

    public void visualizzaArtPerRepeateCode(ControllerInfoSulThread info, int number, CarrelloCache cache){
        String articolo = cache.ritornaArticoloString(number);
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

        System.out.println(message.toMessage());
    }
}