package server.com.server.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.BaseController;
import model.domain.ControllerInfoSulThread;
import server.com.server.exception.PersonalException;

public class ClientHandler implements Runnable {
    
    Logger logger = LogManager.getLogger(ClientHandler.class);

    private final Socket socket;
    private static final String ACTION_1 = "quindi questo thread :";
    private static final String ACTION_2 = " : si arrestera";

    ControllerInfoSulThread info;

    public ClientHandler(Socket socket) throws PersonalException {
        if (socket == null) throw new PersonalException("La socket passata come parametro è settata a null");
        this.socket = socket;
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            info = new ControllerInfoSulThread(in, out);
        } catch (IOException e) {
            throw new PersonalException("Errore nella creazione dei buffer per leggere e  scrivere");
        }
    }

    public Socket getSocket(){
        return this.socket;
    }

    public void setNumber(long id) {
        this.info.setThreadId(id);
    }

    public void stopRunning() {
        this.info.running(false);
    }

    public void run(){
            try {
                BaseController app = new BaseController(this.info);
                app.execute();
            } catch (IOException e) {
                logger.error("Thread server : %d : Sta terminando : %s",this.info.getThreadId(), e.getMessage());
            }catch (PersonalException  e) {
                switch (e.getMessage()) {
                    case "NON si è voluto autenticare":
                            logger.error("Login non effettuato perchè non si è voluto autenticare %s : %d : %s",ACTION_1,this.info.getThreadId(),ACTION_2 );
                        break;
                    
                    case "Sono uscito dal login perchè il server ha chiuso":
                            logger.error("Login non effettuato perchè il server stava chiudendo %s : %d : %s",ACTION_1,this.info.getThreadId(),ACTION_2 );
                        break;

                    case "Ha sbagliato ad autenticarsi":
                            logger.error("Login non effetuato perchè ha sbagliato troppe volte %s : %d : %s",ACTION_1,this.info.getThreadId(),ACTION_2 );
                        break;

                    default:
                            logger.error("%s the thread is : %d ",e.getMessage(),this.info.getThreadId());
                        break;
                    
                }
            }catch (Exception e){
                logger.error("%s the thread is : %d ",e.getMessage(),this.info.getThreadId());
            }
            if (!this.info.isRunning()) {
                logger.error("il capo mi sta chiudendo | thread number: %d",this.info.getThreadId() );
            }
    }
}