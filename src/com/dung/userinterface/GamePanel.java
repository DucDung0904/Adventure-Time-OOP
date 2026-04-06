package com.dung.userinterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.dung.gameObjects.GameWorld;
import com.dung.gameObjects.Player;

public class GamePanel extends JPanel implements Runnable, KeyListener {

	private Thread thread;
	private boolean isRunning = true;

	private InputManager inputmanager;
	
	private Graphics2D bufG2D;
	private BufferedImage bufImage;
	
	public GameWorld gameWorld;


	public GamePanel() { 

		gameWorld = new GameWorld();
		inputmanager = new InputManager(gameWorld);
		bufImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
	}

	@Override
	public void paint(Graphics g) {
		
		g.drawImage(bufImage, 0, 0, this);
		
		// Hiển thị tọa độ của player
		Player player = gameWorld.player;
		 g.setColor(Color.RED); 
		 g.drawString(" X: " + String.format("%.2f", player.getPosX()), 10, 20);
		 g.drawString(" Y: " + String.format("%.2f", player.getPosY()), 10, 40);

		}
		
	


	public void StartGame() {
			thread = new Thread(this);
			thread.start();		
	}
	
	public void UpdateGame() {
		gameWorld.Update();
	}
	public void RenderGame() {
		if (bufImage == null) {
			bufImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		}

		if (bufImage != null) {
			bufG2D = (Graphics2D) bufImage.getGraphics();	
		}

		if(bufG2D != null) {
			
			bufG2D.setColor(Color.WHITE);
			//bufG2D.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
			
			// Render object
			gameWorld.Render(bufG2D);
		}
	}
	
	@Override
	public void run() {
		
		// gameloop
		long FPS = 120;
		long period = 1000 * 1000000 / FPS;
		long beginTime;
		long sleepTime;

		beginTime = System.nanoTime();

		while (isRunning) {

			UpdateGame();
			RenderGame();
			repaint();

			long deltaTime = System.nanoTime() - beginTime;
			sleepTime = period - beginTime;
			try {
				if (sleepTime > 0) {
					Thread.sleep(sleepTime / 1000000);
				} else
					Thread.sleep(period / 2000000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			beginTime = System.nanoTime();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		inputmanager.processKeyPressed(e.getKeyCode());
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		inputmanager.processKeyRelessed(e.getKeyCode());

	}

}
