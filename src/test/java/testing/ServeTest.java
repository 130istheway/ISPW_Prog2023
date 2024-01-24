package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.com.server.Server;

class ServeTest {
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
    void getToLogin() throws IOException {

        final String host = "localhost";
        int port = 5000;
        Socket socket;

        String response;

        Scanner scanner = new Scanner(System.in);

        socket = new Socket(host, port);

        System.out.println("Connected to server on port " + port);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println("Hello, server!");

        response = in.readLine();
        System.out.println(response);

        assertEquals("LOGIN", response);

        out.println("LOGIN");
        response = in.readLine();
        System.out.println(response);

        assert(response.equals("Autenticarsi: "));

        out.println("user:gigi,pass:gigi");
        response = in.readLine();
        System.out.println(response);

        assert(response.equals("Accettata"));

        out.println("WRITEBACK");
        response = in.readLine();
        System.out.println(response);

        assert(response.equals("WRITEBACK MODE"));

        out.println("Oggi si vola");
        response = in.readLine();
        System.out.println(response);

        assert(response.equals("Oggi si vola"));

        out.println("STOPWRITEBACK");
        response = in.readLine();
        System.out.println(response);

        assert(response.equals("WRITEBACKENDED"));

        out.println("EXIT");
        response = in.readLine();
        System.out.println(response);

        assert(response.equals("STOPIT"));

    }


    @Test
    void testLogin2() throws IOException {

        final String host = "localhost";
        int port = 5000;
        Socket socket;

        String response;

        Scanner scanner = new Scanner(System.in);

        socket = new Socket(host, port);

        System.out.println("Connected to server on port " + port);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println("Hello, server!");

        response = in.readLine();
        System.out.println(response);

        assertEquals("LOGIN", response);

        out.println("LOGIN");
        response = in.readLine();
        System.out.println(response);

        assert(response.equals("Autenticarsi: "));

        out.println("user:lollo,pass:gigi");
        response = in.readLine();
        System.out.println(response);

        assert(response.equals("Riprova"));

        out.println("user:popo,pass:gigi");
        response = in.readLine();
        System.out.println(response);

        assert(response.equals("Riprova"));

        out.println("user:brigido,pass:gigi");
        response = in.readLine();
        System.out.println(response);

        assert(response.equals("Riprova"));

        out.println("user:lolletta,pass:gigi");
        response = in.readLine();
        System.out.println(response);

        assert(response.equals("Riprova"));

        out.println("user:jonathanjoestar,pass:gigi");
        response = in.readLine();
        System.out.println(response);

        assert(response.equals("Riprova"));

        out.println("user:jonathanjoestar,pass:gigi");
        response = in.readLine();
        System.out.println(response);

        assert(response.equals("Rifiutato"));

        response = in.readLine();
        System.out.println(response);

        assert(response.equals("STOPIT"));
    }


}