package com.dung.gameObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import com.dung.gameObjects.enemies.Hatdevil;
import com.dung.gameObjects.enemies.Plantgun;
import com.dung.gameObjects.items.Coins;
import com.dung.userinterface.GameFrame;

public class GameWorld {
	
	// Giới hạn dưới của Map
	private static final int MAP_BOTTOM_LIMIT = 1350;
	
	private boolean gameOver;
	
	private long startTime; 
    private long currentTime; 
    private long elapsedTime; 
    private long completionTime;
	
	public PhysicalMap physicalMap;
	
	public Player player;
	
	public Camera camera;
	
	public ParticularObjectManager particularObjectManager;
	
	public BackgroundMap background;
	
	private ArrayList<Coins> coinsList;
	
	
	public GameWorld() {
		
		player = new Player(200, 930, this);
		player.setTeamType(ParticularObject.LEAGUE_TEAM);
		
		physicalMap = new PhysicalMap(0, 0, this);	

		background = new BackgroundMap(0, 0, this);
		camera = new Camera(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, this);
		
		particularObjectManager = new ParticularObjectManager(this);
		particularObjectManager.addObject(player);
		
		coinsList = new ArrayList<>();
		
		gameOver = false;
		startTime = System.currentTimeMillis();
		
		initItems();
		initEnemies();
	}
	
	// Thêm enemy
	public void initEnemies() {
		
		// add plantgun
		ParticularObject plantgun1 = new Plantgun(1580, 1120, this);
        plantgun1.setDirection(ParticularObject.LEFT_DIR);
        plantgun1.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(plantgun1);
        
        ParticularObject plantgun2 = new Plantgun(4511, 790, this);
        plantgun2.setDirection(ParticularObject.LEFT_DIR);
        plantgun2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(plantgun2);
        
        // add hatdevil
        ParticularObject hatdevil1 = new Hatdevil(1250, 1111, 250, this);
        hatdevil1.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(hatdevil1);
        
        ParticularObject hatdevil2 = new Hatdevil(2240, 931, 130, this);
        hatdevil2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(hatdevil2);
        
        ParticularObject hatdevil3 = new Hatdevil(2702, 931, 130, this);
        hatdevil3.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(hatdevil3);
        
        ParticularObject hatdevil4 = new Hatdevil(4092, 1020, 130, this);
        hatdevil4.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(hatdevil4);
        

        ParticularObject hatdevil5 = new Hatdevil(650, 903, 120, this);
        hatdevil5.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(hatdevil5);

		
	}
	
	// Thêm vật phẩm
	public void initItems() {
		
		//add coins
		coinsList.add(new Coins(431, 993, 30, 30, this));
		coinsList.add(new Coins(163, 573, 30, 30, this));
		coinsList.add(new Coins(454, 663, 30, 30, this));
		coinsList.add(new Coins(232, 573, 30, 30, this));
		coinsList.add(new Coins(1439, 573, 30, 30, this));
		coinsList.add(new Coins(1523, 573, 30, 30, this));
		coinsList.add(new Coins(1408, 730, 30, 30, this));
		coinsList.add(new Coins(2264, 820, 30, 30, this));
		coinsList.add(new Coins(1408, 980, 30, 30, this));
		coinsList.add(new Coins(2519, 920, 30, 30, this));
		coinsList.add(new Coins(2774, 820, 30, 30, this));
		coinsList.add(new Coins(4025, 920, 30, 30, this));
		coinsList.add(new Coins(4111, 700, 30, 30, this));
		coinsList.add(new Coins(3680, 543, 30, 30, this));
		coinsList.add(new Coins(3755, 543, 30, 30, this));
		coinsList.add(new Coins(4320, 440, 30, 30, this));
		coinsList.add(new Coins(4320, 440, 30, 30, this));
		
		
		
		
		for (Coins coin : coinsList) {
			coin.setTeamType(ParticularObject.LEAGUE_TEAM);
			particularObjectManager.addObject(coin);
		}
	}
	
