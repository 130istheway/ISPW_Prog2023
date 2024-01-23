package server.com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

import server.com.server.Handler.ClientHandler;
import server.com.server.exception.PersonalException;

public class Server implements Runnable{
    ArrayList<Thread> threads = new ArrayList<>();
    ArrayList<ClientHandler> app = new ArrayList<>();
    private boolean onlytopreventSonarCloudFromErroring = true;

    @Override
    public void run() {
        System.out.println("Starting the Server...");
        try {
            handlerConnection();
        } catch (Exception e) {
            // handle exception

            Thread currentThread = Thread.currentThread();
            long threadId = currentThread.getId();     //avrei voluto usare threadId() ma sonarcloud ha detto di no

            System.out.println("Il thread : " + threadId + " Si è concluso, il messaggio d'errore è : " + e.getMessage());
        }
        System.out.println("Stodjsagv uifdneavdfrbv <aesfvbhefdc v");
    }

    public void handlerConnection () throws IOException, PersonalException{
        ServerSocket serverSocket = new ServerSocket(5000);
        while (onlytopreventSonarCloudFromErroring) {
            try {
                System.out.println("Attendo connessioni...");
                Socket socket = serverSocket.accept();
                handler(socket);
            } catch (IOException e) {
                serverSocket.close();
                throw new PersonalException("Qualcosa è andato storto con la socket | " + e.getMessage());
            }catch (PersonalException e){
                System.out.println("Server shutDown");

                for (ClientHandler ClientHandlerapp : app) {
                    ClientHandlerapp.stopRunning();
                }
                onlytopreventSonarCloudFromErroring = false;
            }
        }
        serverSocket.close();
        throw new PersonalException("Il server è stato chiuso");
    }


    protected void handler(Socket socket) throws PersonalException{
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            if (Objects.equals(in.readLine(), "STOPIT")) {
                out.println("Ok sto avviando la chiusura dell'applicativo che funge da server");
                throw new PersonalException("STOPIT");
            }
            //out.println("Stai entrando nel sistema"); // rispondo al client che lo sto accettando nel sistema
            ClientHandler clientHandlerTemporaneo = new ClientHandler(socket);                       //non posso usare addLast() perchè sonar è molto bello
            app.add(clientHandlerTemporaneo);
            Thread appthread = new Thread(clientHandlerTemporaneo);
            appthread.start();
            clientHandlerTemporaneo.setNumber(appthread.getId());
            threads.add(appthread);
            System.out.println("aviato il thread : " + appthread.getId());
        }catch (IllegalThreadStateException e){
            System.out.println("L'applicazione ha provato a rilanciare un thread, cià non dovrebbe mai succedere quindi non so cosa sta succedendo, per sicurezza chiudo l'app");
            throw e;
        }catch(IOException e){
            System.out.println("o cazzo");
        }catch (PersonalException e){
            System.err.println("mi hanno detto di : " + e.getMessage());
            throw new PersonalException("Server ShutDown");
    }
    }
}