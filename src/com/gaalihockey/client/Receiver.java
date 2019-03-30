package com.gaalihockey.client;

import java.io.*;

public class Receiver extends Thread {
    private BufferedReader in;

    public Receiver(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        String inputLine;

        try {
            while ((inputLine = in.readLine()) != null) {
                // Handle input from server in swing
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
