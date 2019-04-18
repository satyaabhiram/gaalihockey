package com.gaalihockey.client.game;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.scene.input.KeyCode.*;

public class Game extends Application {

    private final double strikerMovement=30;
    private double arenaTopY;
    private double arenaBottomY;

    public static Rectangle player1Striker = null;
    public static Rectangle player2Striker = null;
    public static Circle puck = null;
    //public static  Label variableLabel;
    public static Text text;
    public static Text message;
    public static boolean isPlayer1 = true;

    public static boolean MATCH_STARTED = false;

    @Override
    public void start(Stage stage) {
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
        player1Striker.setX(25.0f);
        player1Striker.setY(210.0f);
        player1Striker.setWidth(10.0f);
        player1Striker.setHeight(80.0f);
        player1Striker.setFill(Color.WHITE);

        arenaBottomY-=player1Striker.getHeight();

        //Rectangle Object for Player 2 Puck
        player2Striker = new Rectangle();
        player2Striker.setX(965.0f);
        player2Striker.setY(210.0f);
        player2Striker.setWidth(10.0f);
        player2Striker.setHeight(80.0f);
        player2Striker.setFill(Color.WHITE);

        //Circle Object for puck
        puck=new Circle();
        puck.setCenterX(500.0f);
        puck.setCenterY(250.0f);
        puck.setRadius(15.0f);
        puck.setFill(Color.WHITE);

        text = new Text();
        text.setText("0:0");
        text.setX(460.0);
        text.setY(40.0);
        text.setFont(Font.font(40));
        text.setFill(Color.GRAY);

        message = new Text();
        message.setText("");
        message.setX(350.0);
        message.setY(250.0);
        message.setFont(Font.font(60));
        message.setFill(Color.GRAY);

        //Creating a Group object
        Group root = new Group(arena);
        root.getChildren().add(player1Striker);
        root.getChildren().add(player2Striker);
        root.getChildren().add(puck);
        root.getChildren().add(text);
        root.getChildren().add(message);
        //Creating a scene object
        Scene scene = new Scene(root, 1000, 500);

        if (isPlayer1)
            moveStrikerOnKeyPress(scene, player1Striker);
        else
            moveStrikerOnKeyPress(scene, player2Striker);

        //Setting title to the Stage
        stage.setTitle("Gaali Hockey : " + (isPlayer1 ? "Player 1" : "Player 2"));

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();

        MATCH_STARTED = true;
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