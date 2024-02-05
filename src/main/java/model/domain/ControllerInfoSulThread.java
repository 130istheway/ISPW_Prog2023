package model.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ControllerInfoSulThread {

    private long threadId;
    private boolean running;

    private BufferedReader in;
    private PrintWriter out;

    public ControllerInfoSulThread(BufferedReader input, PrintWriter output) {
        this.threadId = 0;
        this.running = true;
        this.in = input;
        this.out = output;
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
}
