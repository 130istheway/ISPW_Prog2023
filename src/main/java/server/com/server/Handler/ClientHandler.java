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

    ControllerInfoSulThread info = new ControllerInfoSulThread();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void setNumber(long id) {
        this.info.setThreadId(id);
    }

    public void stopRunning() {
        this.info.running(false);
    }

    public void run(){
            try {
                System.out.println("Sono dentro l'handler del Client");
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BaseController app = new BaseController(in, out, this.info);
                app.execute();
            } catch (IOException | PersonalException e) {
                System.out.println("Thread server " + this.info.getThreadId() + ": Sta terminando : " + e.getMessage());
            }
            if (!this.info.isRunning()) {
                System.out.println("il capo mi sta chiudendo | thread number: " + this.info.getThreadId() );
            }
    }
}