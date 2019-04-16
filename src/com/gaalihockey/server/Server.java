package com.gaalihockey.server;

import java.net.*;
import java.io.*;

public class Server {
    private ServerSocket serverSocket;

    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + port, e);
        }

        try {
            while (true) {
            	System.out.println("Server socket");
            	ClientHandler ch = new ClientHandler(serverSocket.accept());
                //new Thread(new ClientHandler(serverSocket.accept())).start();
            	Thread chThread = new Thread(ch);
            	chThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error accepting client connection", e);
        }
    }

    public void stopServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close server", e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Server started!");
        Server server = new Server();
        server.startServer(1234);
    }
}
