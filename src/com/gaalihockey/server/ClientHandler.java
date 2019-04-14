package com.gaalihockey.server;

import com.gaalihockey.message.Message;
import com.gaalihockey.message.MessageType;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    private Receiver receiver;
    private Sender sender;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private static ArrayList<MatchThread> matchThreads = new ArrayList<>();
    private static MatchThread openMatchThread;

    private MatchThread matchThread;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            this.out = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            PlayerCommunication.sendSearchingMessage(this.out);

            this.in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));

            this.joinMatch();

        } catch (IOException e) {
            throw new RuntimeException("Could not setup in/out streams", e);
        }
    }

    private void joinMatch() {
        if (this.matchThread == null && openMatchThread == null) {
            this.matchThread = new MatchThread();

            this.matchThread.setPlayer1(this.out, this.in);

            openMatchThread = this.matchThread;
            matchThreads.add(openMatchThread);
        } else if (this.matchThread == null && openMatchThread.isOpen()) {
            this.matchThread = openMatchThread;
            this.matchThread.setOpen(false);

            this.matchThread.setPlayer2(this.out, this.in);

            Thread mt = new Thread(this.matchThread);
            mt.start();
            try {
                mt.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            openMatchThread = null;
        }
    }
}
