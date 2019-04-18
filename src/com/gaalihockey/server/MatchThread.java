package com.gaalihockey.server;

import com.gaalihockey.server.game.Game;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class MatchThread implements Runnable {
    private Game game;
    private Socket player1Socket, player2Socket;
    private PlayerCommunication playerCommunication1, playerCommunication2;
    private boolean open, gameOn;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public MatchThread() {
        this.game = new Game();
        this.open = true;
    }

    public void setPlayer1(Socket clientSocket, ObjectOutputStream out, ObjectInputStream in) {
        this.player1Socket = clientSocket;
        this.playerCommunication1 = new PlayerCommunication(this.game, this.game.getPlayer1(), out, in);
        try {
            this.playerCommunication1.sendWaitingMessage();
            this.playerCommunication1.sendInitializationMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPlayer2(Socket clientSocket, ObjectOutputStream out, ObjectInputStream in) {
        this.player2Socket = clientSocket;
        this.playerCommunication2 = new PlayerCommunication(this.game, this.game.getPlayer2(), out, in);
        try {
            this.playerCommunication1.sendMatchFoundMessage();
            this.playerCommunication2.sendInitializationMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        gameOn = true;
        // Start match
        try {
            this.playerCommunication1.sendStartMatchMessage();
            this.playerCommunication2.sendStartMatchMessage();
            // Initialize match
            this.playerCommunication1.sendMatchStartedMessage();
            this.playerCommunication2.sendMatchStartedMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.playerCommunication1.startCommunication();
        this.playerCommunication2.startCommunication();
        // Reset score
        this.game.resetScore();
        // Reset puck position
        this.game.resetPuckPosition();
        // Reset strikers
        //this.game.resetPlayerPositions();
        // Initialize random velocity to puck
        this.game.initializePuckVelocity();

        // Update puck position in loop
        Timer t1 = this.startPuckPositionUpdater();
        // Watch for wall collisions in loop
        Timer t2 = this.startWallCollisionWatcher();
        // Watch for striker hits in loop
        Timer t3 = this.startStrikerHitWatcher();

        this.startMatchStateWatcher();

        t1.cancel();
        t1.purge();
        t2.cancel();
        t2.purge();
        t3.cancel();
        t3.purge();
    }

    private Timer startPuckPositionUpdater() {
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {
                game.updatePuckPosition();
            }
        };

        Timer timer = new Timer();
        long delay = 5001L;
        //long period = (long) 1000;
        long period = (long) 10;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
        return timer;
    }

    private Timer startWallCollisionWatcher() {
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {
                watchForWallCollisions();
            }
        };

        Timer timer = new Timer();
        long delay = 5002L;
        //long period = (long) 1000;
        long period = (long) 10;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
        return timer;
    }

    private void watchForWallCollisions() {
        game.watchForWallCollisions();
        try {
            if (gameOn) {
                if (game.getPlayer1().getScore() >= 10) {
                    this.playerCommunication1.sendMatchWonMessage();
                    this.playerCommunication2.sendMatchLostMessage();
                    this.gameOn = false;

                } else if (game.getPlayer2().getScore() >= 10) {
                    this.playerCommunication1.sendMatchLostMessage();
                    this.playerCommunication2.sendMatchWonMessage();
                    this.gameOn = false;
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    private Timer startStrikerHitWatcher() {
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {
                game.watchForStrikerHits();
            }
        };

        Timer timer = new Timer();
        long delay = 5003L;
        long period = (long) 10;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
        return timer;
    }

    private void startMatchStateWatcher() {
        while (gameOn) {
            try {
                if (!playerCommunication1.isAlive()) {
                    playerCommunication2.sendMatchWonMessage();
                    break;
                }
                if (!playerCommunication2.isAlive()) {
                    playerCommunication1.sendMatchWonMessage();
                    break;
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            playerCommunication1.interrupt();
            playerCommunication2.interrupt();
            player1Socket.close();
            player2Socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
