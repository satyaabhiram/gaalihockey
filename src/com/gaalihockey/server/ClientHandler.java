package com.gaalihockey.server;

import java.net.*;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    private Receiver receiver;
    private Sender sender;

    private static ArrayList<MatchThread> matchThreads = new ArrayList<>();
    private static MatchThread openMatchThread;

    private MatchThread matchThread;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        this.joinGame();

        this.receiver = new Receiver(this.clientSocket, this.matchThread);
        this.sender = new Sender(this.clientSocket, this.matchThread);
        new Thread(this.receiver).start();
        new Thread(this.sender).start();
    }

    private void joinGame() {
        if (this.matchThread == null && openMatchThread == null) {
            this.matchThread = new MatchThread();
            openMatchThread = this.matchThread;
            matchThreads.add(openMatchThread);
        } else if (this.matchThread == null) {
            this.matchThread = openMatchThread;
            this.matchThread.open = false;
            new Thread(this.matchThread).start();
            openMatchThread = null;
        }
    }
}
