package com.gaalihockey.server;

public class MatchThread implements Runnable {
    public boolean open = true;

    public int player1score, player2score,
            player1position, player2position,
            puckX, puckY, puckVelocityX, puckVelocityY;

    public MatchThread() {
        this.player1score = 0;
        this.player2score = 0;
        this.player1position = 0;
        this.player2position = 0;
        this.puckX = 0;
        this.puckY = 0;
        this.puckVelocityX = 0;
        this.puckVelocityY = 0;
    }

    @Override
    public void run() {
        // Update puck position in loop
        while (true) {
            this.updatePuckPosition();
        }
    }

    private void updatePuckPosition() {
        this.puckX += this.puckVelocityX;
        this.puckY += this.puckVelocityY;
    }
}
