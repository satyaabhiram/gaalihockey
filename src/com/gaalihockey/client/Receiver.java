package com.gaalihockey.client;

import java.net.*;
import java.io.*;

public class Receiver implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;

    public Receiver(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException("Could not open client socket", e);
        }
    }

    @Override
    public void run() {
        String inputLine;

        try {
            while ((inputLine = this.in.readLine()) != null) {
                // Handle input from server in swing
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read line", e);
        }
    }
}
