package server;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import server.com.serverISPW.Server;
import server.com.serverISPW.exception.PersonalException;

class lollo implements Runnable{

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Hello");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    
}


class DemoServer{
    public static void main(String[] args) {
        Runnable Server = new Server();
        Runnable Client = new Client();

        ArrayList<Thread> thread = new ArrayList<Thread>();

        thread.add(new Thread(Server));

        for (Thread thread2 : thread) {
            thread2.start();
        }
    }
}


class DemoClient{
    public static void main(String[] args) {
        Runnable Server = new Server();
        Runnable Client = new Client();

        ArrayList<Thread> thread = new ArrayList<Thread>();

        thread.add(new Thread(Client));

        for (Thread thread2 : thread) {
            thread2.start();
        }
    }
}


class DemoClient2{
    public static void main(String[] args) {
        Runnable Server = new Server();
        Runnable Client = new Client();

        ArrayList<Thread> thread = new ArrayList<Thread>();

        thread.add(new Thread(Client));

        for (Thread thread2 : thread) {
            thread2.start();
        }
    }
}


class DemoClient3{
    public static void main(String[] args) {
        Runnable Server = new Server();
        Runnable Client = new Client();

        ArrayList<Thread> thread = new ArrayList<Thread>();

        thread.add(new Thread(Client));

        for (Thread thread2 : thread) {
            thread2.start();
        }
    }
}


class DemoSTOPIT{
    public static void main(String[] args) {
        try{
        final String HOST = "localhost";
        int PORT = 5000;
        Socket socket;

        try {
            socket = new Socket(HOST, PORT);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        System.out.println("Connected to server on port " + SocketSingleton.getPort());

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Send a message to the server
        out.println("STOPIT");
        System.out.println("The server Response : " + in.readLine());

        }catch(IOException ignored){}
    }
}


class Client implements Runnable{

    public void run() {

        boolean exit = false;
        boolean repeat = false;
        int rip = 0;
        String response = null, gigi = null;
        connect : while(true) {
            try {
                Scanner scanner = new Scanner(System.in);

                final String HOST = "localhost";
                int PORT = 5000;
                Socket socket;

                try {
                    socket = new Socket(HOST, PORT);
                } catch (Exception e) {
                    e.printStackTrace();
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
                System.out.println("Server response: " + response);

                if (repeat){
                    out.println(gigi);
                    response = in.readLine();
                    System.out.println("The server Response : " + response);
                    repeat = false;
                    if (Objects.equals(response, "STOPTHAT")){
                        break connect;
                    }
                }

                inviaMessaggi:while (true) {
                    System.out.println("Enter a line of text:");
                    gigi = scanner.nextLine();
                    if (!Objects.equals(gigi, "exit")) {
                        out.println(gigi);
                        response = in.readLine();
                        System.out.println("The server Response : " + response);
                        if (Objects.equals(response, "STOPTHAT")){
                            break connect;
                        }
                    } else {
                        exit = true;
                        break inviaMessaggi;
                    }
                }
                if (exit){
                    break connect;
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
                continue connect;
            }
        }
        System.out.println("Sto uscendo dal thread");
        return;
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
                e.printStackTrace();
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