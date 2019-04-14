package com.gaalihockey.server.game;

public class Puck {
    double x, y, velocityX, velocityY;

    public final double RADIUS = 20;

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getLowerX() {
        return this.x - this.RADIUS;
    }

    public double getHigherX() {
        return this.x + this.RADIUS;
    }

    public double getLowerY() {
        return this.y - this.RADIUS;
    }

    public double getHigherY() {
        return this.y + this.RADIUS;
    }

    public double getVelocityX() {
        return this.velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return this.velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
}
