package com.gaalihockey.client;

import java.io.*;

public class Sender extends Thread {
    private PrintWriter out;

    public Sender(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void run() {
        // Get input from swing and relay to server
    }
}
