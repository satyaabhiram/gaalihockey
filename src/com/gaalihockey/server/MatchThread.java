package com.gaalihockey.server;

import com.gaalihockey.message.Message;
import com.gaalihockey.message.MessageType;
import com.gaalihockey.server.game.Player;
import com.gaalihockey.server.game.Puck;

import java.io.*;
import java.util.*;

public class MatchThread implements Runnable {
    private Player player1, player2;
    private PlayerCommunication playerCommunication1, playerCommunication2;
    private Puck puck;
    private boolean open;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public MatchThread() {
        this.puck = new Puck();
        this.open = true;
    }

    public void setPlayer1(ObjectOutputStream out, ObjectInputStream in) {
        this.player1 = new Player(1);
        this.playerCommunication1 = new PlayerCommunication(this.player1, out, in);
        this.playerCommunication1.sendWaitingMessage();
        this.playerCommunication1.sendInitializationMessage();
    }

    public void setPlayer2(ObjectOutputStream out, ObjectInputStream in) {
        this.player2 = new Player(2);
        this.playerCommunication2 = new PlayerCommunication(this.player2, out, in);
        this.playerCommunication1.sendMatchFoundMessage();
        this.playerCommunication2.sendInitializationMessage();
    }

    @Override
    public void run() {
        // Initialize match
        this.playerCommunication1.sendMatchStartedMessage();
        this.playerCommunication2.sendMatchStartedMessage();

        // Reset score
        this.updateScore(0, 0);
        // Reset puck position
        this.resetPuckPosition();
        // Reset strikers
        this.resetPlayerPositions();
        // Initialize random velocity to puck
        this.initializePuckVelocity();
        // Update puck position in loop
        this.startPuckPositionUpdater();
    }

    private void updateScore(int player1score, int player2score) {
        this.player1.setScore(player1score);
        this.player2.setScore(player2score);
    }

    private void resetPuckPosition() {
        this.puck.setX(0);
        this.puck.setY(0);
    }

    private void resetPlayerPositions() {
        this.player1.setX(0);
        this.player2.setY(0);
    }

    private void initializePuckVelocity() {
        this.puck.setVelocityX((int)(Math.random() * 1000));
        this.puck.setVelocityY((int)(Math.random() * 1000));
    }

    private void startPuckPositionUpdater() {
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
        this.puck.setX(this.puck.getX() + this.puck.getVelocityX());
        this.puck.setY(this.puck.getY() + this.puck.getVelocityY());

        this.playerCommunication1.sendPuckPosition(this.puck.getX(), this.puck.getY());
        this.playerCommunication2.sendPuckPosition(this.puck.getX(), this.puck.getY());
    }
}
