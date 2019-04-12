package com.gaalihockey.server.game;

public class Game {
    private Puck puck;
    private Player player1, player2;

    public Puck getPuck() {
        return puck;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Game() {
        this.puck = new Puck();
        this.player1 = new Player(1);
        this.player2 = new Player(2);

        this.player1.setOpponent(player2);
        this.player2.setOpponent(player1);
    }

    public void resetScore() {
        this.updateScore(0, 0);
    }

    public void resetPuckPosition() {
        this.puck.setX(0);
        this.puck.setY(0);
    }

    public void resetPlayerPositions() {
        this.player1.setX(0);
        this.player2.setY(0);
    }

    public void initializePuckVelocity() {
        this.puck.setVelocityX((int) (Math.random() * 1000));
        this.puck.setVelocityY((int) (Math.random() * 1000));
    }

    public void updateScore(int player1score, int player2score) {
        this.player1.setScore(player1score);
        this.player2.setScore(player2score);
    }

    public void updatePuckPosition() {
        this.puck.setX(this.puck.getX() + this.puck.getVelocityX());
        this.puck.setY(this.puck.getY() + this.puck.getVelocityY());
    }
}
