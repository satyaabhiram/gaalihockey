package com.gaalihockey.client;

import java.net.*;


import java.io.*;

public class Client {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Receiver receiver;
    private Sender sender;

    public void startConnection(String ip, int port) {
        try {
            this.clientSocket = new Socket(ip, port);
            this.in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            this.out = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            this.out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Could not open client socket", e);
        }
    }

    public void startGame() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Application.launch(Game.class);
//            }
//        }).start();
        this.sender = new Sender(this.out);
        this.receiver = new Receiver(this.in);
        Thread senderThread = new Thread(this.sender);
        Thread receiverThread = new Thread(this.receiver);
        senderThread.start();
        receiverThread.start();
        
        try {
            senderThread.join();
            receiverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //this.receiver = new Receiver(this.in);
        //Thread receiverThread = new Thread(this.receiver);
//        receiverThread.start();
//        try {
//            receiverThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void stopConnection() {
        try {
            clientSocket.close();
        } catch (IOException e){
            throw new RuntimeException("Could not close client socket", e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Gaali Hockey");

        Client client1 = new Client();
        client1.startConnection("192.168.0.9", 1234);
        client1.startGame();
        client1.stopConnection();
    }
}
