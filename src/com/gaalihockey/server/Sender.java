package com.gaalihockey.server;

import java.io.*;

public class Sender implements Runnable {
    private ObjectOutputStream out;
    private MatchThread match;

    public Sender(ObjectOutputStream out, MatchThread match) {
        this.out = out;
        this.match = match;
    }

    @Override
    public void run() {
        // Get output from match and send to client
    }
}
