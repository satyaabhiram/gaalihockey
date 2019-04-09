package com.gaalihockey.client;

import com.gaalihockey.message.Message;

import java.io.*;

public class Receiver implements Runnable {
    private ObjectInputStream in;

    public Receiver(ObjectInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        Message inputMessage;
        try {
            while ((inputMessage = (Message) this.in.readObject()) != null) {
                System.out.println("Message from server!\nType: " + inputMessage.getMessageType() + "\nBody: " + inputMessage.getValue1() + ", " + inputMessage.getValue2());
                // Handle input from server in swing
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not read message", e);
        } catch (IOException e) {
            throw new RuntimeException("Could not read message", e);
        }
    }
}
