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
            } catch (Exception e) {
//                e.printStackTrace();
                break;
            }
    	}
    }
    private void sendStrikerPosition() throws IOException {
    	Rectangle striker;
    	if (Game.isPlayer1) {
    		striker = Game.player1Striker;
    	}
    	else {
    		striker = Game.player2Striker;
    	}
    	MessageUtil.sendMessage(this.out, MessageType.STRIKER, Double.toString(striker.getX()+striker.getWidth()/2), Double.toString(striker.getY()+striker.getHeight()/2));
    }
}
