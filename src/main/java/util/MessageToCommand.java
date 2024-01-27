package util;

import java.util.StringTokenizer;

public class MessageToCommand {
    
    private String command;
    private String payload;
    
    public void fromMessage(String message){
        if (message.contains("|")) {
            StringTokenizer parti = new StringTokenizer(message.substring(0, message.length()), "|");
            while (parti.hasMoreTokens()) {
                command = parti.nextToken().trim();
                if ((parti.nextToken()) != null) {
                    payload = message.substring(command.length()+3, message.length());
                    return;
                }
            }
        }else{
            command = message;
        }
    }

    public String toMessage(){
        String message = command;
        if (payload != null) {
            message = message+ " | " + payload;
        }
        return  message;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getCommand() {
        return this.command;
    }

    public String getPayload() {
        return this.payload;
    }

}





/*
qui devo andare avanti e fare la conversione da stringa a due parametri, il comando e il payload
*/