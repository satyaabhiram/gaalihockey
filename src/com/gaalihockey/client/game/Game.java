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

    private final double strikerMovement=30;
    private double arenaTopY;
    private double arenaBottomY;
    private double scoreSize=50.0f;

    public static Rectangle player1Striker = null;
    public static Rectangle player2Striker = null;
    public static Circle puck = null;

    public static boolean isPlayer1 = true;

    @Override
    public void start(Stage stage) throws Exception{
        //Rectangle Object for Arena
        Rectangle arena = new Rectangle();
        arena.setX(0.0f);
        arena.setY(0.0f);
        arena.setWidth(1000.0f);
        arena.setHeight(500.0f);

        arenaTopY=arena.getY();
        arenaBottomY=arena.getY()+arena.getHeight();

        //Rectangle Object for Player 1 Puck
        player1Striker = new Rectangle();
        player1Striker.setX(30.0f);
        player1Striker.setY(250.0f);
        player1Striker.setWidth(10.0f);
        player1Striker.setHeight(70.0f);
        player1Striker.setFill(Color.WHITE);

        arenaBottomY-=player1Striker.getHeight();

        //Rectangle Object for Player 2 Puck
        player2Striker = new Rectangle();
        player2Striker.setX(970.0f);
        player2Striker.setY(250.0f);
        player2Striker.setWidth(10.0f);
        player2Striker.setHeight(70.0f);
        player2Striker.setFill(Color.WHITE);

        //Circle Object for puck
        puck=new Circle();
        puck.setCenterX(500.0f);
        puck.setCenterY(250.0f);
        puck.setRadius(15.0f);
        puck.setFill(Color.WHITE);

        //Rectangle for Player 1 Score
        Rectangle player1Score = new Rectangle();
        player1Score.setX(arena.getX()+arena.getWidth()/2 - 100.0f);
        player1Score.setY(arena.getY());
        player1Score.setHeight(scoreSize);
        player1Score.setWidth(scoreSize);
        player1Score.setFill(Color.GRAY);

        //Rectangle for Player 2 Score
        Rectangle player2Score = new Rectangle();
        player2Score.setX(arena.getX()+arena.getWidth()/2 + 50.0f);
        player2Score.setY(arena.getY());
        player2Score.setHeight(scoreSize);
        player2Score.setWidth(scoreSize);
        player2Score.setFill(Color.GRAY);

        //Creating a Group object
        Group root = new Group(arena);
        root.getChildren().add(player1Striker);
        root.getChildren().add(player2Striker);
        root.getChildren().add(puck);
        root.getChildren().add(player1Score);
        root.getChildren().add(player2Score);
        //Creating a scene object
        Scene scene = new Scene(root, 1000, 500);

        if (isPlayer1)
            moveStrikerOnKeyPress(scene, player1Striker);
        else
            moveStrikerOnKeyPress(scene, player2Striker);

        //Setting title to the Stage
        stage.setTitle("Gaali Hockey");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }

    private void moveStrikerOnKeyPress(Scene scene, final Rectangle myStriker) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent event) {
                double currentPos=myStriker.getY();
                double newPos1=currentPos-strikerMovement;
                double newPos2=currentPos+strikerMovement;
                if(event.getCode()==UP){
                    if(arenaTopY<=newPos1 &&  arenaBottomY>=newPos1){
                        myStriker.setY(newPos1);
                    }
                }else if(event.getCode()==DOWN) {
                    if (arenaTopY<=newPos2 &&  arenaBottomY>=newPos2) {
                        myStriker.setY(newPos2);
                    }
                }
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}