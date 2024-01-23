package server;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import server.com.server.Server;
import server.com.server.exception.PersonalException;


class DemoServer{
    public static void main(String[] args) {
        Runnable server = new Server();

        ArrayList<Thread> thread = new ArrayList<>();

        thread.add(new Thread(server));

        for (Thread thread2 : thread) {
            thread2.start();
        }
    }
}


class DemoClient{
    public static void main(String[] args) {
        Runnable client = new Client();

        ArrayList<Thread> thread = new ArrayList<>();

        thread.add(new Thread(client));

        for (Thread thread2 : thread) {
            thread2.start();
        }
    }
}


class DemoClient2{
    public static void main(String[] args) {
        Runnable client = new Client();

        ArrayList<Thread> thread = new ArrayList<>();

        thread.add(new Thread(client));

        for (Thread thread2 : thread) {
            thread2.start();
        }
    }
}


class DemoClient3{
    public static void main(String[] args) {
        Runnable client = new Client();

        ArrayList<Thread> thread = new ArrayList<>();

        thread.add(new Thread(client));

        for (Thread thread2 : thread) {
            thread2.start();
        }
    }
}



class conn {
    public static Socket connect(String host, int s) {
        Socket socket;

        try {
            socket = new Socket(host, s);
            return socket;
        } catch (Exception e) {
            System.err.println("Socket non funzionante");
        }
        return null;
    }
}




class DemoSTOPIT{
    static String rispostaServer = "The server Response : ";
    public static void main(String[] args) {
        try{
        final String host = "localhost";
        int port = 5000;

        Socket socket = conn.connect(host, port);

        System.out.println("Connected to server on port " + SocketSingleton.getPort());

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Send a message to the server
        out.println("STOPIT");
        System.out.println(rispostaServer + in.readLine());

        }catch(IOException ignored){
            System.err.println("Problema con Socket");
        }
    }
}


class Client implements Runnable{
    static String rispostaServer = "The server Response : ";
    public void run() {

        boolean exit;
        boolean repeat = false;
        int rip = 0;
        String response;
        String gigi = null;
        connect : while(true) {
            try {
                Scanner scanner = new Scanner(System.in);

                final String host = "localhost";
                int port = 5000;
                Socket socket;

                try {
                    socket = new Socket(host, port);
                } catch (Exception e) {
                    System.err.println("Errore con Socket");
                    throw e;
                }
                rip=0;
                System.out.println("Connected to server on port " + SocketSingleton.getPort());

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Send a message to the server
                out.println("Hello, server!");
                System.out.println("Invio al server Hello, server!");


                // Read the server's response
                response = in.readLine();
                System.out.println(rispostaServer + response);

                if (repeat){
                    out.println(gigi);
                    response = in.readLine();
                    System.out.println(rispostaServer + response);
                    repeat = false;
                    if (Objects.equals(response, "STOPTHAT")){
                        break;
                    }
                }

                while (true) {
                    System.out.println("Enter a line of text:");
                    gigi = scanner.nextLine();
                    if (!Objects.equals(gigi, "exit")) {
                        out.println(gigi);
                        response = in.readLine();
                        System.out.println(rispostaServer + response);
                        if (Objects.equals(response, "STOPTHAT")) {
                            break connect;
                        }
                    } else {
                        exit = true;
                        break;
                    }
                }
                if (exit){
                    break;
                }
            } catch (IOException ex) {
                try {
                    rip++;
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (rip>4){
                    try {
                        throw new PersonalException("Provato 5 volte a riconettermi, nessuna speranza chiudo l'applicazione");
                    } catch (PersonalException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("sto provando a riconettermi al server");
                repeat = true;
            }
        }
        System.out.println("Sto uscendo dal thread");
    }
}


class SocketSingleton {

    private static Socket socket;
    private static final String HOST = "localhost";
    private static final int PORT = 5000;

    private SocketSingleton() {}

    public static Socket getSocket() {
        if (socket == null) {
            try {
                socket = new Socket(HOST, PORT);
            } catch (Exception e) {
                System.err.println("Errore con Socket");
            }
        }
        return socket;
    }

    public static void reconect () throws IOException {
        socket = new Socket(HOST, PORT);
    }

    public static int getPort(){
        return PORT;
    }
}