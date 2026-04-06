package com.dung.userinterface;

import java.awt.event.KeyEvent;

import com.dung.gameObjects.GameWorld;

public class InputManager {

	private GameWorld gameWorld;

	public InputManager(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	public void processKeyPressed(int keycode) {

		switch (keycode) {

		case KeyEvent.VK_DOWN:

			break;
		case KeyEvent.VK_UP:
			gameWorld.player.Jump();
			break;
		case KeyEvent.VK_RIGHT:
			gameWorld.player.setDirection(gameWorld.player.RIGHT_DIR);
			gameWorld.player.Run();
			break;
		case KeyEvent.VK_LEFT:
			gameWorld.player.setDirection(gameWorld.player.LEFT_DIR);
			gameWorld.player.Run();
			break;
		case KeyEvent.VK_SPACE:

			break;
		case KeyEvent.VK_ENTER:
			 if (gameWorld.isGameOver()) {
                 gameWorld.resetGame();
             }
             break;
		}
	}

	public void processKeyRelessed(int keycode) {

		switch (keycode) {

		case KeyEvent.VK_DOWN:

			break;

		case KeyEvent.VK_UP:

			break;
		case KeyEvent.VK_RIGHT:
			gameWorld.player.StopRun();
			break;
		case KeyEvent.VK_LEFT:
			gameWorld.player.StopRun();
			break;
		case KeyEvent.VK_SPACE:

			break;
		}
	}
}
