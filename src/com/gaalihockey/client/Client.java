package com.gaalihockey.client;

import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private Receiver receiver;
    private Sender sender;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            receiver = new Receiver(in);
            sender = new Sender(out);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void startGame() {
        receiver.start();
        sender.start();
    }

    public void stopConnection() {
        try {
            System.out.println("Connection closed!");
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Gali Hockey");

        try {
            Client client1 = new Client();
            client1.startConnection("127.0.0.1", 1234);
            TimeUnit.SECONDS.sleep(5);
            client1.startGame();
            Client client2 = new Client();
            client2.startConnection("127.0.0.1", 1234);
            TimeUnit.SECONDS.sleep(5);
            client2.startGame();
            client1.stopConnection();
            client2.stopConnection();
        } catch (InterruptedException e) {

        }
    }
}
