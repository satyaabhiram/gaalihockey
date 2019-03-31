package com.gaalihockey.server;

public class MatchThread implements Runnable {
    public boolean open = true;

    public int player1score, player2score,
            player1position, player2position,
            puckX, puckY;

    @Override
    public void run() {
        // Update puck position in loop
    }
}
