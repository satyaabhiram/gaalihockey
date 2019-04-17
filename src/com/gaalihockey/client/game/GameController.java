package com.gaalihockey.client.game;

import javafx.application.Application;

public class GameController implements Runnable {
    public static void startGame() {
        Application.launch(Game.class);
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Application.launch(Game.class);
	}
}
