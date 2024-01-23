package server.com.server.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import controller.BaseController;
import model.domain.ControllerInfoSulThread;
import server.com.server.exception.PersonalException;

public class ClientHandler implements Runnable {
    private final Socket socket;

    ControllerInfoSulThread info;

    public ClientHandler(Socket socket) throws PersonalException {
        this.socket = socket;
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            info = new ControllerInfoSulThread(in, out);
        } catch (IOException e) {
            throw new PersonalException("Errore nella creazione dei buffer per leggere e  scrivere");
        }
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
                System.out.println("Thread server " + this.info.getThreadId() + ": Sta terminando : " + e.getMessage());
            }catch (PersonalException  e) {
                System.out.println("Login non effettuato perchè il server stava chiudendo");
            }
            if (!this.info.isRunning()) {
                System.out.println("il capo mi sta chiudendo | thread number: " + this.info.getThreadId() );
            }
    }
}