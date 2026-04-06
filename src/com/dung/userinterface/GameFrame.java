package com.dung.userinterface;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import com.dung.effect.CacheDataLoader;

public class GameFrame extends JFrame{

	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 600;
	
	GamePanel gamepanel;
	
	public GameFrame() {
		super("Adventure Time");
		Toolkit window = this.getToolkit();
		Dimension dimension = window.getScreenSize();
		
		this.setBounds((dimension.width - SCREEN_WIDTH)/2, (dimension.height - SCREEN_HEIGHT)/2, SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		
		try {
			CacheDataLoader.getInstance().LoadData();
		} catch (IOException e) {
		
			e.printStackTrace();
		}	
		
		gamepanel = new GamePanel(); 
		add(gamepanel);
		
		this.addKeyListener(gamepanel);
		
	}
	
	public void StartGame() {
		gamepanel.StartGame();
	}
	
	public static void main(String[] args) {
		GameFrame gameframe = new GameFrame();
		gameframe.setVisible(true);
		gameframe.setResizable(false);
		gameframe.StartGame();
	}
}
