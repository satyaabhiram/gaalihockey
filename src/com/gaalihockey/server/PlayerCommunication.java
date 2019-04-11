package com.gaalihockey.server;

import com.gaalihockey.message.MessageType;
import com.gaalihockey.server.game.Player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayerCommunication {
    private Player player;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Receiver receiver;
    private Sender sender;

    public PlayerCommunication(Player player, ObjectOutputStream out, ObjectInputStream in) {
        this.player = player;
        this.receiver = new Receiver(player, in);
        this.sender = new Sender(out);
        this.out = out;
        this.in = in;
    }

    public static void sendSearchingMessage(ObjectOutputStream out) {
        Sender.sendMessage(out, MessageType.TEXT, "Searching for opponents!");
    }

    public void sendWaitingMessage() {
        this.sender.sendMessage(MessageType.TEXT, "No opponents available: waiting for opponents!");
    };

    public void sendInitializationMessage() {
        this.sender.sendMessage(MessageType.INITIALIZE, Integer.toString(this.player.getNumber()));
    };

    public void sendMatchFoundMessage() {
        this.sender.sendMessage(MessageType.TEXT, "Match found!");
    };

    public void sendMatchStartedMessage() {
        this.sender.sendMessage(MessageType.TEXT, "Match Started!");
    };

    public void sendPuckPosition(double puckX, double puckY) {
        this.sender.sendMessage(MessageType.PUCK, Double.toString(puckX), Double.toString(puckY));
    }
}
