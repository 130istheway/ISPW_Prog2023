package server.com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.com.server.handler.ClientHandler;
import server.com.server.exception.PersonalException;

public class Server implements Runnable{
    
    Logger logger = LogManager.getLogger(Server.class);

    ArrayList<Thread> threads = new ArrayList<>();
    ArrayList<ClientHandler> app = new ArrayList<>();
    private boolean possoAvereAltreConnessioni = true;

    @Override
    public void run() {
        logger.trace("Starting the Server...");
        try {
            handlerConnection();
        }catch (Exception e) {

            Thread currentThread = Thread.currentThread();
            long threadId = currentThread.getId();     //avrei voluto usare threadId() ma sonarcloud ha detto di no

            logger.error("Il Server al thread : %d Si è concluso, il messaggio d'errore è : %s", threadId, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void handlerConnection () throws IOException, PersonalException{
        ServerSocket serverSocket = new ServerSocket(5000);
        while (possoAvereAltreConnessioni) {
            try {
                logger.trace("Attendo connessioni...");
                Socket socket = serverSocket.accept();

                handler(socket);
            } catch (IOException e) {
                serverSocket.close();
                throw new PersonalException("Qualcosa è andato storto con la socket | " + e.getMessage());
            }catch (PersonalException e){
                if (!(e.getMessage().contains("NONSTOPPAREILSERVER"))) {
                    logger.trace("Server shutdown");
                for (ClientHandler ClientHandlerapp : app) {
                    ClientHandlerapp.stopRunning();
                }
                possoAvereAltreConnessioni = false;
                }
                try {
                    Thread.sleep(300000);
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        serverSocket.close();
        throw new PersonalException("Il server è stato chiuso");
    }


    protected void handler(Socket socket) throws PersonalException{
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if (Objects.equals(in.readLine(), "STOPIT")) {
                logger.info("Ok sto avviando la chiusura dell'applicativo che funge da server");
                throw new PersonalException("STOPIT");
            }

            ClientHandler clientHandlerTemporaneo = getClientHandler(socket);

            app.add(clientHandlerTemporaneo);                           //non posso usare addLast() perchè sonar è molto bello
            Thread appthread = new Thread(clientHandlerTemporaneo);
            appthread.start();
            clientHandlerTemporaneo.setNumber(appthread.getId());
            threads.add(appthread);
            logger.info("aviato il thread : %d ", appthread.getId());
        }catch (IllegalThreadStateException e){
            logger.error("L'applicazione ha provato a rilanciare un thread, cià non dovrebbe mai succedere quindi non so cosa sta succedendo, per sicurezza chiudo l'app");
            throw e;
        }catch(IOException e){
            logger.trace("Questo è un problema con la socket");
        }catch (PersonalException e){
            if (e.getMessage().equals("Errore nella creazione dei buffer per leggere e  scrivere")) {
                throw new PersonalException("NONSTOPPAREILSERVER ma penso abbiamo finito gli handle per gli agganci alle socket");
            }
            logger.error("mi hanno detto : %s", e.getMessage());
            throw new PersonalException("Server ShutDown");
        }
    }


    private static ClientHandler getClientHandler(Socket socket) throws PersonalException {
        ClientHandler clientHandlerTemporaneo;
        try {
            clientHandlerTemporaneo = new ClientHandler(socket);
        } catch (PersonalException e) {
            switch (e.getMessage()) {
                case "La socket passata come parametro è settata a null":
                    throw e;
                    case "Errore nella creazione dei buffer per leggere e  scrivere":
                        throw new PersonalException("Errore crezione punti di aggancio socket");
                        default:
                            throw new PersonalException("Non so qual'è il problema non dovrei avere altri tipi di errore");
            }
        }
        return clientHandlerTemporaneo;
    }
}