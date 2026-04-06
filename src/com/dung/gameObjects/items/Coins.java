package com.dung.gameObjects.items;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dung.effect.Animation;
import com.dung.effect.CacheDataLoader;
import com.dung.gameObjects.Camera;
import com.dung.gameObjects.GameWorld;
import com.dung.gameObjects.ParticularObject;

public class Coins extends ParticularObject {
    private Animation coinsAni;  
    private boolean collected; 

    public Coins(int x, int y, int width, int height, GameWorld gameWorld) {
        super(x, y, width, height, 0, 0, gameWorld);  
      
        coinsAni = CacheDataLoader.getInstance().getAnimation("coins");
        
        this.collected = false;  
        setTeamType(LEAGUE_TEAM);  
    }

    public boolean isCollected() {
        return collected;
    }


    public void collect() {
        if (!collected) {
            this.collected = true;
            setState(DEATH); 
        }
    }

    @Override
    public void Update() {
        super.Update();

        if (!collected) {
            coinsAni.Update(System.nanoTime());
        }
    }


    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap(); 
    }

 
    @Override
    public void draw(Graphics2D g2) {
        Camera camera = getGameWorld().camera;

        if (getPosX() + getWidth() / 2 > camera.getPosX() && 
            getPosX() - getWidth() / 2 < camera.getPosX() + camera.getWidthView() &&
            getPosY() + getHeight() / 2 > camera.getPosY() &&
            getPosY() - getHeight() / 2 < camera.getPosY() + camera.getHeightView()) {

            if (!collected) {
                // Draw the current frame of the coin animation
                g2.drawImage(coinsAni.getCurrentImage(), 
                             (int) (getPosX() - camera.getPosX() - getWidth() / 2), 
                             (int) (getPosY() - camera.getPosY() - getHeight() / 2), 
                             null);
            }
        }
    }

   
    @Override
    public void attack() {}
}