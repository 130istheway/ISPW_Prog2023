package server.com.server.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import server.com.server.exception.PersonalException;

public class ClientHandler implements Runnable {
    private volatile boolean running = true;
    private final Socket socket;
    private long number;
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void setNumber(long id) {
        number = id;
    }

    public void stopRunning() {
        this.running = false;
    }

    public void run(){
        try {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Server " + number + ": " + inputLine);
                    if (!running) {
                        out.println("STOPTHAT");
                        System.out.println("Server " + number + ": Non rispondo poichè sto chiudendo la connessione");
                        break;
                    }
                    out.println(inputLine);
                }
            } catch (IOException e) {
                System.out.println("Thread server " + number + ": Sta terminando");
            }
            if (!running) {
                try {
                    throw new PersonalException("il capo mi sta chiudendo");
                } catch (PersonalException e) {
                    throw new RuntimeException(e);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage() + " | thread number: " + number);
        }
        return;
    }
}