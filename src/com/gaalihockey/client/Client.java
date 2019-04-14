package com.gaalihockey.client;

import com.gaalihockey.client.game.Game;

import java.net.*;
import java.io.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

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
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Application.launch(Game.class);
            }
        }).start();*/
        this.sender = new Sender(this.out);
        new Thread(this.sender).start();
        this.receiver = new Receiver(this.in);
        new Thread(this.receiver).start();
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
