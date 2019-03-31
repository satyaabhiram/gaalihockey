package com.gaalihockey.client;

import java.net.*;
import java.io.*;

public class Client {
    private Socket clientSocket;

    private Receiver receiver;
    private Sender sender;

    public void startConnection(String ip, int port) {
        try {
            this.clientSocket = new Socket(ip, port);
        } catch (IOException e) {
            throw new RuntimeException("Could not open client socket", e);
        }
    }

    public void startGame() {
        this.receiver = new Receiver(clientSocket);
        this.sender = new Sender(clientSocket);
        new Thread(this.receiver).start();
        new Thread(this.sender).start();
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
        client1.startConnection("127.0.0.1", 1234);
        client1.startGame();
        client1.stopConnection();
    }
}
