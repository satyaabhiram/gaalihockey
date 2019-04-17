package com.gaalihockey.client;

import java.io.*;
import java.util.concurrent.TimeUnit;

import com.gaalihockey.message.MessageType;
import com.gaalihockey.message.MessageUtil;
import com.gaalihockey.client.game.Game;

import javafx.scene.shape.Rectangle;

public class Sender implements Runnable {
    private ObjectOutputStream out;

    public Sender(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        // Get input from swing and relay to server
    	while(true) {
    		try {
                TimeUnit.MILLISECONDS.sleep(10);
                if (Game.MATCH_STARTED) {
                    this.sendStrikerPosition();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    	}
    }
    private void sendStrikerPosition() {
    	Rectangle striker;
    	if (Game.isPlayer1) {
    		striker = Game.player1Striker;
    	}
    	else {
    		striker = Game.player2Striker;
    	}
    	MessageUtil.sendMessage(this.out, MessageType.STRIKER, Double.toString(striker.getX()), Double.toString(striker.getY()));
    }
}
