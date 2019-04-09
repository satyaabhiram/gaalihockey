package com.gaalihockey.client;

import java.io.*;

public class Sender implements Runnable {
    private ObjectOutputStream out;

    public Sender(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        // Get input from swing and relay to server
    }
}
