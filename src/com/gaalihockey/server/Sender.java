package com.gaalihockey.server;

import com.gaalihockey.message.MessageType;
import com.gaalihockey.message.MessageUtil;
import com.gaalihockey.server.game.Game;
import com.gaalihockey.server.game.Player;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class Sender implements Runnable {
    private Game game;
    private Player player;
    private ObjectOutputStream out;

    public Sender(Game game, Player player, ObjectOutputStream out) {
        this.game = game;
        this.player = player;
        this.out = out;
    }

    @Override
    public void run() {
        while (true) {
            // Write output when Game variables change
            try {
            	//System.out.println("in Sender Server");
                TimeUnit.MILLISECONDS.sleep(36);
                this.sendPuckPosition();
                this.sendOpponentPosition();
                this.sendScore();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            // This three function calls will never happen
//            this.sendPuckPosition();
//            this.sendOpponentPosition();
//            this.sendScore();
        }
    }

    private void sendPuckPosition() {
    	double[] temp = this.game.getPuck().getXY();
        MessageUtil.sendMessage(this.out, MessageType.PUCK, Double.toString(temp[0]), Double.toString(temp[1]));
    }

    private void sendOpponentPosition() {
        MessageUtil.sendMessage(this.out, MessageType.OPPONENT, Double.toString(this.player.getOpponent().getX()), Double.toString(this.player.getOpponent().getY()));
    }

    private void sendScore() {
        MessageUtil.sendMessage(this.out, MessageType.SCORE, Integer.toString(this.game.getPlayer1().getScore()), Integer.toString(this.game.getPlayer2().getScore()));
    }
}
