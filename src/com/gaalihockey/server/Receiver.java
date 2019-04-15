package com.gaalihockey.server;

import com.gaalihockey.message.Message;
import com.gaalihockey.message.MessageUtil;
import com.gaalihockey.server.game.Player;

import java.io.*;

public class Receiver implements Runnable {
	private ObjectInputStream in;
	private Player player;

	public Receiver(Player player, ObjectInputStream in) {
		this.player = player;
		this.in = in;
	}

	@Override
	public void run() {
		Message inputMessage;
		while ((inputMessage = MessageUtil.getMessage(this.in)) != null) {
			// Handle input from client in MatchPool
			switch (inputMessage.getMessageType()) {
			case TEXT:
				System.out.println("Message from Player: " + inputMessage.getValue1());
				break;
			case STRIKER:
				this.player.setX(Double.parseDouble(inputMessage.getValue1()));
				this.player.setY(Double.parseDouble(inputMessage.getValue2()));
				System.out.println(this.player.getX());
				break;
			default:
				break;
			}
		}
	}
}
