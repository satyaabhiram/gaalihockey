package com.gaalihockey.server.game;


public class Game {
    private Puck puck;
    private Player player1, player2;
    

    private final double boardLowerX = 0;
    private final double boardHigherX = 1000;
    private final double boardLowerY = 0;
    private final double boardHigherY = 500;

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
    	this.puck.setXY(500.0,250.0);
//        this.puck.setX(500.0f);
//        this.puck.setY(250.0f);
    }

    public void resetPlayerPositions() {
        this.player1.setX(0);
        this.player1.setY(0);
        this.player2.setX(0);
        this.player2.setY(0);
    }

    public void initializePuckVelocity() {
    	this.puck.setVelocityXY(4.0,1.0);
//    	this.puck.setVelocityX(7.0);
//    	this.puck.setVelocityY(4.0);
    	//this.puck.setVelocityX((int) (Math.random() * 1000));
        //this.puck.setVelocityY((int) (Math.random() * 1000));
        //System.out.println();
    }

    public void updateScore(int player1score, int player2score) {
        this.player1.setScore(player1score);
        this.player2.setScore(player2score);
    }

    public void updatePuckPosition() {
//    	System.out.println(this.puck.getX()+"X position");
//    	System.out.println(this.puck.getY()+"Y position");
    	this.puck.updateXY();
//        this.puck.setX(this.puck.getX() + this.puck.getVelocityX());
//        this.puck.setY(this.puck.getY() + this.puck.getVelocityY());
    }

    public void watchForWallCollisions() {
        this.checkXWallCollision();
        this.checkYWallCollision();
    }

    public void watchForStrikerHits() {
        this.checkAndHandleStrikerHit();
    }

    private void checkXWallCollision() {
    	double[] posXY = this.puck.getXY();
    	double[] velXY = this.puck.getXYVelocity();
    	
        if (posXY[0]<=this.boardLowerX && velXY[0]<0) {
        	this.puck.setVelocityXY(-velXY[0],velXY[1]);
            this.updateScore(this.player1.getScore(), this.player2.getScore()+1);
        } else if (posXY[0]>=this.boardHigherX && velXY[0]>0) {
        	this.puck.setVelocityXY(-velXY[0],velXY[1]);
            this.updateScore(this.player1.getScore()+1, this.player2.getScore());
        }
    }

    private void checkYWallCollision() {
    	double[] pos = this.puck.getXY();
        //double y = this.puck.getY();
    	double[] vel = this.puck.getXYVelocity();
        double yVelocity = vel[1];
        if (((pos[1]<=this.boardLowerY) && (yVelocity<0)) || ((pos[1]>=this.boardHigherY) && (yVelocity>0))) {
        	this.puck.setVelocityXY(vel[0],-vel[1]);
        }
    }

//    private void reversePuckVelocityX() {
//        this.puck.setVelocityX(-this.puck.getVelocityX());
//    }

//    private void reversePuckVelocityY() {
//        this.puck.setVelocityY(-this.puck.getVelocityY());
//    }

    private void checkAndHandleStrikerHit() {
    	
    	double[] posXY = this.puck.getXY();
    	double[] velXY = this.puck.getXYVelocity();
    	
    	double xpuckDistance1 = Math.abs(posXY[0] - this.player1.getX());
    	double xpuckDistance2 = Math.abs(posXY[0] - this.player2.getX());
    	double ypuckDistance1 = Math.abs(posXY[1] - this.player1.getY());
    	double ypuckDistance2 = Math.abs(posXY[1] - this.player2.getY());
    	
    	double player1YL = this.player1.getLowerY();
    	double player1YH = this.player1.getHigherY();
    	double player2YL = this.player2.getLowerY();
    	double player2YH = this.player2.getHigherY();
    	
    	
        if ((xpuckDistance1 <= (this.player1.WIDTH_X/2 + this.puck.RADIUS))
                && (ypuckDistance1 <= (this.player1.WIDTH_Y/2 + this.puck.RADIUS))) {
            System.out.println("Collision with striker 1");
            if ((posXY[1]>=player1YL) && (posXY[1]<=player1YH)) {
                if ((posXY[0]>this.player1.getHigherX()) && (velXY[0]<0))
                	this.puck.setVelocityXY(-velXY[0],velXY[1]);
                    //this.reversePuckVelocityX();
            } else if ((posXY[0]>=this.player1.getLowerX()) && (posXY[0]<=this.player1.getHigherX())) {
                if (((posXY[1]<player1YL) && (velXY[1]>0))
                        || ((posXY[1]>player1YH) && (velXY[1]<0)))
                	this.puck.setVelocityXY(velXY[0],-velXY[1]);
            } else {
                if(velXY[0]<0)
                	this.puck.setVelocityXY(-velXY[0],velXY[1]);
                this.puck.setVelocityXY(velXY[0],-velXY[1]);
            }
        } else if ((xpuckDistance2 <= (this.player2.WIDTH_X/2 + this.puck.RADIUS))
                && (ypuckDistance2<= (this.player2.WIDTH_Y/2 + this.puck.RADIUS))) {
            System.out.println("Collision with striker 2");
            if ((posXY[1]>=player2YL) && (posXY[1]<=player2YH)) {
                if ((posXY[0]<this.player2.getLowerX()) && (velXY[0]>0))
                	this.puck.setVelocityXY(-velXY[0],velXY[1]);
            } else if ((posXY[0]>=this.player2.getLowerX()) && (posXY[0]<=this.player2.getHigherX())) {
                if (((posXY[1]<player2YL) && (velXY[1]>0))
                        || ((posXY[1]>player2YH) && (velXY[1]<0)))
                	this.puck.setVelocityXY(velXY[0],-velXY[1]);
            } else {
                if(velXY[0]>0)
                	this.puck.setVelocityXY(-velXY[0],velXY[1]);
                this.puck.setVelocityXY(velXY[0],-velXY[1]);
            }
        }
    }

//    private double xPuckDistance(Player player) {
//        return Math.abs(this.puck.getX() - player.getX());
//    }
//
//    private double yPuckDistance(Player player) {
//        return Math.abs(this.puck.getY() - player.getY());
//    }
}
