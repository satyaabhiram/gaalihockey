package com.gaalihockey.server;

import java.net.*;
import java.io.*;

public class Server {
    private ServerSocket serverSocket;

    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void stopServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Server started!\n\n\n");
        Server server = new Server();
        server.startServer(1234);
    }
}
