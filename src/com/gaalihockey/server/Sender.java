package com.gaalihockey.server;

import com.gaalihockey.message.Message;
import com.gaalihockey.message.MessageType;

import java.io.*;

public class Sender {
    private ObjectOutputStream out;

    public Sender(ObjectOutputStream out) {
        this.out = out;
    }

    public static void sendMessage(ObjectOutputStream out, MessageType messageType, String value1) {
        Message m = new Message(messageType, value1);
        try {
            out.writeObject(m);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Could not write output", e);
        }
    }

    public void sendMessage(MessageType messageType, String value1) {
        Message m = new Message(messageType, value1);
        try {
            this.out.writeObject(m);
            this.out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Could not write output", e);
        }
    }

    public void sendMessage(MessageType messageType, String value1, String value2) {
        Message m = new Message(messageType, value1, value2);
        try {
            this.out.writeObject(m);
            this.out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Could not write output", e);
        }
    }
}
