package com.gaalihockey.server;

import java.io.*;
import java.net.*;

public class Sender implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private MatchThread match;

    public Sender(Socket clientSocket, MatchThread match) {
        try {
            this.clientSocket = clientSocket;
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.match = match;
        } catch (IOException e) {
            throw new RuntimeException("Could not open client socket", e);
        }
    }

    @Override
    public void run() {
        // Get output from match and send to client
    }
}
