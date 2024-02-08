package controller.user;

import model.domain.ControllerInfoSulThread;
import model.domain.Credential;
import util.MessageToCommand;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import carrello.Carrello;
import util.VisualizzaArtPerRepeatedCode;

/**
 * Classe che si occupa della visualizazzione della lista personale dell'utente
 */
public class VisualizzaUserController {
    
    Logger logger = LogManager.getLogger(VisualizzaUserController.class);

    boolean cambiaAttivita = false;

    /**
     * Metodo che si occupa di ricevere i messaggi dalla socket
     * @param credentials
     * @param info
     * @param carrello
     */
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
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * Logica applicativa della classe ceh permette di visionare la lista e di eventualmente anceh cancellare specifici articoli da essa
     * @param inputLine
     * @param credentials
     * @param info
     * @param carrello
     */
    private void controll(String inputLine, Credential credentials, ControllerInfoSulThread info, Carrello carrello){
        MessageToCommand messageToCommand = new MessageToCommand();
        messageToCommand.fromMessage(inputLine);
        String command = messageToCommand.getCommand();
        int number = Integer.parseInt(messageToCommand.getPayload());

        switch (command) {
            case "VISUALIZZAART":
                VisualizzaArtPerRepeatedCode code = new VisualizzaArtPerRepeatedCode();
                code.visualizzaArtPerRepeateCode(info,number, carrello);
                break;

            case "RIMUOVIART":
                boolean delete = carrello.elimina(number);
                if (!delete) {
                    logger.info("non è stato possibile cancellare l'articolo : %d %S",number,credentials.getUsername());
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
