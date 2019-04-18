package com.gaalihockey.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MessageUtil {
    public static Message getMessage(ObjectInputStream in) throws Exception {
        return (Message) in.readObject();
    }

    public static void sendMessage(ObjectOutputStream out, MessageType messageType) throws IOException {
        Message m = new Message(messageType);
        synchronized (out) {
            out.writeObject(m);
            out.flush();
        }
    }

    public static void sendMessage(ObjectOutputStream out, MessageType messageType, String value1) throws IOException {
        Message m = new Message(messageType, value1);
        synchronized (out) {
            out.writeObject(m);
            out.flush();
        }
    }

    public static void sendMessage(ObjectOutputStream out, MessageType messageType, String value1, String value2) throws IOException {
        Message m = new Message(messageType, value1, value2);
        synchronized (out) {
            out.writeObject(m);
            out.flush();
        }
    }
}
