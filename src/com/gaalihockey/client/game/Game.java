package com.gaalihockey.client.game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.scene.input.KeyCode.*;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

public class Game extends Application {

    private final double sliderMovement=10;
    private double arenaTopY;
    private double arenaBottomY;
    private double scoreSize=50.0f;

    public static Rectangle player1Slider=null;
    public static Rectangle player2Slider=null;
    public static Circle puck=null;

    public static boolean isPlayer1;
    @Override
    public void start(Stage stage) throws Exception{


        //Rectangle Object for Arena
        Rectangle arena = new Rectangle();
        arena.setX(150.0f);
        arena.setY(75.0f);
        arena.setWidth(1000.0f);
        arena.setHeight(500.0f);

        arenaTopY=arena.getY();
        arenaBottomY=arena.getY()+arena.getHeight();

        //Rectangle Object for Player 1 Puck
        player1Slider = new Rectangle();
        player1Slider.setX(210.0f);
        player1Slider.setY(290.0f);
        player1Slider.setWidth(10.0f);
        player1Slider.setHeight(70.0f);
        player1Slider.setFill(Color.WHITE);

        arenaBottomY-=player1Slider.getHeight();

        //Rectangle Object for Player 2 Puck
        player2Slider = new Rectangle();
        player2Slider.setX(1080.0f);
        player2Slider.setY(290.0f);
        player2Slider.setWidth(10.0f);
        player2Slider.setHeight(70.0f);
        player2Slider.setFill(Color.WHITE);

        //Circle Object for puck
        puck=new Circle();
        puck.setCenterX(650.0f);
        puck.setCenterY(325.0f);
        puck.setRadius(15.0f);
        puck.setFill(Color.WHITE);

        //Rectangle for Player 1 Score
        Rectangle player1Score = new Rectangle();
        player1Score.setX(arena.getX()+arena.getWidth()/2 - 100.0f);
        player1Score.setY(arena.getY());
        player1Score.setHeight(scoreSize);
        player1Score.setWidth(scoreSize);
        player1Score.setFill(Color.RED);

        //Rectangle for Player 2 Score
        Rectangle player2Score = new Rectangle();
        player2Score.setX(arena.getX()+arena.getWidth()/2 + 50.0f);
        player2Score.setY(arena.getY());
        player2Score.setHeight(scoreSize);
        player2Score.setWidth(scoreSize);
        player2Score.setFill(Color.RED);

        //Creating a Group object
        Group root = new Group(arena);
        root.getChildren().add(player1Slider);
        root.getChildren().add(player2Slider);
        root.getChildren().add(puck);
        root.getChildren().add(player1Score);
        root.getChildren().add(player2Score);
        //Creating a scene object
        Scene scene = new Scene(root, 900, 600);

        movePuckOnKeyPress(scene, player1Slider);
        //movepuckOnKeyPress(scene, puck);
        //Setting title to the Stage
        stage.setTitle("Air Hockey");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
        
        Bounds bounds = arena.getBoundsInLocal();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20),
        		new EventHandler<ActionEvent>() {
        	double dx = 7;
        	double dy = 5;
        	
        	public void handle(ActionEvent t) {
        		puck.setCenterX(puck.getCenterX()+dx);
        		puck.setCenterY(puck.getCenterY()+dy);
        		System.out.println(puck.getCenterX());
        		//System.out.println(puck.getLayoutY());
        		
        		if(puck.getCenterX() <= (bounds.getMinX() + puck.getRadius()) || 
                        puck.getCenterX() >= (bounds.getMaxX() - puck.getRadius()) ){

                	dx = -dx;

                }
        		
        		//If the ball reaches the bottom or top border make the step negative
                if((puck.getCenterY() >= (bounds.getMaxY() - puck.getRadius())) || 
                        (puck.getCenterY() <= (bounds.getMinY() + puck.getRadius()))){

                	dy = -dy;

                }
        	}
        }));
            //    new KeyValue(puck.layoutXProperty(), bounds.getMaxX()-puck.getRadius())));
        timeline.setCycleCount(timeline.INDEFINITE);
        timeline.play();
    }

    private void movePuckOnKeyPress(Scene scene, final Rectangle myPuck) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent event) {
                double currentPos=myPuck.getY();
                double newPos1=currentPos-sliderMovement;
                double newPos2=currentPos+sliderMovement;
                if(event.getCode()==UP){
                    if(arenaTopY<=newPos1 &&  arenaBottomY>=newPos1){
                        myPuck.setY(newPos1);
                    }
                }else if(event.getCode()==DOWN) {
                    if (arenaTopY<=newPos2 &&  arenaBottomY>=newPos2) {
                        myPuck.setY(newPos2);
                    }
                }
            }
        });
    }

    /*
    private void movepuckOnKeyPress (Scene scene, final Circle mypuck) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent event) {
                if(event.getCode()==LEFT){
                    mypuck.setCenterX(mypuck.getCenterX()- 13.0f);
                }else if(event.getCode()==RIGHT) {
                    mypuck.setCenterX(mypuck.getCenterX()+ 13.0f);
                }
            }
        });
    }
    */
//    public static void main(String[] args) {
//        launch(args);
//    }
}