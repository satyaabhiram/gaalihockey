package com.gaalihockey.server;

import com.gaalihockey.server.game.Game;

import java.io.*;
import java.util.*;

public class MatchThread implements Runnable {
    private Game game;
    private PlayerCommunication playerCommunication1, playerCommunication2;
    private boolean open;

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

    public void setPlayer1(ObjectOutputStream out, ObjectInputStream in) {
        this.playerCommunication1 = new PlayerCommunication(this.game, this.game.getPlayer1(), out, in);
        this.playerCommunication1.sendWaitingMessage();
        this.playerCommunication1.sendInitializationMessage();
    }

    public void setPlayer2(ObjectOutputStream out, ObjectInputStream in) {
        this.playerCommunication2 = new PlayerCommunication(this.game, this.game.getPlayer2(), out, in);
        this.playerCommunication1.sendMatchFoundMessage();
        this.playerCommunication2.sendInitializationMessage();
    }

    @Override
    public void run() {
        // Start match
        this.playerCommunication1.sendStartMatchMessage();
        this.playerCommunication2.sendStartMatchMessage();
        // Initialize match
        this.playerCommunication1.sendMatchStartedMessage();
        this.playerCommunication2.sendMatchStartedMessage();
        System.out.println("sending match thread started message");
        this.playerCommunication1.startCommunication();
        this.playerCommunication2.startCommunication();
        System.out.println("matchThread communication started");
        // Reset score
        this.game.resetScore();
        // Reset puck position
        this.game.resetPuckPosition();
        // Reset strikers
        //this.game.resetPlayerPositions();
        // Initialize random velocity to puck
        this.game.initializePuckVelocity();

        // Update puck position in loop
        this.startPuckPositionUpdater();
        // Watch for wall collisions in loop
        this.startWallCollisionWatcher();
        // Watch for striker hits in loop
        this.startStrikerHitWatcher();
    }

    private void startPuckPositionUpdater() {
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {
                game.updatePuckPosition();
            }
        };

        Timer timer = new Timer();
        long delay = 1L;
        //long period = (long) 1000;
        long period = (long) 10;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

    private void startWallCollisionWatcher() {
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {
                game.watchForWallCollisions();
            }
        };

        Timer timer = new Timer();
        long delay = 2L;
        //long period = (long) 1000;
        long period = (long) 10;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

    private void startStrikerHitWatcher() {
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {
                game.watchForStrikerHits();
            }
        };

        Timer timer = new Timer();
        long delay = 3L;
        long period = (long) 10;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }
}
