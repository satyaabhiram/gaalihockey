package com.gaalihockey.client;

import com.gaalihockey.client.game.Game;
import com.gaalihockey.client.game.GameController;
import com.gaalihockey.message.Message;
import com.gaalihockey.message.MessageType;

import java.io.*;

public class Receiver implements Runnable {
    private ObjectInputStream in;

    public Receiver(ObjectInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
    	System.out.println("Started Receiver thread");
        Message inputMessage;
        try {
            while (!(inputMessage = (Message) this.in.readObject()).equals(null)) {
                // System.out.println("Message from server!\nType: " + inputMessage.getMessageType() + "\nBody: " + inputMessage.getValue1() + ", " + inputMessage.getValue2());
                // Handle input from server in swing

                MessageType mType=inputMessage.getMessageType();

                switch (mType){
                    case TEXT:
                        break;

                    case START:
                    	GameController gc = new GameController();
                    	Thread gcThread = new Thread(gc);
                    	gcThread.start();
                        break;

                    case STOP:
                        GameController.stopGame();
                        break;

                    case INITIALIZE:
                        int playerNum=Integer.parseInt(inputMessage.getValue1());
                        if(playerNum==1){
                            Game.isPlayer1=true;
                        }else{
                            Game.isPlayer1=false;
                        }
                        break;

                    case PUCK:
                    	//System.out.println("received puck position");
                        double xCentre=Double.parseDouble(inputMessage.getValue1());
                        double yCentre=Double.parseDouble(inputMessage.getValue2());
                        System.out.println("Set: "+ xCentre+" "+yCentre);
                        if(Game.MATCH_STARTED) {
                            Game.puck.setCenterX(xCentre);
                            Game.puck.setCenterY(yCentre);
                        }
                        break;

                    case OPPONENT:
                        double xPos=Double.parseDouble(inputMessage.getValue1());
                        double yPos=Double.parseDouble(inputMessage.getValue2());
                        if(Game.MATCH_STARTED) {
                            if (Game.isPlayer1) {
                                Game.player2Striker.setX(xPos);
                                Game.player2Striker.setY(yPos);
                            } else {
                                Game.player1Striker.setX(xPos);
                                Game.player1Striker.setY(yPos);
                            }
                        }
                        break;

                    case SCORE:
                        break;
                        
                    case STRIKER:
                    	break;
                    	
                    default:
                    	break;
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not read message", e);
        } catch (IOException e) {
            throw new RuntimeException("Could not read message", e);
        }
    }
}
