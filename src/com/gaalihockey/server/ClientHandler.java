package com.gaalihockey.server;

import java.net.*;
import java.io.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private static MatchThread openMatchThread;
    private static int numberOfActiveMatchThreads = 0;
    private static int numberOfPlayers = 0;

    private MatchThread matchThread;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
        	//System.out.println("Client Handler Thread started");
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

            this.matchThread.setPlayer1(this.clientSocket, this.out, this.in);

            openMatchThread = this.matchThread;
            numberOfPlayers++;
        } else if (this.matchThread == null && openMatchThread.isOpen()) {
            openMatchThread.setOpen(false);

            openMatchThread.setPlayer2(this.clientSocket, this.out, this.in);

            Thread mt = new Thread(openMatchThread);
            mt.start();

            openMatchThread = null;
            numberOfActiveMatchThreads++;
            numberOfPlayers++;

            System.out.println("Number of active matches: " + numberOfActiveMatchThreads + ".\nNumber of players connected: " + numberOfPlayers);

            try {
                mt.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            numberOfActiveMatchThreads--;
            numberOfPlayers-=2;
            System.out.println("Number of active matches: " + numberOfActiveMatchThreads + ".\nNumber of players connected: " + numberOfPlayers);
        }
    }
}
