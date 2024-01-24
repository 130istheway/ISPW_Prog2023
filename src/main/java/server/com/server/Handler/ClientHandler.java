package server.com.server.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import controller.BaseController;
import model.domain.ControllerInfoSulThread;
import server.com.server.exception.PersonalException;
import util.SingletonLogger;
import model.domain.LivelloInformazione;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private static final String ACTION_1 = "quindi questo thread :";
    private static final String ACTION_2 = " : si arrestera";

    SingletonLogger log = SingletonLogger.getInstance();

    ControllerInfoSulThread info;

    public ClientHandler(Socket socket) throws PersonalException {
        if (socket == null) throw new PersonalException("La socket passata come parametro è settata a null");
        this.socket = socket;
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            info = new ControllerInfoSulThread(in, out, log);
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
                this.info.sendlog( LivelloInformazione.ERROR ,"Thread server " + this.info.getThreadId() + ": Sta terminando : " + e.getMessage());
            }catch (PersonalException  e) {
                switch (e.getMessage()) {
                    case "NON si è voluto autenticare":
                        this.info.sendlog( LivelloInformazione.ERROR ,"Login non effettuato perchè non si è voluto autenticare " + ACTION_1 + this.info.getThreadId() + ACTION_2 );
                        break;
                    
                    case "Sono uscito dal login perchè il server ha chiuso":
                        this.info.sendlog( LivelloInformazione.ERROR ,"Login non effettuato perchè il server stava chiudendo " + ACTION_1 + this.info.getThreadId() + ACTION_2 );
                        break;

                    case "Ha sbagliato ad autenticarsi":
                        this.info.sendlog( LivelloInformazione.ERROR ,"Login non effetuato perchè ha sbagliato troppe volte " + ACTION_1 + this.info.getThreadId() + ACTION_2 );
                        break;

                    default:
                        this.info.sendlog( LivelloInformazione.ERROR ,e.getMessage() + " the thread is : " + this.info.getThreadId());
                        break;
                    
                }
            }
            if (!this.info.isRunning()) {
                this.info.sendlog( LivelloInformazione.DEBUG ,"il capo mi sta chiudendo | thread number: " + this.info.getThreadId() );
            }
    }
}