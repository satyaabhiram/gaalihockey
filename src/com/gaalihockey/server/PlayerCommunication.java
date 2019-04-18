package com.gaalihockey.server;

import com.gaalihockey.message.MessageType;
import com.gaalihockey.message.MessageUtil;
import com.gaalihockey.server.game.Game;
import com.gaalihockey.server.game.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayerCommunication {
	private Player player;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Receiver receiver;
	private Sender sender;
	Thread receiverThread, senderThread;

	public PlayerCommunication(Game game, Player player, ObjectOutputStream out, ObjectInputStream in) {
		this.player = player;
		this.receiver = new Receiver(player, in);
		this.sender = new Sender(game, player, out);
		this.out = out;
		this.in = in;
	}

	public void startCommunication() {
		this.receiverThread = new Thread(this.receiver);
		receiverThread.start();
		senderThread = new Thread(this.sender);
		senderThread.start();
	}

	public void interrupt() {
		this.receiverThread.interrupt();
		this.senderThread.interrupt();
	}

	public boolean isInterrupted() {
		return this.receiverThread.isInterrupted() || this.senderThread.isInterrupted();
	}

	public boolean isAlive() {
		return this.receiverThread.isAlive() && this.senderThread.isAlive();
	}

	public static void sendSearchingMessage(ObjectOutputStream out) throws IOException {
		MessageUtil.sendMessage(out, MessageType.TEXT, "Searching for opponents!");
	}

	public void sendWaitingMessage() throws IOException {
		MessageUtil.sendMessage(out, MessageType.TEXT, "No opponents available: waiting for opponents!");
	}

	public void sendInitializationMessage() throws IOException {
		MessageUtil.sendMessage(out, MessageType.INITIALIZE, Integer.toString(this.player.getNumber()));
	}

	public void sendMatchFoundMessage() throws IOException {
		MessageUtil.sendMessage(out, MessageType.TEXT, "Match found!");
	}

	public void sendMatchStartedMessage() throws IOException {
		//System.out.println("match Started"+out.toString());
		MessageUtil.sendMessage(out, MessageType.TEXT, "Match Started!");
	}

	public void sendStartMatchMessage() throws IOException {
		//System.out.println("Start"+out.toString()+"pc");
		MessageUtil.sendMessage(out, MessageType.START);
	}

	public void sendMatchWonMessage() throws IOException {
		MessageUtil.sendMessage(out, MessageType.RESULT, Integer.toString(1));
	}

	public void sendMatchLostMessage() throws IOException {
		MessageUtil.sendMessage(out, MessageType.RESULT, Integer.toString(0));
	}

	public void sendPuckPosition(double puckX, double puckY) throws IOException {
		MessageUtil.sendMessage(out, MessageType.PUCK, Double.toString(puckX), Double.toString(puckY));
	}

}