	// kiểm tra player rơi khởi map
	private void checkPlayerFellOffMap() {
	    if (player.getPosY() >= MAP_BOTTOM_LIMIT) { 
	    		player.setBlood(0);  
	    		gameOver = true;
	    }
	}
	
    private void checkcompleted() {
        if (gameOver) return;
        
        boolean iscollected = true;
        for (Coins coin : coinsList) {
            if (!coin.isCollected()) {
            	iscollected = false;
                break;
            }
        }
        if (iscollected) {
            gameOver = true;
            completionTime = elapsedTime;
           
        }
    }
    
 
    private String formatTime(long timeInSeconds) {
        long minutes = timeInSeconds / 60;
        long seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void resetGame() {
        resetPlayerPosition();
        
        particularObjectManager.particularObjects.removeAll(coinsList);
        coinsList.clear();
      
        initItems();
   
        gameOver = false;
        player.setBlood(3);
        startTime = System.currentTimeMillis();
        completionTime = 0;
    }
    
	public void Update() {
		
		if (!gameOver) {
			currentTime = System.currentTimeMillis();
            elapsedTime = (currentTime - startTime) / 1000; 
			
            particularObjectManager.UpdateObjects();
            camera.Update();
            
            
            for (ParticularObject object : particularObjectManager.particularObjects) {
                if (object instanceof Coins) {
                    Coins coin = (Coins) object;
                    if (!coin.isCollected() && player.getBoundForCollisionWithMap().intersects(coin.getBoundForCollisionWithMap())) {
                    	coin.collect();
                        System.out.println("Đã thu thập vàng!");
                    }
                }
            }
   
            if (player.getState() == ParticularObject.DEATH || player.getBlood() <= 0) {
                gameOver = true;
                return;
            }
            checkPlayerFellOffMap();
            checkcompleted();
        }
    }
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public void resetPlayerPosition() {
        player.setPosX(200);
        player.setPosY(930);
        player.setSpeedX(0);
        player.setSpeedY(0);
    }

	public void Render(Graphics2D g2) {
		//physicalMap.draw(g2);
		background.draw(g2);
		
		particularObjectManager.draw(g2);

			g2.setColor(Color.WHITE);
		    g2.setFont(new Font("Arial", Font.BOLD, 30));
		    String timeText = "Time: " + formatTime(elapsedTime);
		    g2.drawString(timeText, 15, 30);
		    
		    if (gameOver) {     
		        g2.setColor(new Color(0, 0, 0, 100));
		        g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
		      
		        g2.setColor(Color.WHITE);
		        g2.setFont(new Font("Arial", Font.BOLD, 30));
		  
		        if (player.getBlood() <= 0) {
		            String dieText = "YOU DIE!";
		            int x = GameFrame.SCREEN_WIDTH / 2 - g2.getFontMetrics().stringWidth(dieText) / 2;
		            int y = GameFrame.SCREEN_HEIGHT / 2;
		            g2.drawString(dieText, x, y);
		            
		            g2.setFont(new Font("Arial", Font.PLAIN, 20));
		            String enterText = "Enter để thử lại";
		            x = GameFrame.SCREEN_WIDTH / 2 - g2.getFontMetrics().stringWidth(enterText) / 2;
		            y += 40;
		            g2.drawString(enterText, x, y);
		        } 
		        
		        else {
		            String completionText = "Thời gian:" + formatTime(completionTime);
		            int x = GameFrame.SCREEN_WIDTH / 2 - g2.getFontMetrics().stringWidth(completionText) / 2;
		            int y = GameFrame.SCREEN_HEIGHT / 2;
		            g2.drawString(completionText, x, y);
		            
		            g2.setFont(new Font("Arial", Font.PLAIN, 20));
		            String enterText = "Enter để chơi lại";
		            x = GameFrame.SCREEN_WIDTH / 2 - g2.getFontMetrics().stringWidth(enterText) / 2;
		            y += 40;
		            g2.drawString(enterText, x, y);
		        }
		  }
	}
}

		
