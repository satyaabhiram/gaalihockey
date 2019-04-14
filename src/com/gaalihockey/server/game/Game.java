package com.gaalihockey.server.game;

public class Game {
    private Puck puck;
    private Player player1, player2;

    private final double boardLowerX = 0;
    private final double boardHigherX = 3200;
    private final double boardLowerY = 0;
    private final double boardHigherY = 1600;

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

    public void watchForWallCollisions() {
        this.checkXWallCollision();
        this.checkYWallCollision();
    }

    public void watchForStrikerHits() {
        this.checkAndHandleStrikerHit();
    }

    private void checkXWallCollision() {
        if (this.puck.getX()<=this.boardLowerX) {
            this.reversePuckVelocityX();
            this.updateScore(this.player1.getScore(), this.player2.getScore()+1);
        } else if (this.puck.getX()>=this.boardHigherX) {
            this.reversePuckVelocityX();
            this.updateScore(this.player1.getScore()+1, this.player2.getScore());
        }
    }

    private void checkYWallCollision() {
        if ((this.puck.getY()<=this.boardLowerY) || (this.puck.getY()>=this.boardHigherY)) {
            this.reversePuckVelocityY();
        }
    }

    private void reversePuckVelocityX() {
        this.puck.setVelocityX(-this.puck.getVelocityX());
    }

    private void reversePuckVelocityY() {
        this.puck.setVelocityY(-this.puck.getVelocityY());
    }

    private void checkAndHandleStrikerHit() {
        if ((this.xPuckDistance(this.player1) <= (this.player1.WIDTH_X/2 + this.puck.RADIUS))
                && (this.yPuckDistance(this.player1) <= (this.player1.WIDTH_Y/2 + this.puck.RADIUS))) {
            if ((this.puck.getY()>=this.player1.getLowerY()) && (this.puck.getY()<=this.player1.getHigherY())) {
                if ((this.puck.getX()>this.player1.getHigherX()) && (this.puck.getVelocityX()<0))
                    this.reversePuckVelocityX();
            } else if ((this.puck.getX()>=this.player1.getLowerX()) && (this.puck.getX()<=this.player1.getHigherX())) {
                if (((this.puck.getY()<this.player1.getLowerY()) && (this.puck.getVelocityY()>0))
                        || ((this.puck.getY()>this.player1.getHigherY()) && (this.puck.getVelocityY()<0)))
                    this.reversePuckVelocityY();
            } else {
                this.reversePuckVelocityX();
                this.reversePuckVelocityY();
            }
        } else if ((this.xPuckDistance(this.player2) <= (this.player2.WIDTH_X/2 + this.puck.RADIUS))
                && (this.yPuckDistance(this.player2) <= (this.player2.WIDTH_Y/2 + this.puck.RADIUS))) {
            if ((this.puck.getY()>=this.player2.getLowerY()) && (this.puck.getY()<=this.player2.getHigherY())) {
                if ((this.puck.getX()<this.player2.getLowerX()) && (this.puck.getVelocityX()>0))
                    this.reversePuckVelocityX();
            } else if ((this.puck.getX()>=this.player2.getLowerX()) && (this.puck.getX()<=this.player2.getHigherX())) {
                if (((this.puck.getY()<this.player2.getLowerY()) && (this.puck.getVelocityY()>0))
                        || ((this.puck.getY()>this.player2.getHigherY()) && (this.puck.getVelocityY()<0)))
                    this.reversePuckVelocityY();
            } else {
                this.reversePuckVelocityX();
                this.reversePuckVelocityY();
            }
        }
    }

    private double xPuckDistance(Player player) {
        return Math.abs(this.puck.getX() - player.getX());
    }

    private double yPuckDistance(Player player) {
        return Math.abs(this.puck.getY() - player.getY());
    }
}