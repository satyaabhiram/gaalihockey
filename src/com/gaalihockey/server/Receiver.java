package com.gaalihockey.server;

import java.io.*;
import java.net.*;

public class Receiver implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private MatchThread match;

    public Receiver(Socket clientSocket, MatchThread match) {
        try {
            this.clientSocket = clientSocket;
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.match = match;
        } catch (IOException e) {
            throw new RuntimeException("Could not open client socket", e);
        }
    }

    @Override
    public void run() {
        String inputLine;
        try {
            while ((inputLine = this.in.readLine()) != null) {
                // Handle input from client in MatchPool
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read line", e);
        }
    }
}
