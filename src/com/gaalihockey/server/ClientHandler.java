package com.gaalihockey.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    private Receiver receiver;
    private Sender sender;

    private static ArrayList<MatchThread> matchThreads = new ArrayList<>();
    private static MatchThread openMatchThread;

    private MatchThread matchThread;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (this.matchThread == null && openMatchThread == null) {
            this.out.println("No matches available! Please wait for opponent!");
            this.matchThread = new MatchThread();
            openMatchThread = this.matchThread;
            matchThreads.add(openMatchThread);
        } else if (this.matchThread == null) {
            this.matchThread = openMatchThread;
            this.matchThread.open = false;
            this.matchThread.state = "playing";
            this.out.println("MatchThread found!");
            openMatchThread = null;
        }

        this.receiver = new Receiver(this.in, this.clientSocket, this.matchThread);
        this.sender = new Sender(this.out, this.clientSocket, this.matchThread);
        this.receiver.start();
        this.sender.start();
    }
}
