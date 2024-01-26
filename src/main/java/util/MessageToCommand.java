package util;

public class MessageToCommand {
    
    private String command;
    private String payload;
    
    public void fromMessage(String message){
        String[] parti = message.split("|");
        command = parti[0].trim();
        if (parti.length > 1) {
            payload = parti[1].trim();
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
La stringa sarà divisa da | in due, il comando e il parametro, per entrambi sia il client e il server questo metodo è identicoyatta));
*/