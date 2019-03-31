package com.gaalihockey.client;

import java.net.*;
import java.io.*;

public class Sender implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;

    public Sender(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException("Could not write output", e);
        }
    }

    @Override
    public void run() {
        // Get input from swing and relay to server
    }
}
