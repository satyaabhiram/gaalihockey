package com.gaalihockey.server;

import java.io.*;
import java.net.*;

public class Sender extends Thread {
    private PrintWriter out;
    private Socket clientSocket;
    private MatchThread match;

    public Sender(PrintWriter out, Socket clientSocket, MatchThread match) {
        this.out = out;
        this.clientSocket = clientSocket;
        this.match = match;
    }

    @Override
    public void run() {
        // Get output from match and send to client
    }
}
