package model.domain;

import java.net.Socket;

public class ControllerInfoSulThread {

    private long threadId;
    private boolean running;
    Socket socket;

    public ControllerInfoSulThread(){
        this.threadId = (long) 0;
        this.running = true;
    }



    public void running(boolean run){
        this.running = run;
    }

    public boolean isRunning(){
        return running;
    }



    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket(){
        return socket;
    }

    public void setThreadId(long threadId){
        this.threadId = threadId;
    }

    public Long getThreadId(){
        return threadId;
    }


}
