package com.gaalihockey.server;

import com.gaalihockey.message.MessageType;
import com.gaalihockey.message.MessageUtil;
import com.gaalihockey.server.game.Game;
import com.gaalihockey.server.game.Player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayerCommunication {
    private Player player;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Receiver receiver;
    private Sender sender;

    public PlayerCommunication(Game game, Player player, ObjectOutputStream out, ObjectInputStream in) {
        this.player = player;
        this.receiver = new Receiver(player, in);
        this.sender = new Sender(game, player, out);
        this.out = out;
        this.in = in;
    }

    public void startCommunication() {
        new Thread(this.receiver).start();
        new Thread(this.sender).start();
    }

    public static void sendSearchingMessage(ObjectOutputStream out) {
        MessageUtil.sendMessage(out, MessageType.TEXT, "Searching for opponents!");
    }

    public void sendWaitingMessage() {
        MessageUtil.sendMessage(out, MessageType.TEXT, "No opponents available: waiting for opponents!");
    };

    public void sendInitializationMessage() {
        MessageUtil.sendMessage(out, MessageType.INITIALIZE, Integer.toString(this.player.getNumber()));
    };

    public void sendMatchFoundMessage() {
        MessageUtil.sendMessage(out, MessageType.TEXT, "Match found!");
    };

    public void sendMatchStartedMessage() {
        MessageUtil.sendMessage(out, MessageType.TEXT, "Match Started!");
    };

    public void sendPuckPosition(double puckX, double puckY) {
        MessageUtil.sendMessage(out, MessageType.PUCK, Double.toString(puckX), Double.toString(puckY));
    }

}
