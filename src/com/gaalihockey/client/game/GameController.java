package com.gaalihockey.client.game;

import javafx.application.Application;
import javafx.application.Platform;

public class GameController implements Runnable {
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Application.launch(Game.class);
	}

	public static void stopGame() {
		Platform.exit();
	}
}
