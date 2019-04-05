package com.gaalihockey.client.game;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static javafx.scene.input.KeyCode.*;

public class Game extends Application {

    private final double puckMovement=5;
    private double arenaTopY;
    private double arenaBottomY;

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
        Rectangle player1Puck = new Rectangle();
        player1Puck.setX(170.0f);
        player1Puck.setY(290.0f);
        player1Puck.setWidth(10.0f);
        player1Puck.setHeight(70.0f);
        player1Puck.setFill(Color.WHITE);

        arenaBottomY-=player1Puck.getHeight();

        //Rectangle Object for Player 2 Puck
        Rectangle player2Puck = new Rectangle();
        player2Puck.setX(1120.0f);
        player2Puck.setY(290.0f);
        player2Puck.setWidth(10.0f);
        player2Puck.setHeight(70.0f);
        player2Puck.setFill(Color.WHITE);

        //Circle Object for Ball
        Circle ball=new Circle();
        ball.setCenterX(650.0f);
        ball.setCenterY(325.0f);
        ball.setRadius(15.0f);
        ball.setFill(Color.WHITE);



        //Creating a Group object
        Group root = new Group(arena);
        root.getChildren().add(player1Puck);
        root.getChildren().add(player2Puck);
        root.getChildren().add(ball);
        //Creating a scene object
        Scene scene = new Scene(root, 1200, 900);

        movePuckOnKeyPress(scene, player1Puck);
        //moveBallOnKeyPress(scene, ball);
        //Setting title to the Stage
        stage.setTitle("Air Hockey");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }

    private void movePuckOnKeyPress(Scene scene, final Rectangle myPuck) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent event) {
                double currentPos=myPuck.getY();
                double newPos1=currentPos-puckMovement;
                double newPos2=currentPos+puckMovement;
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
    private void moveBallOnKeyPress (Scene scene, final Circle myBall) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent event) {
                if(event.getCode()==LEFT){
                    myBall.setCenterX(myBall.getCenterX()- 13.0f);
                }else if(event.getCode()==RIGHT) {
                    myBall.setCenterX(myBall.getCenterX()+ 13.0f);
                }
            }
        });
    }
    */
//    public static void main(String[] args) {
//        launch(args);
//    }
}