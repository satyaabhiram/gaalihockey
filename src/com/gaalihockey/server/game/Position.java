package com.gaalihockey.server.game;
//Immutable object
public class Position {
	private final double x;
	private final double y;
	
	Position(double x, double y){
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	
}
