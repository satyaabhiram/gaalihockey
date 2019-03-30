package com.gaalihockey.server;

import java.io.*;
import java.net.*;

public class Receiver extends Thread {
    private BufferedReader in;
    private Socket clientSocket;
    private MatchThread match;

    public Receiver(BufferedReader in, Socket clientSocket, MatchThread match) {
        this.in = in;
        this.clientSocket = clientSocket;
        this.match = match;
    }

    @Override
    public void run() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                // Handle input from client in MatchPool
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
