package com.gaalihockey.server;

import com.gaalihockey.message.Message;

import java.io.*;

public class Receiver implements Runnable {
    private ObjectInputStream in;
    private MatchThread match;

    public Receiver(ObjectInputStream in, MatchThread match) {
        this.in = in;
        this.match = match;
    }

    @Override
    public void run() {
        Message inputMessage;
        try {
            while ((inputMessage = (Message) this.in.readObject()) != null) {
                // Handle input from client in MatchPool
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not read message", e);
        } catch (IOException e) {
            throw new RuntimeException("Could not read message", e);
        }
    }
}
