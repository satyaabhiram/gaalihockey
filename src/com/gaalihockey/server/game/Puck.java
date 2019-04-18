package com.gaalihockey.server.game;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Puck {
    private double x, y, velocityX, velocityY;
    static final Lock positionLock = new ReentrantLock();
    static final Lock velocityLock = new ReentrantLock();
    

    public final double RADIUS = 20;
    
    public void setVelocityXY(double x,double y) {
    	velocityLock.lock();
    	try {
    		this.velocityX = x;
    		this.velocityY = y;
    	}
    	finally {
    		velocityLock.unlock();
		}
    }
    public void updateXY() {
    	for(;;) {
    		positionLock.lock();
            try {
            	if(velocityLock.tryLock()) {
            		try {
            			this.x = this.x+this.velocityX;
                    	this.y = this.y+this.velocityY;
                    	return;
            		}
            		finally {
						velocityLock.unlock();
					}
            	}
            }
            finally {
    			positionLock.unlock();
    		}
    	}
    }
    
    public void setXY(double x, double y) {
    	positionLock.lock();
    	try {
    		this.x = x;
    		this.y = y;
    	}
    	finally {
			positionLock.unlock();
		}
    }
    
    public double[] getXY() {
    	positionLock.lock();
    	try {
    		double[] temp = {this.x,this.y};
    		return temp;
    	}
    	finally {
			positionLock.unlock();
		}
    }
    
    public double[] getXYVelocity() {
    	velocityLock.lock();
    	try {
    		double[] velocity = {this.velocityX,this.velocityY};
    		return velocity;
    	}
    	finally {
    		velocityLock.unlock();
    	}
    }

//    public double getX() {
//    	positionLock.lock();
//    	try {
//    		return this.x;
//    	}
//    	finally {
//    		positionLock.unlock();
//    	}
//        
//    }

//    public void setX(double x) {
//    	positionLock.lock();
//    	try {
//    		this.x = x;
//    	}
//        finally {
//			positionLock.unlock();
//		}
//    }

//    public double getY() {
//    	positionLock.lock();
//    	try {
//    		return this.y;
//    	}
//        finally {
//			positionLock.unlock();
//		}
//    }
//
//    public void setY(double y) {
//    	positionLock.lock();
//    	try {
//    		this.y = y;
//    	}
//        finally {
//			positionLock.unlock();
//		}
//    }

//    public double getLowerX() {
//    	positionLock.lock();
//    	try {
//    		return this.x - this.RADIUS;
//    	}
//        finally {
//			positionLock.unlock();
//		}
//    }
//
//    public double getHigherX() {
//    	positionLock.lock();
//    	try {
//    		return this.x + this.RADIUS;
//    	}
//        finally {
//			positionLock.unlock();
//		}
//    }
//
//    public double getLowerY() {
//    	positionLock.lock();
//    	try {
//    		return this.y - this.RADIUS;
//    	}
//        finally {
//			positionLock.unlock();
//		}    
//    }
//
//    public double getHigherY() {
//    	positionLock.lock();
//    	try {
//    		return this.y + this.RADIUS;
//    	}
//        finally {
//			positionLock.unlock();
//		} 
//    }

//    public double getVelocityX() {
//    	velocityLock.lock();
//    	try {
//    		return this.velocityX;
//    	}
//        finally {
//        	velocityLock.unlock();
//		}       
//    }
//
//    public void setVelocityX(double velocityX) {
//    	velocityLock.lock();
//    	try {
//    		this.velocityX = velocityX;
//    	}
//        finally {
//        	velocityLock.unlock();
//		}  
//    }
//
//    public double getVelocityY() {
//    	velocityLock.lock();
//    	try {
//    		return this.velocityY;
//    	}
//        finally {
//        	velocityLock.unlock();
//		}        
//    }
//
//    public void setVelocityY(double velocityY) {
//    	velocityLock.lock();
//    	try {
//    		this.velocityY = velocityY;
//    	}
//        finally {
//        	velocityLock.unlock();
//		}
//    }
}
