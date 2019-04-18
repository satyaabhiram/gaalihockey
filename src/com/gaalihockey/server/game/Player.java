package com.gaalihockey.server.game;

public class Player {
    int number;
    int score;
    double x;
    //y is volatile because only y value is changing and x is constant.
    volatile double y;

    final double WIDTH_X = 10;
    final double WIDTH_Y = 80;

    Player opponent;

    public Player(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public double getLowerX() {
        return this.x - this.WIDTH_X/2;
    }

    public double getHigherX() {
        return this.x + this.WIDTH_X/2;
    }

    public double getLowerY() {
    return this.y - this.WIDTH_Y/2;
    }

    public double getHigherY() {
        return this.y + this.WIDTH_Y/2;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Player getOpponent() {
        return this.opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
}
