package model.domain.ui;

import model.domain.Role;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GestionePerUI {

    private boolean running;
    private String username;
    private  Role role;

    private String negozio;

    private BufferedReader in;
    private PrintWriter out;

    public GestionePerUI(BufferedReader input, PrintWriter output) {
        this.running = true;
        this.in = input;
        this.out = output;
        username = null;
        role= Role.NONE;
        negozio = null;
    }

    public void running(boolean run){
        this.running = run;
    }

    public boolean isRunning(){
        return running;
    }

    public void sendMessage(String message){
        out.println(message);
    }

    public String getMessage() throws IOException{
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void setCredential(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public void setNegozio(String negozio){
        this.negozio = negozio;
    }

    public String getNegozio(){
        return negozio;
    }
    
}
