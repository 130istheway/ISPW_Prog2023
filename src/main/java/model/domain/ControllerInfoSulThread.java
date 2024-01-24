package model.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import util.SingletonLogger;

public class ControllerInfoSulThread {

    SingletonLogger log;

    private long threadId;
    private boolean running;

    Socket socket;

    private BufferedReader in;
    private PrintWriter out;

    public ControllerInfoSulThread(BufferedReader input, PrintWriter output, SingletonLogger logger) {
        this.threadId = 0;
        this.running = true;
        this.in = input;
        this.out = output;
        this.log = logger;
    }

    public ControllerInfoSulThread(){}

    public void running(boolean run){
        this.running = run;
    }

    public boolean isRunning(){
        return running;
    }

    public void setThreadId(long threadId){
        this.threadId = threadId;
    }

    public Long getThreadId(){
        return threadId;
    }


    public void sendMessage(String message){
        out.println(message);
    }

    public String getMessage() throws IOException{
        return in.readLine();
    }

    public void sendlog(LivelloInformazione livello , String message){
        log.sendInformazione(livello, message);
    }
}
