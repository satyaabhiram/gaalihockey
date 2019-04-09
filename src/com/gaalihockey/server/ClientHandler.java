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
            Message m = new Message(MessageType.TEXT, "Searching for opponents!");
            this.out.writeObject(m);
            this.out.flush();

            this.in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));

            this.sender = new Sender(this.out, this.matchThread);
            new Thread(this.sender).start();
            this.receiver = new Receiver(this.in, this.matchThread);
            new Thread(this.receiver).start();

            this.joinMatch();

        } catch (IOException e) {
            throw new RuntimeException("Could not setup in/out streams", e);
        }
    }

    private void joinMatch() {
        try {
            if (this.matchThread == null && openMatchThread == null) {
                this.matchThread = new MatchThread(this.out);
                openMatchThread = this.matchThread;
                Message m = new Message(MessageType.TEXT, "No opponents available: waiting for opponents!");
                this.out.writeObject(m);
                this.out.flush();
                m = new Message(MessageType.INITIALIZE, "1");
                this.out.writeObject(m);
                this.out.flush();
                matchThreads.add(openMatchThread);
            } else if (this.matchThread == null && openMatchThread.isOpen()) {
                this.matchThread = openMatchThread;
                this.matchThread.setOpen(false);
                Message m = new Message(MessageType.TEXT, "Match found!");
                this.out.writeObject(m);
                this.out.flush();
                m = new Message(MessageType.INITIALIZE, "2");
                this.out.writeObject(m);
                this.out.flush();
                this.matchThread.setClient2(this.out);
                new Thread(this.matchThread).start();
                openMatchThread = null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not write output", e);
        }
    }
}
