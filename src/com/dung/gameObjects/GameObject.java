package com.dung.gameObjects;

public abstract class GameObject {

	private float posX;
	private float posY;

	private GameWorld gameWorld;

	public GameObject(float x, float y, GameWorld gameWorld) {
		posX = x;
		posY = y;
		this.gameWorld = gameWorld;
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public GameWorld getGameWorld() {
		return gameWorld;
	}

	public void setGameWorld(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	public abstract void Update();

}
