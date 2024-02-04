package com.app.progettoispw202324;

import server.com.server.Server;

import java.util.ArrayList;

public class Inizio {

    public static void main(String[] args){
        setUp();
    }

    private static void setUp(){
        ArrayList<Thread> thread = new ArrayList<>();

        Runnable server = new Server();
        thread.add(new Thread(server));

        for (Thread thread2 : thread) {
            thread2.start();
        }
    }
}
