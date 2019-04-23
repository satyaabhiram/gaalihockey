package com.gaalihockey.server;

import java.net.*;
import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    // One MatchThread for each match.
    // Static object openMatchThread to remember the MatchThread that is waiting for a player
    private static MatchThread openMatchThread;
    private static int numberOfActiveMatchThreads = 0;
    private static int numberOfPlayers = 0;
    // lock to ensure static objects aren't accessed concurrently
    static final Lock matchThreadLock = new ReentrantLock();

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
        Thread mt = null;
        matchThreadLock.lock();
        try {
            if (this.matchThread == null && openMatchThread == null) {
                // If client is first player, create a MatchThread that is open, and store in static variable
                this.matchThread = new MatchThread();

                this.matchThread.setPlayer1(this.clientSocket, this.out, this.in);

                openMatchThread = this.matchThread;
                numberOfPlayers++;
            } else if (this.matchThread == null && openMatchThread.isOpen()) {
                // If client is second player, add him to the static MatchThread object, reset static variable
                openMatchThread.setOpen(false);

                openMatchThread.setPlayer2(this.clientSocket, this.out, this.in);

                mt = new Thread(openMatchThread);
                mt.start();

                openMatchThread = null;
                numberOfActiveMatchThreads++;
                numberOfPlayers++;

                System.out.println("Number of active matches: " + numberOfActiveMatchThreads + "\nNumber of players connected: " + numberOfPlayers);
            }
        } finally {
            matchThreadLock.unlock();
        }

        if (mt != null) {
            try {
                // Wait for the match to end before terminating ClientHandler thread
                mt.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            numberOfActiveMatchThreads--;
            numberOfPlayers-=2;
            System.out.println("Number of active matches: " + numberOfActiveMatchThreads + "\nNumber of players connected: " + numberOfPlayers);
        }
    }
}
