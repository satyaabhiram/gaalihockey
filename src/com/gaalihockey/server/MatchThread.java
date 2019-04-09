package com.gaalihockey.server;

import com.gaalihockey.message.Message;
import com.gaalihockey.message.MessageType;

import java.io.*;
import java.util.*;

public class MatchThread implements Runnable {
    private ObjectOutputStream out1, out2;

    private boolean open = true;

    private double player1score, player2score,
            player1position, player2position,
            puckX, puckY, puckVelocityX, puckVelocityY;

    public MatchThread(ObjectOutputStream out1) {
        this.out1 = out1;
    }

    public void setClient2(ObjectOutputStream out2) {
        this.out2 = out2;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public void run() {
        // Initialize match
        Message m = new Message(MessageType.TEXT, "Match Started!");
        try {
            this.out1.writeObject(m);
            this.out1.flush();
            this.out2.writeObject(m);
            this.out2.flush();
        } catch (IOException e) {
            throw new RuntimeException("could not write output", e);
        }

        // Reset score
        this.updateScore(0, 0);
        // Reset puck position
        this.resetPuckPosition();
        // Reset strikers
        this.resetStrikerPositions();
        // Initialize random velocity to puck
        this.initializePuckVelocity();
        // Update puck position in loop
        this.startPuckPositionUpdater();
    }

    private void updateScore(double player1score, double player2score) {
        this.player1score = player1score;
        this.player2score = player2score;
    }

    void resetPuckPosition() {
        this.puckX = 0;
        this.puckY = 0;
    }

    void resetStrikerPositions() {
        this.player1position = 0;
        this.player2position = 0;
    }

    void initializePuckVelocity() {
        this.puckVelocityX = (int)(Math.random() * 1000);
        this.puckVelocityY = (int)(Math.random() * 1000);
    }

    void startPuckPositionUpdater() {
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run () {
                updatePuckPosition();
            }
        };

        Timer timer = new Timer();
        long delay = 0L;
        long period = (long) 10;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

    private void updatePuckPosition() {
        this.puckX += this.puckVelocityX;
        this.puckY += this.puckVelocityY;

        Message m = new Message(MessageType.PUCK, Double.toString(this.puckX), Double.toString(this.puckY));
        try {
            this.out1.writeObject(m);
            this.out1.flush();
            this.out2.writeObject(m);
            this.out2.flush();
        } catch (IOException e) {
            throw new RuntimeException("Could not write output", e);
        }
    }
}
