package com.gaalihockey.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MessageUtil {
    public static Message getMessage(ObjectInputStream in) {
        Message m;
        try {
            m = (Message) in.readObject();
        } catch (IOException e) {
            throw new RuntimeException("Could not read input", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not read input", e);
        }
        return m;
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

    public static void sendMessage(ObjectOutputStream out, MessageType messageType, String value1, String value2) {
        Message m = new Message(messageType, value1, value2);
        try {
            out.writeObject(m);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Could not write output", e);
        }
    }
}
