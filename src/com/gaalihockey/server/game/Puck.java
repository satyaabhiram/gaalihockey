package com.gaalihockey.server.game;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Puck {
    private double x, y, velocityX, velocityY;
    static final Lock puckLock = new ReentrantLock();

    public final double RADIUS = 20;
    
    public void setVelocityXY(double x,double y) {
    	puckLock.lock();
    	try {
    		this.velocityX = x;
    		this.velocityY = y;
    	}
    	finally {
			puckLock.unlock();
		}
    }
    public void updateXY() {
        puckLock.lock();
        try {
        	this.x = this.x+this.getVelocityX();
        	this.y = this.y+this.getVelocityY();
        }
        finally {
			puckLock.unlock();
		}
    }
    
    public void setXY(double x, double y) {
    	puckLock.lock();
    	try {
    		this.x = x;
    		this.y = y;
    	}
    	finally {
			puckLock.unlock();
		}
    }
    
    public double[] getXY() {
    	puckLock.lock();
    	try {
    		double[] temp = {this.x,this.y};
    		return temp;
    	}
    	finally {
			puckLock.unlock();
		}
    }

    public double getX() {
    	puckLock.lock();
    	try {
    		return this.x;
    	}
    	finally {
    		puckLock.unlock();
    	}
        
    }

    public void setX(double x) {
    	puckLock.lock();
    	try {
    		this.x = x;
    	}
        finally {
			puckLock.unlock();
		}
    }

    public double getY() {
    	puckLock.lock();
    	try {
    		return this.y;
    	}
        finally {
			puckLock.unlock();
		}
    }

    public void setY(double y) {
    	puckLock.lock();
    	try {
    		this.y = y;
    	}
        finally {
			puckLock.unlock();
		}
    }

    public double getLowerX() {
    	puckLock.lock();
    	try {
    		return this.x - this.RADIUS;
    	}
        finally {
			puckLock.unlock();
		}
    }

    public double getHigherX() {
    	puckLock.lock();
    	try {
    		return this.x + this.RADIUS;
    	}
        finally {
			puckLock.unlock();
		}
    }

    public double getLowerY() {
    	puckLock.lock();
    	try {
    		return this.y - this.RADIUS;
    	}
        finally {
			puckLock.unlock();
		}    
    }

    public double getHigherY() {
    	puckLock.lock();
    	try {
    		return this.y + this.RADIUS;
    	}
        finally {
			puckLock.unlock();
		} 
    }

    public double getVelocityX() {
    	puckLock.lock();
    	try {
    		return this.velocityX;
    	}
        finally {
			puckLock.unlock();
		}       
    }

    public void setVelocityX(double velocityX) {
    	puckLock.lock();
    	try {
    		this.velocityX = velocityX;
    	}
        finally {
			puckLock.unlock();
		}  
    }

    public double getVelocityY() {
    	puckLock.lock();
    	try {
    		return this.velocityY;
    	}
        finally {
			puckLock.unlock();
		}        
    }

    public void setVelocityY(double velocityY) {
    	puckLock.lock();
    	try {
    		this.velocityY = velocityY;
    	}
        finally {
			puckLock.unlock();
		}
    }
}
