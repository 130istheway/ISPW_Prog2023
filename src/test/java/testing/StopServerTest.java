package testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.com.server.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class StopServerTest {

    @BeforeEach
    void setUp(){
        ArrayList<Thread> thread = new ArrayList<>();

        Runnable server = new Server();
        thread.add(new Thread(server));

        for (Thread thread2 : thread) {
            thread2.start();
        }
    }

    @Test
    void stopServer() throws IOException {
        final String host = "localhost";
        int port = 5000;

        String response;

        Socket socket = new Socket(host, port);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Send a message to the server
        out.println("STOPIT");
        response = in.readLine();
        assertEquals("Ok sto avviando la chiusura dell'applicativo che funge da server", response);
    }
}
